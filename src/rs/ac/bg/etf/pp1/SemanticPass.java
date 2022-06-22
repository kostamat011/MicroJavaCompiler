package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SemanticPass extends VisitorAdaptor {

		Logger log = Logger.getLogger(getClass());

		// defined global vars count
		//
		private int globalVarCount = 0;
		
		// current type for vars and constants
		//
		private Struct currType = Tab.noType;
		
		// current type for methods
		//
		private Struct currMethodType = Tab.noType;
		
		// current method type name
		//
		private String currMethodTypeName = null;
		
		// current method
		//
		private Obj currMethod = Tab.noObj;
		
		// count of local vars declared in current method
		//
		private int localVarCount = 0;
		
		// is return statement found for current method
		//
		private boolean currMethodReturnFound = false;
		
		// is main method found in program definiton
		//
		private boolean mainFound = false;
		
		// current record 
		//
		private Obj currRecord = Tab.noObj;
		
		// count of fields declared in current record
		//
		private int fieldCount = 0;
		
		// Symbol table extensions
		//
		public static final int Record = 8;
		public static final Struct boolType = new Struct(Struct.Bool);
		public static final Struct recordType = new Struct(Record);
		
		// init symbol table before semantic pass begins
		//
		public SemanticPass() {
			Tab.init();
			Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
		}
		
		// check if successful semantic pass
		//
		private boolean error = false;
		
		public boolean isSuccessful() {
			return !error;
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Errors util methods */
		
		private void reportError(String msg, SyntaxNode node) {
			error = true;
			log.error(msg);	
		}
		
		private void reportInfo(String msg, SyntaxNode node) {
			log.info(msg);
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Other util methods */
		
		private boolean findSymbolInTable(String name) {
			return (Tab.find(name) != Tab.noObj);
		}
		
		private boolean findSymbolInCurrentScope(String name) {
			return (Tab.currentScope.findSymbol(name) != null);
		}
		
		private void insertVarToTable(String name, boolean isArray) {
			if(isArray) {
				Tab.insert(Obj.Var, name, new Struct(Struct.Array, currType));
			} else {
				Tab.insert(Obj.Var, name, currType);
			}
		}
		
		private boolean insertConstToTable(String name, ConstVal constVal) {
			
			// insert const of correct type and set address to const value
			// in case of type mismatch between current type and const literal type return false
			//
			if(constVal instanceof NumberConst) {
				if(currType != Tab.intType) {
					return false;
				}
				Obj insertedConst  = Tab.insert(Obj.Con, name, Tab.intType);
				insertedConst.setAdr(((NumberConst)constVal).getNumVal());
			} 
			
			else if (constVal instanceof BoolConst) {
				if(currType != boolType) {
					return false;
				}
				Obj insertedConst  = Tab.insert(Obj.Con, name, boolType);
				insertedConst.setAdr((((BoolConst)constVal).getBoolVal()) ? 1 : 0);
			} 
			
			else if (constVal instanceof CharConst) {
				if(currType != Tab.charType) {
					return false;
				}
				Obj insertedConst  = Tab.insert(Obj.Con, name, Tab.charType);
				insertedConst.setAdr(((CharConst)constVal).getCharVal());
			}
			
			else {
				return false;
			}
			
			return true;
		}
		
		private void resetMethodData() {
			currMethod = Tab.noObj;
			currMethodType = Tab.noType;
			currMethodTypeName = null;
			currMethodReturnFound = false;
			localVarCount = 0;
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Visiting program */
		
		// Program name - start of program
		//
		public void visit(ProgramName progName) {
			//log.info("Evo nas u program name");
			progName.obj = Tab.insert(Obj.Prog, progName.getName(), Tab.noType);
			Tab.openScope();
		}
		
		
		// Program - end of program
		//
		public void visit(Program prog) {
			//log.info("Evo nas u program");
			if(globalVarCount > 65536) {
				reportError("Program is using more than 65536 global variables - forbidden.", null);
			}
			
			if(!mainFound) {
				reportError("No main method found", null);
			}
			
			Tab.chainLocalSymbols(prog.getProgramName().obj);
			Tab.closeScope();
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Visiting type */
		
		public void visit(Type t) {
			Obj typeNode = Tab.find(t.getName());
			if(typeNode == Tab.noObj) {
				reportError("Name " + t.getName() + " not found in symbol table." ,null);
				currType = t.struct = Tab.noType;
			} else {
				if(typeNode.getKind() == Obj.Type) {
					currType = t.struct = typeNode.getType();
				} else {
					reportError("Name "+ t.getName() + " does not represent a type in symbol table.", t);
					currType = t.struct = Tab.noType;
				}
			}
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Visiting variable declaration */
		
		// Local vars
		//
		public void visit(VarDeclSingle varDecl) {
			if(findSymbolInCurrentScope(varDecl.getVarName())) {
				reportError("Variable with name " + varDecl.getVarName() + " already defined in current scope.", varDecl);
			} else {
				boolean isArray = (varDecl.getArrBracketsOption() != null);
				if(currMethod != Tab.noObj && currRecord == Tab.noObj) {
					localVarCount++;
				} else if(currMethod == Tab.noObj && currRecord != Tab.noObj) {
					fieldCount++;
				} else {
					reportError("Cannot declare local variable outside Method or Record", varDecl);
					return;
				}
				insertVarToTable(varDecl.getVarName(), isArray);
			}
		}
		
		// Global vars
		//
		public void visit(GlobalVarDeclarationSingle globalVarDecl) {
			if(findSymbolInCurrentScope(globalVarDecl.getVarName())) {
				reportError("Global variable with name " + globalVarDecl.getVarName() + " is already defined.", globalVarDecl);
			} else {
				boolean isArray = (globalVarDecl.getArrBracketsOption() != null);
				insertVarToTable(globalVarDecl.getVarName(), isArray);
				globalVarCount++;
			}
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Visiting const assignment */
		
		public void visit(ConstAssign constAssign) {
			if(findSymbolInCurrentScope(constAssign.getConstName())) {
				reportError("Constant with name " + constAssign.getConstName() + " is already defined.", constAssign);
			} else {
				if(!insertConstToTable(constAssign.getConstName(), constAssign.getConstVal())) {
					reportError("Invalid constant type",constAssign);
				} else {
					// success
				}
			}
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Visiting record */
		
		// Record Name - start of record
		//
		public void visit(RecordName recordName) {
			if(findSymbolInTable(recordName.getName())) {
				reportError("Name " + recordName.getName() + " is already defined.", recordName);
			} else {
				currRecord = recordName.obj = Tab.insert(Obj.Type, recordName.getName(), recordType);
				Tab.openScope();
			}
		}
		
		// Record - end of record
		//
		public void visit(RecordDeclarationExpr record) {
			Tab.chainLocalSymbols(record.getRecordName().obj);
			Tab.closeScope();
			currRecord = Tab.noObj;
		}
		
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Visiting method return type */
		
		// Void return type
		//
		public void visit(MethodTypeVoid methodTypeVoid) {
			currMethodType = Tab.noType;
			currMethodTypeName = "void";
		}
		
		// Return type
		//
		public void visit(MethodTypeType methodType) {
			currMethodType = methodType.getType().struct;
			currMethodTypeName = methodType.getType().getName();
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Visiting method */
		
		// Method Name - start of method signature
		//
		public void visit(MethodName methodName) {
			if(findSymbolInTable(methodName.getName())) {
				reportError("Name " + methodName.getName() + " is already defined.", methodName);
				currMethod = Tab.noObj;
			} else {
				methodName.obj = Tab.insert(Obj.Meth, methodName.getName(), currMethodType);
				currMethod = methodName.obj;
				currMethod.setLevel(0);
				Tab.openScope();
			}
		}
		
		// Method signature without params
		//
		public void visit(MethodSignaturePlain methodSignature) {
			String name = methodSignature.getMethodName().getName();
			MethodType type = methodSignature.getMethodType();
			
			if(name.equals("main")) {
				if(type instanceof MethodTypeVoid) {
					if(mainFound) {
						reportError("Multiple definitions of main method.", methodSignature);
						currMethod = Tab.noObj;
					} else {
						mainFound = true;
					}
				} else {
					reportError("Main method must have void type.", methodSignature);
				}
			} 
		}
		
		// Method signature with params
		//
		public void visit(MethodSignatureParams methodSignature) {
			String name = methodSignature.getMethodName().getName();
			if(name.equals("main")) {
				reportError("Main method must have no parameters.", methodSignature);
			}
		}
		
		// Method signature with var args
		//
		public void visit(MethodSignatureVarArgs methodSignature) {
			String name = methodSignature.getMethodName().getName();
			if(name.equals("main")) {
				reportError("Main method must have no parameters.", methodSignature);
			}
		}
		
		// Method declaration - end of method
		//
		public void visit(MethodDeclaration method) {
			if( (currMethodType != Tab.noType) && !currMethodReturnFound) {
				reportError("Missing return statement for method of type " + currMethodTypeName, method);
				return;
			}
			
			if(localVarCount > 256) {
				reportError("Method "+ currMethod.getName() + " has more than 256 local variables.", method);
				return;
			}
			
			resetMethodData();
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Visiting formal parameters */
		
		public void visit(FormPars formPars) {
			// at this moment current scope will contain only formal params visited so far
			//
			SymbolDataStructure pars = Tab.currentScope().getLocals();
			currMethod.setLocals(pars);
		}
		
		// Single Formal parameter visit
		//
		public void visit(FormPar formPar) {
			if(formPar instanceof FormParSingle) {
				FormParSingle param = (FormParSingle)formPar;
				
				if(findSymbolInCurrentScope(param.getFormParName())) {
					reportError("Name of formal parameter "+param.getFormParName()+" is already defined.",param);
					return;
				}
				
				boolean isArray = (param.getArrBracketsOption() != null);
				
				insertVarToTable(param.getFormParName(), isArray);
			}
		}
		
		// VarArgs visit
		//
		public void visit(VarArgsDeclaration varArgs) {
			String varArgName = varArgs.getVarArgsName();
			if(findSymbolInCurrentScope(varArgName)) {
				reportError("Name of var arg "+varArgName+" is already defined.", varArgs);
				return;
			}
			// insert var args to table as an array
			//
			insertVarToTable(varArgName,true);
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Visiting statements */
		
		// return
		//
		public void visit(SingleReturnStatement ret) {
			if(currMethod == Tab.noObj) {
				reportError("Return statement found outside the method",ret);
				return;
			}
			
			if(currMethodType != Tab.noType) {
				reportError("Return statement without expression is invalid for method of type "+ currMethodTypeName,ret);
				return;
			}
			
			currMethodReturnFound = true;
		}
		
		// return expr
		//
		public void visit(SingleReturnExprStatement ret) {
			if(currMethod == Tab.noObj) {
				reportError("Return statement found outside the method",ret);
				return;
			}
			
			if(currMethodType == Tab.noType) {
				reportError("Return statement with expression is invalid for method of type void", ret);
				return;
			}
			
			Struct returnExprType = ret.getExpr().obj.getType();
			
			if(!returnExprType.compatibleWith(currMethodType)) {
				reportError("Return statement returning wrong type, " + currMethodTypeName + " expected", ret );
				return;
			}
			
			currMethodReturnFound = true;
		}
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/* Visiting expressions */
		
		
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	
}

package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Stack;

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
	
	// stack of lists of actual params for method calls
	//
	private Stack<ArrayList<Obj>> listsOfActParamsStack = new Stack<ArrayList<Obj>>();

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
		if (isArray) {
			Tab.insert(Obj.Var, name, new Struct(Struct.Array, currType));
		} else {
			Tab.insert(Obj.Var, name, currType);
		}
	}

	private boolean insertConstToTable(String name, ConstVal constVal) {

		// insert const of correct type and set address to const value
		// in case of type mismatch between current type and const literal type return
		// false
		//
		if (constVal instanceof NumberConst) {
			if (currType != Tab.intType) {
				return false;
			}
			Obj insertedConst = Tab.insert(Obj.Con, name, Tab.intType);
			insertedConst.setAdr(((NumberConst) constVal).getNumVal());
		}

		else if (constVal instanceof BoolConst) {
			if (currType != boolType) {
				return false;
			}
			Obj insertedConst = Tab.insert(Obj.Con, name, boolType);
			insertedConst.setAdr((((BoolConst) constVal).getBoolVal()) ? 1 : 0);
		}

		else if (constVal instanceof CharConst) {
			if (currType != Tab.charType) {
				return false;
			}
			Obj insertedConst = Tab.insert(Obj.Con, name, Tab.charType);
			insertedConst.setAdr(((CharConst) constVal).getCharVal());
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

	private String getMulopName(Mulop mul) {
		if (mul instanceof MulopMul) {
			return "multiplication";
		}
		if (mul instanceof MulopDiv) {
			return "division";
		}
		if (mul instanceof MulopPct) {
			return "moduo";
		}
		return "";
	}

	private String getAddopName(Addop add) {
		if (add instanceof AddopPlus) {
			return "addition";
		}
		if (add instanceof AddopMinus) {
			return "substraction";
		}
		return "";
	}
	
	private boolean isInt(Obj o) {
		return o.getType().getKind() == Struct.Int;
	}
	
	private boolean isChar(Obj o) {
		return o.getType().getKind() == Struct.Char;
	}
	
	private boolean isCharArray(Obj o) {
		return (o.getType().getKind() == Struct.Array) && (o.getType().getElemType().getKind() == Struct.Char);
	}
	
	private boolean isIntArray(Obj o) {
		return (o.getType().getKind() == Struct.Array) && (o.getType().getElemType().getKind() == Struct.Int);
	}
	
	private boolean isAssignableType(Obj o) {
		return (o.getKind() == Obj.Var) || (o.getKind() == Obj.Fld) || (o.getKind() == Obj.Elem);
	}
	
	

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Visiting program */

	// Program name - start of program
	//
	public void visit(ProgramName progName) {
		// log.info("Evo nas u program name");
		progName.obj = Tab.insert(Obj.Prog, progName.getName(), Tab.noType);
		Tab.openScope();
	}

	// Program - end of program
	//
	public void visit(Program prog) {
		// log.info("Evo nas u program");
		if (globalVarCount > 65536) {
			reportError("Program is using more than 65536 global variables - forbidden.", null);
		}

		if (!mainFound) {
			reportError("No main method found", null);
		}

		Tab.chainLocalSymbols(prog.getProgramName().obj);
		Tab.closeScope();
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Visiting type */

	public void visit(Type t) {
		Obj typeNode = Tab.find(t.getName());
		if (typeNode == Tab.noObj) {
			reportError("Name " + t.getName() + " not found in symbol table.", null);
			currType = t.struct = Tab.noType;
		} else {
			if (typeNode.getKind() == Obj.Type) {
				currType = t.struct = typeNode.getType();
			} else {
				reportError("Name " + t.getName() + " does not represent a type in symbol table.", t);
				currType = t.struct = Tab.noType;
			}
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Visiting variable declaration */

	// Local vars
	//
	public void visit(VarDeclSingle varDecl) {
		if (findSymbolInCurrentScope(varDecl.getVarName())) {
			reportError("Variable with name " + varDecl.getVarName() + " already defined in current scope.", varDecl);
		} else {
			boolean isArray = (varDecl.getArrBracketsOption() instanceof ArrayBrackets);
			if (currMethod != Tab.noObj && currRecord == Tab.noObj) {
				localVarCount++;
			} else if (currMethod == Tab.noObj && currRecord != Tab.noObj) {
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
		if (findSymbolInCurrentScope(globalVarDecl.getVarName())) {
			reportError("Global variable with name " + globalVarDecl.getVarName() + " is already defined.",
					globalVarDecl);
		} else {
			boolean isArray = (globalVarDecl.getArrBracketsOption() instanceof ArrayBrackets);
			insertVarToTable(globalVarDecl.getVarName(), isArray);
			globalVarCount++;
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Visiting const assignment */

	public void visit(ConstAssign constAssign) {
		if (findSymbolInCurrentScope(constAssign.getConstName())) {
			reportError("Constant with name " + constAssign.getConstName() + " is already defined.", constAssign);
		} else {
			if (!insertConstToTable(constAssign.getConstName(), constAssign.getConstVal())) {
				reportError("Invalid constant type", constAssign);
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
		if (findSymbolInTable(recordName.getName())) {
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
		if (findSymbolInTable(methodName.getName())) {
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

		if (name.equals("main")) {
			if (type instanceof MethodTypeVoid) {
				if (mainFound) {
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
		if (name.equals("main")) {
			reportError("Main method must have no parameters.", methodSignature);
		}
	}

	// Method signature with var args
	//
	public void visit(MethodSignatureVarArgs methodSignature) {
		String name = methodSignature.getMethodName().getName();
		if (name.equals("main")) {
			reportError("Main method must have no parameters.", methodSignature);
		}
	}

	// Method declaration - end of method
	//
	public void visit(MethodDeclaration method) {
		if ((currMethodType != Tab.noType) && !currMethodReturnFound) {
			reportError("Missing return statement for method of type " + currMethodTypeName, method);
			return;
		}

		if (localVarCount > 256) {
			reportError("Method " + currMethod.getName() + " has more than 256 local variables.", method);
			return;
		}
		
		Tab.closeScope();

		resetMethodData();
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Visiting formal parameters */

	public void visit(FormParsMultiple formPars) {
		// at this moment current scope will contain only formal params visited so far
		//
		SymbolDataStructure pars = Tab.currentScope().getLocals();
		currMethod.setLocals(pars);
	}

	// Single Formal parameter visit
	//
	public void visit(FormParSingle formPar) {
		FormParSingle param = (FormParSingle) formPar;

		if (findSymbolInCurrentScope(param.getFormParName()) || currMethod.getName().equals(param.getFormParName())) {
			reportError("Name of formal parameter " + param.getFormParName() + " is already defined.", param);
			return;
		}

		boolean isArray = (param.getArrBracketsOption() instanceof ArrayBrackets);
		insertVarToTable(param.getFormParName(), isArray);

		currMethod.setLevel(currMethod.getLevel() + 1);
	}

	// VarArgs visit
	//
	public void visit(VarArgsDeclaration varArgs) {
		String varArgName = varArgs.getVarArgsName();
		if (findSymbolInCurrentScope(varArgName)) {
			reportError("Name of var arg " + varArgName + " is already defined.", varArgs);
			return;
		}
		// insert var args to table as an array
		//
		insertVarToTable(varArgName, true);
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Visiting statements */

	// return
	//
	public void visit(SingleReturnStatement ret) {
		if (currMethod == Tab.noObj) {
			reportError("Return statement found outside the method", ret);
			return;
		}

		if (currMethodType != Tab.noType) {
			reportError("Return statement without expression is invalid for method of type " + currMethodTypeName, ret);
			return;
		}

		currMethodReturnFound = true;
	}

	// return expr
	//
	public void visit(SingleReturnExprStatement ret) {
		if (currMethod == Tab.noObj) {
			reportError("Return statement found outside the method", ret);
			return;
		}

		if (currMethodType == Tab.noType) {
			reportError("Return statement with expression is invalid for method of type void", ret);
			return;
		}

		Struct returnExprType = ret.getExpr().obj.getType();

		if (!returnExprType.compatibleWith(currMethodType)) {
			reportError("Return statement returning wrong type, " + currMethodTypeName + " expected", ret);
		}

		currMethodReturnFound = true;
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visiting designator statements */
	
	// Assignment e.g. x = 5
	//
	public void visit(DesignatorStmtAssignCorrect stmt) {
		Obj designator = stmt.getDesignator().obj;
		if(designator == null) {
			return;
		}
		
		if(!isAssignableType(designator)) {
			reportError("Symbol " + designator.getName() + " is used like an assignable type but is not.", stmt);
			return;
		}
		
		Obj expr = stmt.getExpr().obj;
		
		if(expr == null) {
			return;
		}
		
		//reportInfo(expr.getType().getKind()+" "+designator.getType().getKind(),null);
		if(!expr.getType().assignableTo(designator.getType())) {
			reportError("Invalid assignment. Types are incompatible.", stmt);
			return;
		}
		
	}
	
	// Increment e.g. x++
	//
	public void visit(DesignatorStmtPlusPlus stmt) {
		Obj designator = stmt.getDesignator().obj;
		if(designator == null) {
			return;
		}
		if(!isAssignableType(designator)) {
			reportError("Invalid increment. Object must be variable, element or field.", stmt);
			return;
		}
		if(!isInt(designator)) {
			reportError("Invalid increment. Type must be int.", stmt);
		}
	}
	
	// Decrement e.g. x--
	//
	public void visit(DesignatorStmtMinusMinus stmt) {
		Obj designator = stmt.getDesignator().obj;
		if(designator == null) {
			return;
		}
		if(!isAssignableType(designator)) {
			reportError("Invalid decrement. Object must be variable, element or field.", stmt);
			return;
		}
		if(!isInt(designator)) {
			reportError("Invalid decrement. Type must be int.", stmt);
		}
	}
	
	public void visit(DesignatorStmtMethodCall stmt) {
		//
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Visiting expressions, terms, factors */

	// Expression which is a single term e.g 6*8
	//
	public void visit(SingleTermExprPositive expr) {
		expr.obj = expr.getTerm().obj;
	}

	// Expression which is a single term with minus sign e.g. -9
	//
	public void visit(SingleTermExprNegative expr) {
		if (expr.getTerm().obj.getType() != Tab.intType) {
			reportError("Minus sign cannot be used with non-number factors", expr);
			return;
		}
		expr.obj = expr.getTerm().obj;
	}

	// Expression which is a combination of terms with addition operations e.g 7*8 +
	// 3*2
	//
	public void visit(MultiTermExpr expr) {
		Obj lhs = expr.getExpr().obj;
		Obj rhs = expr.getTerm().obj;
		Addop addop = expr.getAddop();
		if (!(lhs.getType().equals(Tab.intType) && rhs.getType().equals(Tab.intType))) {
			reportError("Invalid argument types for operation: " + getAddopName(addop), expr);
			return;
		}
		expr.obj = new Obj(Obj.Var, null, Tab.intType);
	}

	// Term which is a single factor e.g. 7
	//
	public void visit(SingleFactorTerm term) {
		term.obj = term.getFactor().obj;
	}

	// Term which is connecting Term and Factor with mulop
	//
	public void visit(MultiFactorTerm term) {
		Obj lhs = term.getTerm().obj;
		Obj rhs = term.getFactor().obj;
		Mulop mulop = term.getMulop();
		if (!(lhs.getType().equals(Tab.intType) && rhs.getType().equals(Tab.intType))) {
			reportError("Invalid argument types for operation: " + getMulopName(mulop), term);
			return;
		}
		term.obj = new Obj(Obj.Var, null, Tab.intType);
	}

	// Number constant factor e.g. 1
	//
	public void visit(NumConstFactor factor) {
		factor.obj = new Obj(Obj.Con, null, Tab.intType);
		factor.obj.setAdr(factor.getN1());
	}

	// Char constant factor e.g. a
	//
	public void visit(CharConstFactor factor) {
		factor.obj = new Obj(Obj.Con, null, Tab.charType);
		factor.obj.setAdr(factor.getC1());
	}

	// Boolean constant factor e.g. true
	//
	public void visit(BoolConstFactor factor) {
		factor.obj = new Obj(Obj.Con, null, boolType);
		factor.obj.setAdr(factor.getB1() ? 1 : 0);
	}

	// Expression factor e.g. (2*6 + 3*5)
	//
	public void visit(ExprFactor factor) {
		factor.obj = factor.getExpr().obj;
	}

	// Designator factor e.g. x
	//
	public void visit(DesignatorEmptyFactor factor) {
		Obj designatorObj = factor.getDesignator().obj;
		if (!isAssignableType(designatorObj)){
			reportError("Symbol " + designatorObj.getName() + " is used like an assignable type but is not.", factor);
			return;
		}
		factor.obj = designatorObj;
	}
	
	// Method call factor e.g foo(3, 4, 5)
	//
	public void visit(MethodCallFactor factor) {
		 factor.obj = factor.getMethodCall().obj;
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Visting Designators */

	// a
	//
	public void visit(IdentDesignator designator) {
		if (!findSymbolInTable(designator.getIdent())) {
			reportError("Symbol " + designator.getIdent() + " is not defined.", designator);
			return;
		}

		designator.obj = Tab.find(designator.getIdent());
	}

	// a[1]
	//
	public void visit(IdentArrayDesignator designator) {
		if (!findSymbolInTable(designator.getIdent())) {
			reportError("Symbol " + designator.getIdent() + " is not defined.", designator);
			return;
		}

		Obj symbolNode = Tab.find(designator.getIdent());

		if (symbolNode.getType().getKind() != Struct.Array) {
			reportError("Symbol " + designator.getIdent() + " is used like an array but is not.", designator);
			return;
		}

		Obj expr = designator.getExpr().obj;

		if (expr.getType() != Tab.intType) {
			reportError("Expression in array braces does not evaluate to integer type.", designator);
			return;
		}

		designator.obj = new Obj(Obj.Elem, symbolNode.getName(), symbolNode.getType().getElemType());
	}

	// rec.a
	//
	public void visit(IdentMemberDesignator designator) {
		if (!findSymbolInTable(designator.getIdent())) {
			reportError("Symbol " + designator.getIdent() + " is not defined.", designator);
			return;
		}

		Obj owner = Tab.find(designator.getIdent());

		if ((owner.getKind() != Obj.Type) || owner.getType().getKind() != Record) {
			reportError("Symbol " + designator.getIdent() + " is used like a record but is not.", designator);
			return;
		}

		SymbolDataStructure ownersMembers = owner.getType().getMembersTable();

		Obj member = ownersMembers.searchKey(designator.getMemberName());
		if (member.equals(Tab.noObj)) {
			reportError("Symbol " + designator.getMemberName() + " is not defined under " + designator.getIdent(),
					designator);
			return;
		}

		designator.obj = member;
	}

	// rec.a[4]
	//
	public void visit(IdentMemberArrayDesignator designator) {
		if (!findSymbolInTable(designator.getIdent())) {
			reportError("Symbol " + designator.getIdent() + " is not defined.", designator);
			return;
		}

		Obj owner = Tab.find(designator.getIdent());

		if ((owner.getKind() != Obj.Type) || owner.getType().getKind() != Record) {
			reportError("Symbol " + designator.getIdent() + " is used like a record but is not.", designator);
			return;
		}

		SymbolDataStructure ownersMembers = owner.getType().getMembersTable();

		Obj member = ownersMembers.searchKey(designator.getMemberArrayName());
		if (member.equals(Tab.noObj)) {
			reportError("Symbol " + designator.getMemberArrayName() + " is not defined under " + designator.getIdent(),
					designator);
			return;
		}

		if (member.getKind() != Obj.Var || member.getType().getKind() != Struct.Array) {
			reportError("Symbol " + designator.getIdent() + " is used like an array but is not.", designator);
			return;
		}

		Obj expr = designator.getExpr().obj;

		if (expr.getType() != Tab.intType) {
			reportError("Expression in array braces does not evaluate to integer type.", designator);
			return;
		}

		designator.obj = new Obj(Obj.Elem, member.getName(), member.getType().getElemType());
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visiting actual parameters and method call */
	
	
	// Single actual parameter (expression)
	//
	public void visit(ActParsSingleExpr pars) {
		ArrayList<Obj> actParams = listsOfActParamsStack.pop();
		actParams.add(pars.getExpr().obj);
		listsOfActParamsStack.push(actParams);
	}
	
	public void visit(ActParsMultiExpr pars) {
		ArrayList<Obj> actParams = listsOfActParamsStack.pop();
		actParams.add(pars.getExpr().obj);
		listsOfActParamsStack.push(actParams);
	}
	
	// Left Paren - start of act pars
	//
	public void visit(ActParsLeftParen parsLParen) {
		listsOfActParamsStack.push(new  ArrayList<Obj>());
	}
	
	// Right Paren - end of act pars
	//
	public void visit(ActParsRightParen parsRParen) {
		// nothing
	}
	
	// Method call - with or without params
	//
	public void visit(MethodCall call) {
		Obj methodDesignator = call.getDesignator().obj;
		if (methodDesignator.getKind() != Obj.Meth){
			reportError("Symbol " + methodDesignator.getName() + " is used like a method but is not a method.", call);
			return;
		}
		
		// method call with params
		//
		if(call.getActParsOption() instanceof ActParsYes) {
			ArrayList<Obj> actualParams = listsOfActParamsStack.pop();
			if(actualParams.size() != methodDesignator.getLevel()) {
				reportError("Invalid number of function parameters.", call);
				return;
			}
			
			// predefined chr(e)
			//
			if(methodDesignator.getName().equals("chr")) {
				if(!isInt(actualParams.get(0))) {
					reportError("Wrong parameter type for predefined method chr. Expected int.",call);
					return;
				}
			}
			
			// predefined ord(c)
			//
			if(methodDesignator.getName().equals("ord")) {
				if(!isChar(actualParams.get(0))) {
					reportError("Wrong parameter type for predefined method ord. Expected char.",call);
					return;
				}
			}
			
			// predefined len(a)
			//
			if(methodDesignator.getName().equals("len")) {
				if(!isIntArray(actualParams.get(0)) && !isCharArray(actualParams.get(0))) {
					reportError("Wrong parameter type for predefined method len. Expected array.",call);
					return;
				}
			}
			
			// user defined method
			//
			ArrayList<Obj> formalArgs = new ArrayList<Obj>(methodDesignator.getLocalSymbols());
			if(actualParams.size() != formalArgs.size()) {
				reportError("Invalid number of function parameters.", call);
				return;
			}
			int i = 0;
			for(Obj arg: formalArgs) {
				Obj param = actualParams.get(i++);
				if(!param.getType().assignableTo(arg.getType())) {
					reportError("Type of paramater incompatible with argument type.",call);
					return;
				}
			}
			
			call.obj = methodDesignator;
		}
		
		// method call without params
		//
		else if(call.getActParsOption() instanceof ActParsNo) {
			
		}
		
		// undefined method call
		//
		else {
			reportError("Invalid method call",call);
		}
		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**/


}

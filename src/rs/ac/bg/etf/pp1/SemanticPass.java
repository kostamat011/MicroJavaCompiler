package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticPass extends VisitorAdaptor {
	
	// visit statistics
	//
	int printCallCount = 0;
	int globalVarCount = 0;
	int localVarCount = 0;
	int membersDesignators = 0;
	int membersArraysDesignators = 0;
	int nestedDesignators = 0;
	int ifcnt = 0;
	int ifelsecnt = 0;
	int varArgsCnt = 0;
	
	Logger log = Logger.getLogger(getClass());
	
	// current type for vars and constants
	//
	private Struct currType;
	
	// Symbol table extensions
	//
	public static final int Record = 8;
	public static final Struct boolType = new Struct(Struct.Bool);
	public static final Struct recordType = new Struct(Record);
	
	// init symbol table before semantic pass begins
	//
	public SemanticPass() {
		Tab.init();
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "record", recordType));
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
		
		// init current type to no type
		//
		currType = Tab.noType;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Errors util methods */
	
	private boolean error = false;
	
	private void reportError(String msg, SyntaxNode node) {
		error = true;
		log.error(msg);	
	}
	
	private void reportInfo(String msg, SyntaxNode node) {
		log.info(msg);
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Other util methods */
	
	private boolean findSymbol(String name) {
		return (Tab.find(name) != Tab.noObj);
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visiting program */
	
	// Program name - start of program
	//
	public void visit(ProgramName progName) {
		log.info("Evo nas u program name");
		progName.obj = Tab.insert(Obj.Prog, progName.getName(), Tab.noType);
		Tab.openScope();
	}
	
	
	// Program - end of program
	//
	public void visit(Program prog) {
		log.info("Evo nas u program");
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
	
	/* Visiting var declaration */
	
	public void visit(VarDeclSingle varDecl) {
		if(Tab.currentScope.findSymbol(varDecl.getVarName()) != null) {
			reportError("Variable name already defined in current scope.", varDecl);
		}
		
		if(varDecl.getArrBracketsOption() != null) {
			Tab.insert(Obj.Var, varDecl.getVarName(), new Struct(Struct.Array, currType));
		} else {
			Tab.insert(Obj.Var, varDecl.getVarName(), currType);
		}
		
		localVarCount++;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */



	/*public void visit(GlobalVarDeclarationSingle vardecl){
		globalVarDeclCount++;
	}
	
    public void visit(IfStatement print) {
		ifcnt++;
	}
    
    public void visit(IfElseStatement print) {
		ifelsecnt++;
	}
    
    public void visit(IdentMemberDesignator print) {
    	membersDesignators++;
    }
    
    public void visit(IdentMemberArrayDesignator print) {
    	membersArraysDesignators++;
    }
    
    public void visit(NestedDesignator print) {
    	nestedDesignators++;
    }
    
    public void visit(VarArgsDeclaration print) {
    	varArgsCnt++;
    }*/
}

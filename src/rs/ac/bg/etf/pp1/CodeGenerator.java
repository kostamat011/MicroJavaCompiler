package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class CodeGenerator extends VisitorAdaptor {

	// address of main method
	//
	private int mainPc;
	
	// error found yes or no
	//
	private boolean error = false;
	
	// type of var args for current method call
	//
	private Struct currVarArgsType = Tab.noType;
	
	// hash map for keeping info about varArgs 
	// 0  = char type of var args
	// 1  = int type of var args
	//
	private HashMap<String, Integer> varArgsMethods = new HashMap<String, Integer>();
	
	// stack of lists of actual params for method calls
	//
	private Stack<ArrayList<Object>> actParamsStack = new Stack<ArrayList<Object>>();
	
	public CodeGenerator() {
		generatePredefinedMethods();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Errors and infos util methods */
	
	Logger log = Logger.getLogger(getClass());

	private void reportError(String msg, SyntaxNode node) {
		error = true;
		if(node != null) {
			int line = node.getLine();
			log.error("Line " + line + ": " + msg);
		} else {
			log.error(msg);
		}
	}

	private void reportInfo(String msg, SyntaxNode node) {
		if(node != null) {
			int line = node.getLine();
			log.info("Line " + line + ": " + msg);
		} else {
			log.info(msg);
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Utility */
	
	// generate code for ord, chr and len methods
	//
	private void generatePredefinedMethods() {
		int startAddrChr = Code.pc;
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_1);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		int startAddrOrd = Code.pc;
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_1);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		int startAddrLen = Code.pc;
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_1);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		try {
			Tab.find("chr").setAdr(startAddrChr);
			Tab.find("ord").setAdr(startAddrOrd);
			Tab.find("len").setAdr(startAddrLen);
		} catch (Exception e) {
			reportError("Error in generating pre-defined methods code.",null);
		}
	}
	
	private int countFormalPars(Obj method) {
		if(method.getKind() != Obj.Meth) {
			reportError("Trying to count formal params of non-method type",null);
			return 0;
		}
		ArrayList<Obj> locals = new ArrayList(method.getLocalSymbols());
		int cnt = 0;
		for(Obj o : locals) {
			if(o.getType().equals(SemanticPass.methodDividerType)) {
				break;
			}
			cnt++;
		}
		return cnt;
	}
	
	private int countLocalVars(Obj method) {
		if(method.getKind() != Obj.Meth) {
			reportError("Trying to count formal params of non-method type",null);
			return 0;
		}
		ArrayList<Obj> locals = new ArrayList(method.getLocalSymbols());
		int cnt = 0;
		boolean dividerFound = false;
		for(Obj o : locals) {
			if(dividerFound) {
				cnt++;
			}
			if(o.getType().equals(SemanticPass.methodDividerType)) {
				dividerFound = true;
			}
		}
		return cnt;
	}
	
	private ArrayList<Object> takeLastN(ArrayList a, int n) {
		int target = a.size() - n;
		ArrayList<Object> ret = new ArrayList();
		for(int i=0; i<a.size(); ++i) {
			if(i>=target) {
				ret.add(a.get(i));
			}
		}
		return ret;
	}
	
	private void handleMulop(Mulop mul) {
		if(mul instanceof MulopMul) {
			Code.put(Code.mul);
			return;
		}
		
		if (mul instanceof MulopDiv) {
			Code.put(Code.div);
			return;
		}
		
		if (mul instanceof MulopPct) {
			Code.put(Code.rem);
			return;
		}
	}
	
	private void handleAddop(Addop add) {
		if(add instanceof AddopPlus) {
			Code.put(Code.add);
			return;
		}
		
		if(add instanceof AddopMinus) {
			Code.put(Code.sub);
			return;
		}
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visit method signatures */
	
	// foo(int ... args)
	//
	public void visit(MethodSignatureVarArgsOnly signature) {
		
		// store 0 for char var args, 1 for int
		//
		int value = (currVarArgsType.equals(Tab.charType)) ? 0 : 1;
		varArgsMethods.put(signature.getMethodName().getName(), 0);
		
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
	}
	
	// foo(int a, int b)
	//
	public void visit(MethodSignatureParams signature) {		

		Obj method = Tab.find(signature.getMethodName().getName());
		
		// locals contain formal args + local vars + 1 divider 
		//
		int formParsNum = countFormalPars(method);
		int totalLocalsNum = formParsNum + countLocalVars(method);
		
		Code.put(Code.enter);
		Code.put(formParsNum);
		Code.put(totalLocalsNum);
	}
	
	// foo(int a, int b, int ... args)
	//
	public void visit(MethodSignatureVarArgs signature) {

		Obj method = Tab.find(signature.getMethodName().getName());
		
		// locals contain formal args + local vars + 1 divider 
		//
		int formParsNum = countFormalPars(method);
		int totalLocalsNum = formParsNum + countLocalVars(method);
		
		// store 0 for char var args, 1 for int
		//
		int value = (currVarArgsType.equals(Tab.charType)) ? 0 : 1;
		varArgsMethods.put(signature.getMethodName().getName(), value);
		
		Code.put(Code.enter);
		Code.put(formParsNum);
		Code.put(totalLocalsNum);
	}
	
	// foo()
	//
	public void visit(MethodSignaturePlain signature) {
		MethodName name = signature.getMethodName();
		if(name.getName().equals("main")) {
			mainPc = Code.pc;
		}
		Code.put(Code.enter);
		Code.put(0);
		Code.put(0);
	}
	
	// returning from void method without return statement
	//
	public void visit(MethodDeclaration ret) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// visiting var args
	//
	public void visit(VarArgs var) {
		Struct type = Tab.noType;
		if(var.getParent() instanceof MethodSignatureVarArgsOnly) {
			MethodSignatureVarArgsOnly sig = (MethodSignatureVarArgsOnly)var.getParent();
			type = sig.getType().struct;
		}
		
		else if(var.getParent() instanceof MethodSignatureVarArgs) {
			MethodSignatureVarArgs sig = (MethodSignatureVarArgs)var.getParent();
			type = sig.getType().struct;
		}
		
		else {
			reportError("Unexpected varargs",var);
			
		}
		
		currVarArgsType = type;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visiting Method calls */
	
	public void visit(MethodCall call) {
		
		// all act pars are currently on stack 
		
		// if method has varArgs we must pack the parameters into array
		//
		Obj method = call.getDesignator().obj;
		if(varArgsMethods.containsKey(call)) {
			
			// if method call had var args then number of var args
			// was stored to obj address during semantic pass
			//
			Obj callObj = call.obj;
			int num = callObj.getAdr();
			
			// last num actual pars should form array
			// pop them from stack
			//
			for(int i=0; i<num; ++i) {
				Code.put(Code.pop);
			}

			// array type is stored in varArgs hashmap
			//
			int varArgType = varArgsMethods.get(method.getName());
			
			// creating array of num elements
			//
			Code.put(Code.const_);
			Code.put4(num);
			
			// for int allocate num words, for char allocate num bytes
			//
			Code.put4((varArgType==1) ? (4*num) : num);
			Code.put(Code.newarray);
			Code.put(varArgType);
			
			// populating array with last num actual pars
			//
			Obj tmp = Tab.find("varArgsTemp");
			if(tmp == Tab.noObj) {
				reportError("Code generator failure", null);
				return;
			}
			ArrayList<Object> pars = actParamsStack.pop();
			pars = takeLastN(pars, num);
			for(int i=0; i<num; ++i) {
				// save address of array
				//
				Code.put(Code.putstatic);
				Code.put2(tmp.getAdr());
				
				// put index on stack
				//
				Code.put(Code.const_);
				Code.put4(i);
				
				// put param value on stack
				//
				Code.put(Code.const_);
				if(varArgType == 0) {
					Code.put4((Character)pars.get(i));
				} else {
					Code.put4((Integer)pars.get(i));
				}
				
				// store param in array
				//
				Code.put(Code.astore);
				
				// put array address on stack
				//
				Code.put(Code.getstatic);
				Code.put2(tmp.getAdr());
			}
		}
		
		// call address is relative to PC 
		//
		int relAddr = method.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(relAddr);
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visiting Factors */
	
	public void visit(NumConstFactor factor) {
		Code.load(factor.obj);
	}
	
	public void visit(CharConstFactor factor) {
		Code.load(factor.obj);
	}
	
	public void visit(BoolConstFactor factor) {
		Code.load(factor.obj);
	}
	
	public void visit(ExprFactor factor) {
		// do nothing bcz that expression is already on stack
		//
	}
	
	public void visit(DesignatorEmptyFactor factor) {
		// done in designator visit
	}
	
	public void visit(MethodCallFactor factor) {
		// done in MethodCall visit
	}
	
	public void visit(NewTypeFactor factor) {
		// empty
	}
	
	public void visit(NewTypeArrayFactor factor) {
		// n is already on stack from expression visit
		// must calculate size based on type
		//
		int isInt = 0;
		Struct type = factor.getType().struct;
		if(type.equals(Tab.intType)) {
			isInt = 1;
		}
		
		Code.put(Code.newarray);
		Code.put(isInt);
	}
	
	/* Visiting terms */
	
	public void visit(SingleFactorTerm term) {
		// do nothing because that factor is already on stack
		//
	}

	public void visit(MultiFactorTerm term) {
		handleMulop(term.getMulop());
	}
	
	/* Visiting expressions */
	
	public void visit(SingleTermExprPositive expr) {
		// do nothing because that term is already on stack
		//
	}
	
	public void visit(SingleTermExprNegative expr) {
		Code.put(Code.const_m1);
		Code.put(Code.mul);
	}
	
	public void visit(MultiTermExpr expr) {
		handleAddop(expr.getAddop());
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visiting designators statements */
	
	public void visit(DesignatorStmtAssignCorrect assign) {
		Code.store(assign.getDesignator().obj);	
	}
	
	// In array element assignment a[expr] = expr;
	// both expressions will be added to stack in expression visit
	// no need to visit ArrayDesignator, its enough to put array var on stack
	//
	public void visit(ArrayDesignatorStart desig) {
		Code.load(desig.obj);
	}
	
	public void visit(DesignatorStmtPlusPlus stmt) {
		Obj d = stmt.getDesignator().obj;
		
		// var increment
		//
		if(d.getKind() == Obj.Var) {
			Code.load(d);
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.store(d);
		}
		
		// array elem increment
		//
		else if(stmt.getDesignator().obj.getKind() == Obj.Elem) {
			// stack: array, index
			//
			Code.put(Code.dup2);
			
			// stack: array, index, array, index
			//
			Code.load(d);
			
			// stack: array, index, array[index]
			//
			Code.put(Code.const_1);
			Code.put(Code.add);
			
			// stack: array, index, array[index] + 1
			//
			Code.put(Code.astore);
			
			// stack: empty
			//
		}
	}
	
	public void visit(DesignatorStmtMinusMinus stmt) {
		Obj d = stmt.getDesignator().obj;
		
		// var decrement
		//
		if(d.getKind() == Obj.Var) {
			Code.load(d);
			Code.put(Code.const_m1);
			Code.put(Code.add);
			Code.store(d);
		}
		
		// array elem decrement
		//
		else if(stmt.getDesignator().obj.getKind() == Obj.Elem) {
			// stack: array, index
			//
			Code.put(Code.dup2);
			
			// stack: array, index, array, index
			//
			Code.load(d);
			
			// stack: array, index, array[index]
			//
			Code.put(Code.const_m1);
			Code.put(Code.add);
			
			// stack: array, index, array[index] - 1
			//
			Code.put(Code.astore);
			
			// stack: empty
			//
		}
	}
	
	public void visit(DesignatorStmtMethodCall stmt) {
		// done in method call visit
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */	
	
}

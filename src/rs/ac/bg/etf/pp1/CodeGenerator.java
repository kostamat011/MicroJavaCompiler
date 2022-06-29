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

	// constants for sizes of var args array
	//
	public static final int BYTE_VARARGS_TYPE = 0;
	public static final int WORD_VARARGS_TYPE = 0;
	
	// address of main method
	//
	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}

	// error found yes or no
	//
	private boolean error = false;
	
	public boolean isError() {
		return error;
	}

	// type of var args for current method call
	//
	private Struct currVarArgsType = Tab.noType;
	
	// hash map for keeping info about varArgs 
	//
	private HashMap<String, Integer> varArgsMethods = new HashMap<String, Integer>();
	
	// stack of lists of actual params for method calls
	//
	private ArrayList<Object> currActParams = new ArrayList<Object>();
	private Stack<ArrayList<Object>> actParamsStack = new Stack<ArrayList<Object>>();
	
	public CodeGenerator() {
		generatePredefinedMethods();
		generateModificationMethod();
	}
	
	// stacks for keeping addresses for if and while jumps
	//
	private Stack<Integer> ifConditionsAddrStack = new Stack<Integer>();
	private Stack<Integer> doWhileAddrStack =  new Stack<Integer>();
	private Stack<Integer> breakAddrStack = new Stack<Integer>();
	private Stack<Integer> contAddrStack = new Stack<Integer>();
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* Errors and infos util methods */
	
	Logger log = Logger.getLogger(getClass());

	private void reportError(String msg, SyntaxNode node) {
		error = true;
		if(node != null) {
			int line = node.getLine();
			System.out.println("\n");
			System.out.println("Line " + line + " ERROR: " + msg);
			System.out.println("\n");
		} else {
			System.out.println("\n");
			System.out.println("ERROR: "+msg);
			System.out.println("\n");
		}
	}

	private void reportInfo(String msg, SyntaxNode node) {
		if(node != null) {
			int line = node.getLine();
			System.out.println("Line " + line + ": " + msg);
		} else {
			System.out.println(msg);
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
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		int startAddrOrd = Code.pc;
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		int startAddrLen = Code.pc;
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
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
	
	private void generateModificationMethod() {
		// modification - max element of array
		//
		int startAddrMod = Code.pc;
		Code.put(Code.enter);
		Code.put(1);
		Code.put(4);
		// stack: array_addr
		
		// local[0] will store array addr (passed as param)
		//
		
		// local[1] will store array length
		//
		Code.put(Code.load_n + 0);
		Code.put(Code.arraylength);
		Code.put(Code.store_n + 1);
		
		// local[2] will contain max val
		//
		Code.put(Code.const_n + 0);
		Code.put(Code.store_n + 2);
		
		// local[3] will contain counter
		//
		Code.put(Code.const_n + 0);
		Code.put(Code.store_n + 3);
		
		// check if counter reached array len
		//
		Code.put(Code.load_n + 3);
		Code.put(Code.load_n + 1);
		
		// if counter >= array_len jump to end
		//
		Code.put(Code.jcc + Code.eq);
		Code.put2(21); // 3 + 1 + 1 + 1 + 1 + 7 + 4 + 3 = 17
		
		// compare current array element to max
		//
		Code.put(Code.load_n + 2); // stack: max
		Code.put(Code.load_n + 0); // stack: max, addr
		Code.put(Code.load_n + 3); // stack: max, addr, cnt
		Code.put(Code.aload);      // stack: max, addr[cnt]
		
		// if max is >= current el, skip max assignment
		//
		Code.put(Code.jcc + Code.ge);
		Code.put2(7); // 3 + 1 + 1 + 1 + 1 = 7
		
		// assign current elem to max
		//
		Code.put(Code.load_n + 0); // stack: addr
		Code.put(Code.load_n + 3); // stack: addr, cnt
		Code.put(Code.aload); 	   // stack: addr[cnt]
		Code.put(Code.store_n + 2);
		
		// increment counter
		//
		Code.put(Code.load_n + 3);
		Code.put(Code.const_1);
		Code.put(Code.add);
		Code.put(Code.store_n + 3);
		
		// jump back to condition check
		//
		Code.put(Code.jmp);
		Code.put2(-20); // -4 -14 - 2 = -20
		
		// in the end after loop is finished put max on stack
		//
		Code.put(Code.load_n + 2);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		try {
			Tab.find("mod").setAdr(startAddrMod);
		} catch (Exception e) {
			reportError("Error in generatin modification code.",null);
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
	
	private int countLocalVars(Obj method, boolean hasPars) {
		if(method.getKind() != Obj.Meth) {
			reportError("Trying to count formal params of non-method type",null);
			return 0;
		}
		ArrayList<Obj> locals = new ArrayList(method.getLocalSymbols());
		int cnt = 0;
		boolean dividerFound = false;
		
		// if method has params, local vars will be after divider
		// if method has no params, all locals will be local vars
		//
		for(Obj o : locals) {
			if(dividerFound || !hasPars) {
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
	
	private boolean isInt(Obj o) {
		return o.getType().getKind() == Struct.Int;
	}
	
	private boolean isChar(Obj o) {
		return o.getType().getKind() == Struct.Char;
	}
	
	private boolean isBool(Obj o) {
		return o.getType().getKind() == Struct.Bool;
	}
	
	private int fetchRelopCode(Relop rel) {
		if(rel instanceof RelopGT) {
			return Code.gt;
		}
		
		if(rel instanceof RelopGTE) {
			return Code.ge;
		}
		
		if(rel instanceof RelopNOTEQUAL) {
			return Code.ne;
		}
		
		if(rel instanceof RelopEQUAL) {
			return Code.eq;
		}
		
		if(rel instanceof RelopLS) {
			return Code.lt;
		}
		
		if(rel instanceof RelopLSE) {
			return Code.le;
		}
		
		reportError("Invalid rel operation.",null);
		return 0;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visit method signatures */
	
	// foo(int ... args)
	//
	public void visit(MethodSignatureVarArgsOnly signature) {
		
		// store 0 for char var args, 1 for int
		//
		int value = (currVarArgsType.equals(Tab.charType)) ? BYTE_VARARGS_TYPE : WORD_VARARGS_TYPE;
		varArgsMethods.put(signature.getMethodName().getName(), value);
		
		Obj method = signature.getMethodName().obj;
		
		method.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		int totalLocalsNum = countLocalVars(method, true);
		Code.put(1 + totalLocalsNum);
	}
	
	// foo(int a, int b)
	//
	public void visit(MethodSignatureParams signature) {		

		Obj method = signature.getMethodName().obj;
		
		// locals contain formal args + local vars + 1 divider 
		//
		int formParsNum = countFormalPars(method);
		int totalLocalsNum = formParsNum + countLocalVars(method, true);
		
		method.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(formParsNum);
		Code.put(totalLocalsNum);
	}
	
	// foo(int a, int b, int ... args)
	//
	public void visit(MethodSignatureVarArgs signature) {

		Obj method = signature.getMethodName().obj;
		
		// locals contain formal args + local vars + 1 divider 
		//
		int formParsNum = countFormalPars(method);
		int totalLocalsNum = formParsNum + countLocalVars(method,true);
		
		// store 0 for char var args, 1 for int
		//
		int value = (currVarArgsType.equals(Tab.charType)) ? BYTE_VARARGS_TYPE : WORD_VARARGS_TYPE;
		varArgsMethods.put(signature.getMethodName().getName(), value);
		
		method.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(formParsNum);
		Code.put(totalLocalsNum);
		
	}
	
	// foo()
	//
	public void visit(MethodSignaturePlain signature) {
		MethodName name = signature.getMethodName();
		Obj method = signature.getMethodName().obj;
		if(name.getName().equals("main")) {
			mainPc = Code.pc;
		}
		method.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(0);
		int totalLocalsNum = countLocalVars(method,false);
		Code.put(totalLocalsNum);
		
	}
	
	// returning from method will be always the same
	// if there is return statement, expr will aready be on stack
	//
	public void visit(MethodDeclaration ret) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// visiting var args
	//
	public void visit(VarArgsDeclaration var) {
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
		if(varArgsMethods.containsKey(method.getName())) {
			
			// if method call had var args then number of var args
			// was stored to obj address during semantic pass
			//
			Obj callObj = call.obj;
			int num = callObj.getAdr();

			// array type is stored in varArgs hashmap
			//
			int varArgType = varArgsMethods.get(method.getName());
			
			// creating array of num elements
			//
			Code.put(Code.const_);
			Code.put4(num);
			
			// for int allocate num words, for char allocate num bytes
			//
			Code.put(Code.newarray);
			Code.put(varArgType);
			
			// save created array addr in temp global var
			//
			Code.put(Code.putstatic);
			Code.put2(1);
			
			for(int i=num-1; i>=0; i--) {
				//stack: param1, param2, param3
				Code.put(Code.getstatic);
				Code.put2(1);
				
				// stack: param1, param2, param3, array
				Code.put(Code.dup2);
	
				// stack: param 1, param 2, param3, array, param3, array
				Code.put(Code.pop);
				
				// stack: param1, param2, param3, array, param3
				Code.put(Code.putstatic);
				Code.put2(0);
				
				// stack: param1, param2, param3, array
				Code.put(Code.const_);
				Code.put4(i);
				
				//stack: param1, param2, param3, array, index
				Code.put(Code.getstatic);
				Code.put2(0);
				
				//stack: param1, param2, param3, array, index, param3
				if(varArgType == 0) {
					Code.put(Code.bastore);
				} else {
					Code.put(Code.astore);
				}
				
				//stack: param1, param2, param3
				Code.put(Code.pop);
				
				//stack: param1, param2
			}

			// put formed array on stack as param
			//
			Code.put(Code.getstatic);
			Code.put2(1);
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
	
	public void visit(MaxArrayFactor factor) {
		Obj a = Tab.find(factor.getArrayName());
		if(a == Tab.noObj) {
			reportError("No array with name "+ factor.getArrayName(), factor);
			return;
		}
		Code.load(a);
		int callOffset = Tab.find("mod").getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(callOffset);
	}
	
	public void visit(DesignatorEmptyFactor factor) {
		// all loading is handled here
		// because factors can only appear on right side of the expression
		//
		if(factor.getDesignator() instanceof IdentDesignator) {
			Code.load(factor.getDesignator().obj);
		} else if(factor.getDesignator() instanceof IdentArrayDesignator) {
			// array name and index expr are already on stack
			// only put load command
			//
			if(factor.getDesignator().obj.getType() == Tab.intType) { 
				Code.put(Code.aload);
			} else {
				Code.put(Code.baload);
			}
		} else if(factor.getDesignator() instanceof IdentMemberDesignator) {
			Code.put(Code.getfield);
			Code.put2(factor.getDesignator().obj.getAdr());
		} else if(factor.getDesignator() instanceof IdentMemberArrayDesignator) {
			Code.put(Code.aload);
		}
	}
	
	public void visit(MethodCallFactor factor) {
		// done in MethodCall visit
	}
	
	public void visit(NewTypeFactor factor) {
		Struct type = factor.getType().struct;
		int membersCnt = new ArrayList(type.getMembers()).size();
		int size = membersCnt;
		size *= 4;
		Code.put(Code.new_);
		Code.put2(size);
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
		if(assign.getDesignator() instanceof IdentMemberDesignator) {
			Code.put(Code.putfield);
			Code.put2(assign.getDesignator().obj.getAdr());
		} else
			Code.store(assign.getDesignator().obj);	
	}
	
	// In array element designator a[expr]
	// expression will be added to stack in expression visit
	// array addres must be added to stack before expression 
	// so this cannot be done in Designator visit
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
		
		// field increment
		//
		if(d.getKind() == Obj.Fld) {
			
			// stack: addr
			//
			Code.put(Code.dup);
			
			// stack: addr addr
			//
			Code.put(Code.getfield);
			Code.put2(d.getAdr());
			
			// stack: addr val
			//
			Code.put(Code.const_1);
			Code.put(Code.add);
			
			//stack: addr val+1
			//
			Code.put(Code.putfield);
			Code.put2(d.getAdr());
		}
		
		// array elem increment
		//
		else if(stmt.getDesignator().obj.getKind() == Obj.Elem) {
			// stack: array, index
			//
			Code.put(Code.dup);
			
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
		if(d.getKind() == Obj.Var || d.getKind() == Obj.Fld) {
			Code.load(d);
			Code.put(Code.const_m1);
			Code.put(Code.add);
			Code.store(d);
		}
		
		// field decrement
		//
		if(d.getKind() == Obj.Fld) {
			
			// stack: addr
			//
			Code.put(Code.dup2);
			
			// stack: addr addr
			//
			Code.put(Code.getfield);
			Code.put2(d.getAdr());
			
			// stack: addr val
			//
			Code.put(Code.const_m1);
			Code.put(Code.add);
			
			//stack: addr val-1
			//
			Code.put(Code.putfield);
			Code.put2(d.getAdr());
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
	
	/* Visiting record designators */
	
	// rec.a
	//
	public void visit(IdentMemberDesignator designator) {
		// do nothing, because its not same for load and store
	}
	
	// rec
	//
	public void visit(RecordDesignatorStart designator) {
		// here put records addr on stack
		//
		Code.load(designator.obj);
	}
	

	// rec.a ([4])
	//
	public void visit(RecordDesignatorArrayStart designator) {
		// records addr is on stack
		// need to get arrays addr by getfield
		//
		Code.put(Code.getfield);
		Code.put2(designator.obj.getAdr());
	}

	// rec.a[4]
	//
	public void visit(IdentMemberArrayDesignator designator) {
		// nothing
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */	
		
	/* Visiting print and read statements */
	
	public void visit(SinglePrintStatement stmt) {
		Obj expr = stmt.getExpr().obj;
		int numConst = 1;
		if(stmt.getNumConstOption() instanceof NumConstYes) {
			numConst = ((NumConstYes)stmt.getNumConstOption()).getN1();
		}
		
		// printing int
		//
		if(isInt(expr) || isBool(expr)) {
			if(numConst == 1) {
				Code.put(Code.const_5);
				Code.put(Code.print);
			}
			else {
				Code.put(Code.const_5);
				Code.put(Code.print);
				for(int i=1; i < numConst; ++i) {
					Code.load(expr);
					Code.put(Code.const_5);
					Code.put(Code.print);
				}
			}
		}
		
		// printing char
		//
		if(isChar(expr)) {
			if(numConst == 1) {
				Code.put(Code.const_1);
				Code.put(Code.bprint);
			}
			else {
				Code.put(Code.const_1);
				Code.put(Code.bprint);
				for(int i=1; i < numConst; ++i) {
					Code.load(expr);
					Code.put(Code.const_1);
					Code.put(Code.bprint);
				}
			}
		}
		
		// printing bool
		//
		/*if(isBool(expr)) {
			
		}*/
	}
	
	public void visit(SingleReadStatement stmt) {
		Obj desig = stmt.getDesignator().obj;
		if(isInt(desig)) {
			Code.put(Code.read);
		} else {
			Code.put(Code.bread);
		}
		Code.store(desig);
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visiting conditions */
	
	public void visit(CondSingleExprFact factor) {
		// expression will be on stack
	}
	
	public void visit(CondMultiExprFact factor) {
		// two expressions are on stack, apply relop
		//
		Relop relop = factor.getRelop();
		int relopCode = fetchRelopCode(relop);
		int jumpCode = Code.jcc + relopCode;
		
		Code.put(jumpCode);
		Code.put2(11); // jump: 3(jump) + 1(const) + 4(0) + 3(jump) = 11
		Code.put(Code.const_); // 0 - false
		Code.put4(0);
		Code.put(Code.jmp);
		Code.put2(8); // jump: 3(jump) + 1(const) + 4(1) = 8
		Code.put(Code.const_);
		Code.put4(1);// 1 - true
	}
	
	public void visit(CondSingleFactTerm term) {
		// factor is on stack
	}
	
	public void visit(CondMultiFactTerm term) {
		// two boolean vals are on stack
		// use multiplication for and
		//
		Code.put(Code.mul);
	}
	
	public void visit(ConditionMultiTerm cond) {
		// two boolean vals are on stack
		// use addition for or
		//
		Code.put(Code.add);
	}
	
	public void visit(ConditionSingleTerm cond) {
		// term is on stack
	}
	
	public void visit(IfConditionMultiTerm cond) {
		// two boolean vals are on stack
		// use addition for or
		//
		Code.put(Code.add);
	}
	
	public void visit(IfConditionSingleTerm cond) {
		// term is on stack
	}
	
	public void visit(IfStart ifstart) {
		ifJump();
	}
	
	private void ifJump() {
		// if value on top of stack is zero, condition is false
		// we need to jump to: 
		// a) else statement
		// b) end of if statement
		// we dont know address where to jump yet
		// we must save PC where this address  should be stored and fill it later
		//
		Code.put(Code.const_n); // putting 0 (false) on top of stack
		
		Code.put(Code.jcc + Code.eq); // expression == 0 => condition is false
		// here we will later put offset to jump if condition is false
		//
		ifConditionsAddrStack.push(Code.pc);
		Code.put2(1); // temp value that will hold place for actual addr to be written
		
		// if expression !=0 => condition is true, jump wont happen, proceed to next code
		//
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* visiting If statements */
	
	public void visit(IfStatement stmt) {
		int pc = Code.pc;
		int jmpAddr = ifConditionsAddrStack.pop();
		int offset = pc - jmpAddr + 1;
		
		// replace temp 1 value at jmpAddr with actual value of jmp offset 
		// with end of if statement
		//
		Code.put2(jmpAddr, offset);
	}
	
	public void visit(ElseStart stmt) {
		int jmpAddr = ifConditionsAddrStack.pop();
		
		// must jump over else statement if if statement completed
		// this will happen when if condition was true
		//
		Code.put(Code.jmp); 
		ifConditionsAddrStack.push(Code.pc);
		Code.put2(1);
		
		int pc = Code.pc;
		int offset = pc - jmpAddr + 1;
		
		// this is the point where we jump when if condition is false
		//
		Code.put2(jmpAddr, offset);
	}
	
	public void visit(IfElseStatement stmt) {
		int pc = Code.pc;
		int jmpAddr = ifConditionsAddrStack.pop();
		int offset = pc - jmpAddr + 1;
		
		// replace temp 1 value at jmpAddr with actual value of jmp offset 
		// with ending of whole ifelse statement
		//
		Code.put2(jmpAddr, offset);
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visiting dowhile statements */
	
	// when entering do we must save address  where to jump if while condition will be true
	//
	public void visit(DoEnter doEnter) {
		doWhileAddrStack.push(Code.pc);
	}
	
	public void visit(WhileStart whileStart) {
		// this is place where continue statements need to jump
		// there can be more than 1 continue in a loop
		// so must empty whole stack and fill missing jump addrs
		//
		while(!contAddrStack.empty()) {
			int contAddr = contAddrStack.pop();
			int contOffset = Code.pc - contAddr + 1;
			Code.put2(contAddr, contOffset);
		}
	}
	
	public void visit(WhileCondition whileCondition) {
		int doAddr = doWhileAddrStack.pop();
		int offset = doAddr - (Code.pc + 1);
		
		Code.put(Code.const_n); // putting 0 (false) on top of stack
		
		// if top of stack is not equal to 0, means condition is true,
		// jump back to start of do
		//
		Code.put(Code.jcc + Code.ne); 
		Code.put2(offset);
		
		// this is place where break statements need to jump
		// there can be more than 1 break in a loop
		// so must empty whole stack and fill missing jump addrs
		//
		while(!breakAddrStack.empty()) {
			int brkAddr = breakAddrStack.pop();
			int brkOffset = Code.pc - brkAddr + 1;
			Code.put2(brkAddr, brkOffset);
		}
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visiting Continue and break statements */
	
	// Continue - jump to do while begining (before condition check)
	//
	public void visit(SingleContinueStatement cont) {
		// we don't know where to jump in continue until end of loop is visited
		// so leave temp val in jump address
		//
		Code.put(Code.jmp);
		contAddrStack.push(Code.pc);
		Code.put2(1); // temp placeholder
	}
	
	// Break - jump to while end (after condition check)
	//
	public void visit(SingleBreakStatement brk) {
		// we don't know where to jump in break until end of loop is visited
		// so leave temp val in jump address
		//
		Code.put(Code.jmp);
		breakAddrStack.push(Code.pc);
		Code.put2(1); // temp placeholder
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Visiting program */
	
	public void visit(Program program) {
		if(Code.pc > 8*1024) {
			reportError("Code size is over 8KB.", program);
		}
		Tab.closeScope();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	
}

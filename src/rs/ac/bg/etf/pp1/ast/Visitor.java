// generated with ast extension for cup
// version 0.8
// 28/5/2022 2:8:1


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Mulop Mulop);
    public void visit(MethodDecl MethodDecl);
    public void visit(MethodDeclOption MethodDeclOption);
    public void visit(Relop Relop);
    public void visit(MethodType MethodType);
    public void visit(MethodSignature MethodSignature);
    public void visit(AllDeclList AllDeclList);
    public void visit(FormParsOption FormParsOption);
    public void visit(StatementList StatementList);
    public void visit(Addop Addop);
    public void visit(NumConstOption NumConstOption);
    public void visit(RecordDecl RecordDecl);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(GlobalVarDecl GlobalVarDecl);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(Condition Condition);
    public void visit(Statements Statements);
    public void visit(DesignatorStatementAssign DesignatorStatementAssign);
    public void visit(IfCondition IfCondition);
    public void visit(GlobalVarDeclSingle GlobalVarDeclSingle);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(ConstVal ConstVal);
    public void visit(VarDeclOption VarDeclOption);
    public void visit(ActPars ActPars);
    public void visit(SingleDecl SingleDecl);
    public void visit(VarArgs VarArgs);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ConstAssignList ConstAssignList);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(ArrBracketsOption ArrBracketsOption);
    public void visit(ClassDecl ClassDecl);
    public void visit(ConstDecl ConstDecl);
    public void visit(CondFact CondFact);
    public void visit(FormPar FormPar);
    public void visit(ActParsOption ActParsOption);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(SingleStatement SingleStatement);
    public void visit(ClassMethodDeclOption ClassMethodDeclOption);
    public void visit(FormPars FormPars);
    public void visit(GlobalVarDeclList GlobalVarDeclList);
    public void visit(CondMultiExprFact CondMultiExprFact);
    public void visit(CondSingleExprFact CondSingleExprFact);
    public void visit(CondMultiFactTerm CondMultiFactTerm);
    public void visit(CondSingleFactTerm CondSingleFactTerm);
    public void visit(IfConditionError IfConditionError);
    public void visit(IfConditionMultiTerm IfConditionMultiTerm);
    public void visit(IfConditionSingleTerm IfConditionSingleTerm);
    public void visit(ConditionMultiTerm ConditionMultiTerm);
    public void visit(ConditionSingleTerm ConditionSingleTerm);
    public void visit(MethodCall MethodCall);
    public void visit(ActParsRightParen ActParsRightParen);
    public void visit(ActParsLeftParen ActParsLeftParen);
    public void visit(ActParsNo ActParsNo);
    public void visit(ActParsYes ActParsYes);
    public void visit(ActParsSingleExpr ActParsSingleExpr);
    public void visit(ActParsMultiExpr ActParsMultiExpr);
    public void visit(AssignError AssignError);
    public void visit(DesignatorStmtAssignCorrect DesignatorStmtAssignCorrect);
    public void visit(DesignatorStmtMethodCall DesignatorStmtMethodCall);
    public void visit(DesignatorStmtMinusMinus DesignatorStmtMinusMinus);
    public void visit(DesignatorStmtPlusPlus DesignatorStmtPlusPlus);
    public void visit(DesignatorStmtAssign DesignatorStmtAssign);
    public void visit(ArrayDesignatorStart ArrayDesignatorStart);
    public void visit(RecordDesignatorArrayStart RecordDesignatorArrayStart);
    public void visit(RecordDesignatorStart RecordDesignatorStart);
    public void visit(IdentMemberArrayDesignator IdentMemberArrayDesignator);
    public void visit(IdentMemberDesignator IdentMemberDesignator);
    public void visit(IdentArrayDesignator IdentArrayDesignator);
    public void visit(IdentDesignator IdentDesignator);
    public void visit(RelopLSE RelopLSE);
    public void visit(RelopLS RelopLS);
    public void visit(RelopEQUAL RelopEQUAL);
    public void visit(RelopNOTEQUAL RelopNOTEQUAL);
    public void visit(RelopGTE RelopGTE);
    public void visit(RelopGT RelopGT);
    public void visit(MulopPct MulopPct);
    public void visit(MulopDiv MulopDiv);
    public void visit(MulopMul MulopMul);
    public void visit(AddopMinus AddopMinus);
    public void visit(AddopPlus AddopPlus);
    public void visit(MethodCallFactor MethodCallFactor);
    public void visit(DesignatorEmptyFactor DesignatorEmptyFactor);
    public void visit(NewTypeArrayFactor NewTypeArrayFactor);
    public void visit(NewTypeFactor NewTypeFactor);
    public void visit(BoolConstFactor BoolConstFactor);
    public void visit(ExprFactor ExprFactor);
    public void visit(CharConstFactor CharConstFactor);
    public void visit(NumConstFactor NumConstFactor);
    public void visit(MultiFactorTerm MultiFactorTerm);
    public void visit(SingleFactorTerm SingleFactorTerm);
    public void visit(MultiTermExpr MultiTermExpr);
    public void visit(SingleTermExprNegative SingleTermExprNegative);
    public void visit(SingleTermExprPositive SingleTermExprPositive);
    public void visit(NumConstNo NumConstNo);
    public void visit(NumConstYes NumConstYes);
    public void visit(DoEnter DoEnter);
    public void visit(ElseStart ElseStart);
    public void visit(IfStart IfStart);
    public void visit(WhileStart WhileStart);
    public void visit(WhileCondition WhileCondition);
    public void visit(DoWhileStatement DoWhileStatement);
    public void visit(IfElseStatement IfElseStatement);
    public void visit(IfStatement IfStatement);
    public void visit(SingleReturnExprStatement SingleReturnExprStatement);
    public void visit(SingleReturnStatement SingleReturnStatement);
    public void visit(SingleContinueStatement SingleContinueStatement);
    public void visit(SingleBreakStatement SingleBreakStatement);
    public void visit(SinglePrintStatement SinglePrintStatement);
    public void visit(SingleReadStatement SingleReadStatement);
    public void visit(SingleDesignatorStatement SingleDesignatorStatement);
    public void visit(StatementListNo StatementListNo);
    public void visit(StatementListMultiple StatementListMultiple);
    public void visit(StatementMultiple StatementMultiple);
    public void visit(StatementSingle StatementSingle);
    public void visit(StatementsInBraces StatementsInBraces);
    public void visit(FormParSingle FormParSingle);
    public void visit(FormParsSingle FormParsSingle);
    public void visit(FormParsMultiple FormParsMultiple);
    public void visit(VarArgsDeclaration VarArgsDeclaration);
    public void visit(MethodTypeVoid MethodTypeVoid);
    public void visit(MethodTypeType MethodTypeType);
    public void visit(MethodName MethodName);
    public void visit(FormParsEnd FormParsEnd);
    public void visit(MethodSignaturePlain MethodSignaturePlain);
    public void visit(MethodSignatureVarArgs MethodSignatureVarArgs);
    public void visit(MethodSignatureParams MethodSignatureParams);
    public void visit(MethodSignatureVarArgsOnly MethodSignatureVarArgsOnly);
    public void visit(MethodDeclaration MethodDeclaration);
    public void visit(MethodDeclListNo MethodDeclListNo);
    public void visit(MethodDeclListMultiple MethodDeclListMultiple);
    public void visit(ClassMethodOption ClassMethodOption);
    public void visit(ClassDeclarationExpr ClassDeclarationExpr);
    public void visit(RecordName RecordName);
    public void visit(RecordDeclarationExpr RecordDeclarationExpr);
    public void visit(NoArray NoArray);
    public void visit(ArrayBrackets ArrayBrackets);
    public void visit(GlobalVarDeclarationError GlobalVarDeclarationError);
    public void visit(GlobalVarDeclarationSingle GlobalVarDeclarationSingle);
    public void visit(GlobalVarDeclListSingle GlobalVarDeclListSingle);
    public void visit(GlobalVarDeclListMultiple GlobalVarDeclListMultiple);
    public void visit(GlobalVarDeclarationExpr GlobalVarDeclarationExpr);
    public void visit(NoVarDeclOption NoVarDeclOption);
    public void visit(VarDeclOptionTrue VarDeclOptionTrue);
    public void visit(VarDeclSingle VarDeclSingle);
    public void visit(VarDeclListSingle VarDeclListSingle);
    public void visit(VarDeclListMultiple VarDeclListMultiple);
    public void visit(VarDeclarationExpr VarDeclarationExpr);
    public void visit(BoolConst BoolConst);
    public void visit(CharConst CharConst);
    public void visit(NumberConst NumberConst);
    public void visit(ConstAssign ConstAssign);
    public void visit(ConstAssignSingle ConstAssignSingle);
    public void visit(ConstAssignListMultiple ConstAssignListMultiple);
    public void visit(ConstDeclarationExpr ConstDeclarationExpr);
    public void visit(ClassDeclaration ClassDeclaration);
    public void visit(RecordDeclaration RecordDeclaration);
    public void visit(GlobalVarDeclaration GlobalVarDeclaration);
    public void visit(ConstDeclaration ConstDeclaration);
    public void visit(Type Type);
    public void visit(NoDecl NoDecl);
    public void visit(AllDeclListMultiple AllDeclListMultiple);
    public void visit(ProgramName ProgramName);
    public void visit(Program Program);

}

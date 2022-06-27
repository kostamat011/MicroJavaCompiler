// generated with ast extension for cup
// version 0.8
// 27/5/2022 6:33:21


package rs.ac.bg.etf.pp1.ast;

public class DoWhileStatement extends SingleStatement {

    private DoEnter DoEnter;
    private StatementList StatementList;
    private WhileCondition WhileCondition;

    public DoWhileStatement (DoEnter DoEnter, StatementList StatementList, WhileCondition WhileCondition) {
        this.DoEnter=DoEnter;
        if(DoEnter!=null) DoEnter.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
        this.WhileCondition=WhileCondition;
        if(WhileCondition!=null) WhileCondition.setParent(this);
    }

    public DoEnter getDoEnter() {
        return DoEnter;
    }

    public void setDoEnter(DoEnter DoEnter) {
        this.DoEnter=DoEnter;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public WhileCondition getWhileCondition() {
        return WhileCondition;
    }

    public void setWhileCondition(WhileCondition WhileCondition) {
        this.WhileCondition=WhileCondition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoEnter!=null) DoEnter.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
        if(WhileCondition!=null) WhileCondition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoEnter!=null) DoEnter.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
        if(WhileCondition!=null) WhileCondition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoEnter!=null) DoEnter.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        if(WhileCondition!=null) WhileCondition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileStatement(\n");

        if(DoEnter!=null)
            buffer.append(DoEnter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileCondition!=null)
            buffer.append(WhileCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhileStatement]");
        return buffer.toString();
    }
}

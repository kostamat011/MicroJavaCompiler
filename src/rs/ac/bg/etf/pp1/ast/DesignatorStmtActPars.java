// generated with ast extension for cup
// version 0.8
// 23/5/2022 21:14:19


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStmtActPars extends DesignatorStatement {

    private Designator Designator;
    private ActParsOption ActParsOption;

    public DesignatorStmtActPars (Designator Designator, ActParsOption ActParsOption) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ActParsOption=ActParsOption;
        if(ActParsOption!=null) ActParsOption.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ActParsOption getActParsOption() {
        return ActParsOption;
    }

    public void setActParsOption(ActParsOption ActParsOption) {
        this.ActParsOption=ActParsOption;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ActParsOption!=null) ActParsOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ActParsOption!=null) ActParsOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ActParsOption!=null) ActParsOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStmtActPars(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsOption!=null)
            buffer.append(ActParsOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStmtActPars]");
        return buffer.toString();
    }
}

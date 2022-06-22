// generated with ast extension for cup
// version 0.8
// 22/5/2022 19:13:25


package rs.ac.bg.etf.pp1.ast;

public class SinglePrintStatement extends SingleStatement {

    private Expr Expr;
    private NumConstOption NumConstOption;

    public SinglePrintStatement (Expr Expr, NumConstOption NumConstOption) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.NumConstOption=NumConstOption;
        if(NumConstOption!=null) NumConstOption.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public NumConstOption getNumConstOption() {
        return NumConstOption;
    }

    public void setNumConstOption(NumConstOption NumConstOption) {
        this.NumConstOption=NumConstOption;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(NumConstOption!=null) NumConstOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(NumConstOption!=null) NumConstOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(NumConstOption!=null) NumConstOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SinglePrintStatement(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NumConstOption!=null)
            buffer.append(NumConstOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SinglePrintStatement]");
        return buffer.toString();
    }
}

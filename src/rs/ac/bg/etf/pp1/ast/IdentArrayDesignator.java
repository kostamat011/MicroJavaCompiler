// generated with ast extension for cup
// version 0.8
// 26/5/2022 4:39:38


package rs.ac.bg.etf.pp1.ast;

public class IdentArrayDesignator extends Designator {

    private ArrayDesignatorStart ArrayDesignatorStart;
    private Expr Expr;

    public IdentArrayDesignator (ArrayDesignatorStart ArrayDesignatorStart, Expr Expr) {
        this.ArrayDesignatorStart=ArrayDesignatorStart;
        if(ArrayDesignatorStart!=null) ArrayDesignatorStart.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public ArrayDesignatorStart getArrayDesignatorStart() {
        return ArrayDesignatorStart;
    }

    public void setArrayDesignatorStart(ArrayDesignatorStart ArrayDesignatorStart) {
        this.ArrayDesignatorStart=ArrayDesignatorStart;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayDesignatorStart!=null) ArrayDesignatorStart.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayDesignatorStart!=null) ArrayDesignatorStart.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayDesignatorStart!=null) ArrayDesignatorStart.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentArrayDesignator(\n");

        if(ArrayDesignatorStart!=null)
            buffer.append(ArrayDesignatorStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentArrayDesignator]");
        return buffer.toString();
    }
}

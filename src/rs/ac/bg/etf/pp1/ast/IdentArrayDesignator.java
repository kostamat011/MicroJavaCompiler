// generated with ast extension for cup
// version 0.8
// 23/5/2022 21:14:19


package rs.ac.bg.etf.pp1.ast;

public class IdentArrayDesignator extends Designator {

    private String ident;
    private Expr Expr;

    public IdentArrayDesignator (String ident, Expr Expr) {
        this.ident=ident;
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident=ident;
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
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentArrayDesignator(\n");

        buffer.append(" "+tab+ident);
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

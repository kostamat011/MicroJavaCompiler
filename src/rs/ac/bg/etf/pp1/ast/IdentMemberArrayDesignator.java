// generated with ast extension for cup
// version 0.8
// 22/5/2022 6:31:55


package rs.ac.bg.etf.pp1.ast;

public class IdentMemberArrayDesignator extends MemberDesignator {

    private String I1;
    private String memberArrayName;
    private Expr Expr;

    public IdentMemberArrayDesignator (String I1, String memberArrayName, Expr Expr) {
        this.I1=I1;
        this.memberArrayName=memberArrayName;
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public String getMemberArrayName() {
        return memberArrayName;
    }

    public void setMemberArrayName(String memberArrayName) {
        this.memberArrayName=memberArrayName;
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
        buffer.append("IdentMemberArrayDesignator(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        buffer.append(" "+tab+memberArrayName);
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentMemberArrayDesignator]");
        return buffer.toString();
    }
}

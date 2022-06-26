// generated with ast extension for cup
// version 0.8
// 26/5/2022 3:19:39


package rs.ac.bg.etf.pp1.ast;

public class IdentMemberDesignator extends Designator {

    private String ident;
    private String memberName;

    public IdentMemberDesignator (String ident, String memberName) {
        this.ident=ident;
        this.memberName=memberName;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident=ident;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName=memberName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentMemberDesignator(\n");

        buffer.append(" "+tab+ident);
        buffer.append("\n");

        buffer.append(" "+tab+memberName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentMemberDesignator]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 22/5/2022 6:31:55


package rs.ac.bg.etf.pp1.ast;

public class IdentMemberDesignator extends MemberDesignator {

    private String I1;
    private String memberName;

    public IdentMemberDesignator (String I1, String memberName) {
        this.I1=I1;
        this.memberName=memberName;
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
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

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        buffer.append(" "+tab+memberName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentMemberDesignator]");
        return buffer.toString();
    }
}

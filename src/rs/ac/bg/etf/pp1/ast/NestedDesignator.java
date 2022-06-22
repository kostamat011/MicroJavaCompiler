// generated with ast extension for cup
// version 0.8
// 22/5/2022 19:13:25


package rs.ac.bg.etf.pp1.ast;

public class NestedDesignator extends Designator {

    private MemberDesignator MemberDesignator;

    public NestedDesignator (MemberDesignator MemberDesignator) {
        this.MemberDesignator=MemberDesignator;
        if(MemberDesignator!=null) MemberDesignator.setParent(this);
    }

    public MemberDesignator getMemberDesignator() {
        return MemberDesignator;
    }

    public void setMemberDesignator(MemberDesignator MemberDesignator) {
        this.MemberDesignator=MemberDesignator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MemberDesignator!=null) MemberDesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MemberDesignator!=null) MemberDesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MemberDesignator!=null) MemberDesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NestedDesignator(\n");

        if(MemberDesignator!=null)
            buffer.append(MemberDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NestedDesignator]");
        return buffer.toString();
    }
}

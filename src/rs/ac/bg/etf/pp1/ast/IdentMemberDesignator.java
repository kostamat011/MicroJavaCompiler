// generated with ast extension for cup
// version 0.8
// 28/5/2022 20:16:51


package rs.ac.bg.etf.pp1.ast;

public class IdentMemberDesignator extends Designator {

    private RecordDesignatorStart RecordDesignatorStart;
    private String memberName;

    public IdentMemberDesignator (RecordDesignatorStart RecordDesignatorStart, String memberName) {
        this.RecordDesignatorStart=RecordDesignatorStart;
        if(RecordDesignatorStart!=null) RecordDesignatorStart.setParent(this);
        this.memberName=memberName;
    }

    public RecordDesignatorStart getRecordDesignatorStart() {
        return RecordDesignatorStart;
    }

    public void setRecordDesignatorStart(RecordDesignatorStart RecordDesignatorStart) {
        this.RecordDesignatorStart=RecordDesignatorStart;
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
        if(RecordDesignatorStart!=null) RecordDesignatorStart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RecordDesignatorStart!=null) RecordDesignatorStart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RecordDesignatorStart!=null) RecordDesignatorStart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentMemberDesignator(\n");

        if(RecordDesignatorStart!=null)
            buffer.append(RecordDesignatorStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+memberName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentMemberDesignator]");
        return buffer.toString();
    }
}

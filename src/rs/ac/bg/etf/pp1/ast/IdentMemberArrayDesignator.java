// generated with ast extension for cup
// version 0.8
// 28/5/2022 2:8:1


package rs.ac.bg.etf.pp1.ast;

public class IdentMemberArrayDesignator extends Designator {

    private RecordDesignatorStart RecordDesignatorStart;
    private RecordDesignatorArrayStart RecordDesignatorArrayStart;
    private Expr Expr;

    public IdentMemberArrayDesignator (RecordDesignatorStart RecordDesignatorStart, RecordDesignatorArrayStart RecordDesignatorArrayStart, Expr Expr) {
        this.RecordDesignatorStart=RecordDesignatorStart;
        if(RecordDesignatorStart!=null) RecordDesignatorStart.setParent(this);
        this.RecordDesignatorArrayStart=RecordDesignatorArrayStart;
        if(RecordDesignatorArrayStart!=null) RecordDesignatorArrayStart.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public RecordDesignatorStart getRecordDesignatorStart() {
        return RecordDesignatorStart;
    }

    public void setRecordDesignatorStart(RecordDesignatorStart RecordDesignatorStart) {
        this.RecordDesignatorStart=RecordDesignatorStart;
    }

    public RecordDesignatorArrayStart getRecordDesignatorArrayStart() {
        return RecordDesignatorArrayStart;
    }

    public void setRecordDesignatorArrayStart(RecordDesignatorArrayStart RecordDesignatorArrayStart) {
        this.RecordDesignatorArrayStart=RecordDesignatorArrayStart;
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
        if(RecordDesignatorStart!=null) RecordDesignatorStart.accept(visitor);
        if(RecordDesignatorArrayStart!=null) RecordDesignatorArrayStart.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RecordDesignatorStart!=null) RecordDesignatorStart.traverseTopDown(visitor);
        if(RecordDesignatorArrayStart!=null) RecordDesignatorArrayStart.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RecordDesignatorStart!=null) RecordDesignatorStart.traverseBottomUp(visitor);
        if(RecordDesignatorArrayStart!=null) RecordDesignatorArrayStart.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentMemberArrayDesignator(\n");

        if(RecordDesignatorStart!=null)
            buffer.append(RecordDesignatorStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RecordDesignatorArrayStart!=null)
            buffer.append(RecordDesignatorArrayStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
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

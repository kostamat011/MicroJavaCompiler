// generated with ast extension for cup
// version 0.8
// 27/5/2022 6:33:21


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarationExpr extends ConstDecl {

    private Type Type;
    private ConstAssignList ConstAssignList;

    public ConstDeclarationExpr (Type Type, ConstAssignList ConstAssignList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstAssignList=ConstAssignList;
        if(ConstAssignList!=null) ConstAssignList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstAssignList getConstAssignList() {
        return ConstAssignList;
    }

    public void setConstAssignList(ConstAssignList ConstAssignList) {
        this.ConstAssignList=ConstAssignList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstAssignList!=null) ConstAssignList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstAssignList!=null) ConstAssignList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstAssignList!=null) ConstAssignList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationExpr(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAssignList!=null)
            buffer.append(ConstAssignList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarationExpr]");
        return buffer.toString();
    }
}

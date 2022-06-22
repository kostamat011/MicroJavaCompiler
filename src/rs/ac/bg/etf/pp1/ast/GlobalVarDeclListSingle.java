// generated with ast extension for cup
// version 0.8
// 22/5/2022 19:13:25


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclListSingle extends GlobalVarDeclList {

    private GlobalVarDeclSingle GlobalVarDeclSingle;

    public GlobalVarDeclListSingle (GlobalVarDeclSingle GlobalVarDeclSingle) {
        this.GlobalVarDeclSingle=GlobalVarDeclSingle;
        if(GlobalVarDeclSingle!=null) GlobalVarDeclSingle.setParent(this);
    }

    public GlobalVarDeclSingle getGlobalVarDeclSingle() {
        return GlobalVarDeclSingle;
    }

    public void setGlobalVarDeclSingle(GlobalVarDeclSingle GlobalVarDeclSingle) {
        this.GlobalVarDeclSingle=GlobalVarDeclSingle;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobalVarDeclSingle!=null) GlobalVarDeclSingle.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarDeclSingle!=null) GlobalVarDeclSingle.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarDeclSingle!=null) GlobalVarDeclSingle.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDeclListSingle(\n");

        if(GlobalVarDeclSingle!=null)
            buffer.append(GlobalVarDeclSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclListSingle]");
        return buffer.toString();
    }
}

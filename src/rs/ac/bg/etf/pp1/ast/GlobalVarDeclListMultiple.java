// generated with ast extension for cup
// version 0.8
// 22/5/2022 19:13:25


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclListMultiple extends GlobalVarDeclList {

    private GlobalVarDeclList GlobalVarDeclList;
    private GlobalVarDeclSingle GlobalVarDeclSingle;

    public GlobalVarDeclListMultiple (GlobalVarDeclList GlobalVarDeclList, GlobalVarDeclSingle GlobalVarDeclSingle) {
        this.GlobalVarDeclList=GlobalVarDeclList;
        if(GlobalVarDeclList!=null) GlobalVarDeclList.setParent(this);
        this.GlobalVarDeclSingle=GlobalVarDeclSingle;
        if(GlobalVarDeclSingle!=null) GlobalVarDeclSingle.setParent(this);
    }

    public GlobalVarDeclList getGlobalVarDeclList() {
        return GlobalVarDeclList;
    }

    public void setGlobalVarDeclList(GlobalVarDeclList GlobalVarDeclList) {
        this.GlobalVarDeclList=GlobalVarDeclList;
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
        if(GlobalVarDeclList!=null) GlobalVarDeclList.accept(visitor);
        if(GlobalVarDeclSingle!=null) GlobalVarDeclSingle.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarDeclList!=null) GlobalVarDeclList.traverseTopDown(visitor);
        if(GlobalVarDeclSingle!=null) GlobalVarDeclSingle.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarDeclList!=null) GlobalVarDeclList.traverseBottomUp(visitor);
        if(GlobalVarDeclSingle!=null) GlobalVarDeclSingle.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDeclListMultiple(\n");

        if(GlobalVarDeclList!=null)
            buffer.append(GlobalVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalVarDeclSingle!=null)
            buffer.append(GlobalVarDeclSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclListMultiple]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 27/5/2022 2:12:12


package rs.ac.bg.etf.pp1.ast;

public class AllDeclListMultiple extends AllDeclList {

    private AllDeclList AllDeclList;
    private SingleDecl SingleDecl;

    public AllDeclListMultiple (AllDeclList AllDeclList, SingleDecl SingleDecl) {
        this.AllDeclList=AllDeclList;
        if(AllDeclList!=null) AllDeclList.setParent(this);
        this.SingleDecl=SingleDecl;
        if(SingleDecl!=null) SingleDecl.setParent(this);
    }

    public AllDeclList getAllDeclList() {
        return AllDeclList;
    }

    public void setAllDeclList(AllDeclList AllDeclList) {
        this.AllDeclList=AllDeclList;
    }

    public SingleDecl getSingleDecl() {
        return SingleDecl;
    }

    public void setSingleDecl(SingleDecl SingleDecl) {
        this.SingleDecl=SingleDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AllDeclList!=null) AllDeclList.accept(visitor);
        if(SingleDecl!=null) SingleDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AllDeclList!=null) AllDeclList.traverseTopDown(visitor);
        if(SingleDecl!=null) SingleDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AllDeclList!=null) AllDeclList.traverseBottomUp(visitor);
        if(SingleDecl!=null) SingleDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AllDeclListMultiple(\n");

        if(AllDeclList!=null)
            buffer.append(AllDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SingleDecl!=null)
            buffer.append(SingleDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AllDeclListMultiple]");
        return buffer.toString();
    }
}

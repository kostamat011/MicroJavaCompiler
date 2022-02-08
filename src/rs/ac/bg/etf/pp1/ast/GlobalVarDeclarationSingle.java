// generated with ast extension for cup
// version 0.8
// 8/1/2022 6:14:8


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclarationSingle extends GlobalVarDeclSingle {

    private ArrBracketsOption ArrBracketsOption;

    public GlobalVarDeclarationSingle (ArrBracketsOption ArrBracketsOption) {
        this.ArrBracketsOption=ArrBracketsOption;
        if(ArrBracketsOption!=null) ArrBracketsOption.setParent(this);
    }

    public ArrBracketsOption getArrBracketsOption() {
        return ArrBracketsOption;
    }

    public void setArrBracketsOption(ArrBracketsOption ArrBracketsOption) {
        this.ArrBracketsOption=ArrBracketsOption;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrBracketsOption!=null) ArrBracketsOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrBracketsOption!=null) ArrBracketsOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrBracketsOption!=null) ArrBracketsOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDeclarationSingle(\n");

        if(ArrBracketsOption!=null)
            buffer.append(ArrBracketsOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclarationSingle]");
        return buffer.toString();
    }
}

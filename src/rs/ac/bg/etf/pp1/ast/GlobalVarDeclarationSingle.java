// generated with ast extension for cup
// version 0.8
// 28/5/2022 20:16:51


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclarationSingle extends GlobalVarDeclSingle {

    private String varName;
    private ArrBracketsOption ArrBracketsOption;

    public GlobalVarDeclarationSingle (String varName, ArrBracketsOption ArrBracketsOption) {
        this.varName=varName;
        this.ArrBracketsOption=ArrBracketsOption;
        if(ArrBracketsOption!=null) ArrBracketsOption.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
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

        buffer.append(" "+tab+varName);
        buffer.append("\n");

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

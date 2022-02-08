// generated with ast extension for cup
// version 0.8
// 8/1/2022 6:14:8


package rs.ac.bg.etf.pp1.ast;

public class FormParSingle extends FormPar {

    private Type Type;
    private ArrBracketsOption ArrBracketsOption;

    public FormParSingle (Type Type, ArrBracketsOption ArrBracketsOption) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ArrBracketsOption=ArrBracketsOption;
        if(ArrBracketsOption!=null) ArrBracketsOption.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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
        if(Type!=null) Type.accept(visitor);
        if(ArrBracketsOption!=null) ArrBracketsOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ArrBracketsOption!=null) ArrBracketsOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ArrBracketsOption!=null) ArrBracketsOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParSingle(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ArrBracketsOption!=null)
            buffer.append(ArrBracketsOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParSingle]");
        return buffer.toString();
    }
}

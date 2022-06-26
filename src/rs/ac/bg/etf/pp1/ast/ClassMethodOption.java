// generated with ast extension for cup
// version 0.8
// 26/5/2022 4:39:38


package rs.ac.bg.etf.pp1.ast;

public class ClassMethodOption extends ClassMethodDeclOption {

    private MethodDeclOption MethodDeclOption;

    public ClassMethodOption (MethodDeclOption MethodDeclOption) {
        this.MethodDeclOption=MethodDeclOption;
        if(MethodDeclOption!=null) MethodDeclOption.setParent(this);
    }

    public MethodDeclOption getMethodDeclOption() {
        return MethodDeclOption;
    }

    public void setMethodDeclOption(MethodDeclOption MethodDeclOption) {
        this.MethodDeclOption=MethodDeclOption;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclOption!=null) MethodDeclOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclOption!=null) MethodDeclOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclOption!=null) MethodDeclOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassMethodOption(\n");

        if(MethodDeclOption!=null)
            buffer.append(MethodDeclOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassMethodOption]");
        return buffer.toString();
    }
}

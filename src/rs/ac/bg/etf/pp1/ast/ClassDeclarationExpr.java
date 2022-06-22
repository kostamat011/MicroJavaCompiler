// generated with ast extension for cup
// version 0.8
// 22/5/2022 3:52:45


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclarationExpr extends ClassDecl {

    private String className;
    private VarDeclOption VarDeclOption;
    private ClassMethodDeclOption ClassMethodDeclOption;

    public ClassDeclarationExpr (String className, VarDeclOption VarDeclOption, ClassMethodDeclOption ClassMethodDeclOption) {
        this.className=className;
        this.VarDeclOption=VarDeclOption;
        if(VarDeclOption!=null) VarDeclOption.setParent(this);
        this.ClassMethodDeclOption=ClassMethodDeclOption;
        if(ClassMethodDeclOption!=null) ClassMethodDeclOption.setParent(this);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className=className;
    }

    public VarDeclOption getVarDeclOption() {
        return VarDeclOption;
    }

    public void setVarDeclOption(VarDeclOption VarDeclOption) {
        this.VarDeclOption=VarDeclOption;
    }

    public ClassMethodDeclOption getClassMethodDeclOption() {
        return ClassMethodDeclOption;
    }

    public void setClassMethodDeclOption(ClassMethodDeclOption ClassMethodDeclOption) {
        this.ClassMethodDeclOption=ClassMethodDeclOption;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclOption!=null) VarDeclOption.accept(visitor);
        if(ClassMethodDeclOption!=null) ClassMethodDeclOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclOption!=null) VarDeclOption.traverseTopDown(visitor);
        if(ClassMethodDeclOption!=null) ClassMethodDeclOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclOption!=null) VarDeclOption.traverseBottomUp(visitor);
        if(ClassMethodDeclOption!=null) ClassMethodDeclOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclarationExpr(\n");

        buffer.append(" "+tab+className);
        buffer.append("\n");

        if(VarDeclOption!=null)
            buffer.append(VarDeclOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMethodDeclOption!=null)
            buffer.append(ClassMethodDeclOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclarationExpr]");
        return buffer.toString();
    }
}

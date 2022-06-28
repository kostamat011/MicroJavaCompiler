// generated with ast extension for cup
// version 0.8
// 28/5/2022 2:8:1


package rs.ac.bg.etf.pp1.ast;

public class MethodSignatureVarArgsOnly extends MethodSignature {

    private MethodType MethodType;
    private MethodName MethodName;
    private Type Type;
    private VarArgs VarArgs;
    private FormParsEnd FormParsEnd;

    public MethodSignatureVarArgsOnly (MethodType MethodType, MethodName MethodName, Type Type, VarArgs VarArgs, FormParsEnd FormParsEnd) {
        this.MethodType=MethodType;
        if(MethodType!=null) MethodType.setParent(this);
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarArgs=VarArgs;
        if(VarArgs!=null) VarArgs.setParent(this);
        this.FormParsEnd=FormParsEnd;
        if(FormParsEnd!=null) FormParsEnd.setParent(this);
    }

    public MethodType getMethodType() {
        return MethodType;
    }

    public void setMethodType(MethodType MethodType) {
        this.MethodType=MethodType;
    }

    public MethodName getMethodName() {
        return MethodName;
    }

    public void setMethodName(MethodName MethodName) {
        this.MethodName=MethodName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarArgs getVarArgs() {
        return VarArgs;
    }

    public void setVarArgs(VarArgs VarArgs) {
        this.VarArgs=VarArgs;
    }

    public FormParsEnd getFormParsEnd() {
        return FormParsEnd;
    }

    public void setFormParsEnd(FormParsEnd FormParsEnd) {
        this.FormParsEnd=FormParsEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodType!=null) MethodType.accept(visitor);
        if(MethodName!=null) MethodName.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(VarArgs!=null) VarArgs.accept(visitor);
        if(FormParsEnd!=null) FormParsEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodType!=null) MethodType.traverseTopDown(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarArgs!=null) VarArgs.traverseTopDown(visitor);
        if(FormParsEnd!=null) FormParsEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodType!=null) MethodType.traverseBottomUp(visitor);
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarArgs!=null) VarArgs.traverseBottomUp(visitor);
        if(FormParsEnd!=null) FormParsEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodSignatureVarArgsOnly(\n");

        if(MethodType!=null)
            buffer.append(MethodType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodName!=null)
            buffer.append(MethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarArgs!=null)
            buffer.append(VarArgs.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsEnd!=null)
            buffer.append(FormParsEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodSignatureVarArgsOnly]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 28/5/2022 20:16:51


package rs.ac.bg.etf.pp1.ast;

public class MethodSignatureParams extends MethodSignature {

    private MethodType MethodType;
    private MethodName MethodName;
    private FormPars FormPars;
    private FormParsEnd FormParsEnd;

    public MethodSignatureParams (MethodType MethodType, MethodName MethodName, FormPars FormPars, FormParsEnd FormParsEnd) {
        this.MethodType=MethodType;
        if(MethodType!=null) MethodType.setParent(this);
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
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

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
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
        if(FormPars!=null) FormPars.accept(visitor);
        if(FormParsEnd!=null) FormParsEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodType!=null) MethodType.traverseTopDown(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(FormParsEnd!=null) FormParsEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodType!=null) MethodType.traverseBottomUp(visitor);
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(FormParsEnd!=null) FormParsEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodSignatureParams(\n");

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

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsEnd!=null)
            buffer.append(FormParsEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodSignatureParams]");
        return buffer.toString();
    }
}

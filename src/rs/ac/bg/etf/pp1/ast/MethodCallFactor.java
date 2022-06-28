// generated with ast extension for cup
// version 0.8
// 28/5/2022 20:16:51


package rs.ac.bg.etf.pp1.ast;

public class MethodCallFactor extends Factor {

    private MethodCall MethodCall;

    public MethodCallFactor (MethodCall MethodCall) {
        this.MethodCall=MethodCall;
        if(MethodCall!=null) MethodCall.setParent(this);
    }

    public MethodCall getMethodCall() {
        return MethodCall;
    }

    public void setMethodCall(MethodCall MethodCall) {
        this.MethodCall=MethodCall;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodCall!=null) MethodCall.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodCall!=null) MethodCall.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodCall!=null) MethodCall.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodCallFactor(\n");

        if(MethodCall!=null)
            buffer.append(MethodCall.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodCallFactor]");
        return buffer.toString();
    }
}

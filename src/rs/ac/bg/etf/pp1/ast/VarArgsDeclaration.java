// generated with ast extension for cup
// version 0.8
// 24/5/2022 21:54:11


package rs.ac.bg.etf.pp1.ast;

public class VarArgsDeclaration extends VarArgs {

    private String varArgsName;

    public VarArgsDeclaration (String varArgsName) {
        this.varArgsName=varArgsName;
    }

    public String getVarArgsName() {
        return varArgsName;
    }

    public void setVarArgsName(String varArgsName) {
        this.varArgsName=varArgsName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarArgsDeclaration(\n");

        buffer.append(" "+tab+varArgsName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarArgsDeclaration]");
        return buffer.toString();
    }
}

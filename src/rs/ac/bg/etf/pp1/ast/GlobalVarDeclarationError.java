// generated with ast extension for cup
// version 0.8
// 26/5/2022 17:11:54


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclarationError extends GlobalVarDeclSingle {

    public GlobalVarDeclarationError () {
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
        buffer.append("GlobalVarDeclarationError(\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclarationError]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 27/5/2022 6:33:21


package rs.ac.bg.etf.pp1.ast;

public class NoDecl extends AllDeclList {

    public NoDecl () {
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
        buffer.append("NoDecl(\n");

        buffer.append(tab);
        buffer.append(") [NoDecl]");
        return buffer.toString();
    }
}

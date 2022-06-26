// generated with ast extension for cup
// version 0.8
// 26/5/2022 3:19:39


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

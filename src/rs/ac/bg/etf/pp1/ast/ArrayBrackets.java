// generated with ast extension for cup
// version 0.8
// 22/5/2022 19:13:25


package rs.ac.bg.etf.pp1.ast;

public class ArrayBrackets extends ArrBracketsOption {

    public ArrayBrackets () {
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
        buffer.append("ArrayBrackets(\n");

        buffer.append(tab);
        buffer.append(") [ArrayBrackets]");
        return buffer.toString();
    }
}

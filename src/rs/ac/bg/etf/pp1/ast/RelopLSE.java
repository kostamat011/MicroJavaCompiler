// generated with ast extension for cup
// version 0.8
// 22/5/2022 2:20:26


package rs.ac.bg.etf.pp1.ast;

public class RelopLSE extends Relop {

    public RelopLSE () {
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
        buffer.append("RelopLSE(\n");

        buffer.append(tab);
        buffer.append(") [RelopLSE]");
        return buffer.toString();
    }
}

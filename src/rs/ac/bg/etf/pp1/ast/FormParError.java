// generated with ast extension for cup
// version 0.8
// 22/5/2022 3:52:45


package rs.ac.bg.etf.pp1.ast;

public class FormParError extends FormPar {

    public FormParError () {
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
        buffer.append("FormParError(\n");

        buffer.append(tab);
        buffer.append(") [FormParError]");
        return buffer.toString();
    }
}

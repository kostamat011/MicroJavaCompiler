// generated with ast extension for cup
// version 0.8
// 28/5/2022 2:8:1


package rs.ac.bg.etf.pp1.ast;

public class RecordDesignatorArrayStart implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String ident;

    public RecordDesignatorArrayStart (String ident) {
        this.ident=ident;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident=ident;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("RecordDesignatorArrayStart(\n");

        buffer.append(" "+tab+ident);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordDesignatorArrayStart]");
        return buffer.toString();
    }
}

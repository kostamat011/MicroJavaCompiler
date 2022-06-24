// generated with ast extension for cup
// version 0.8
// 24/5/2022 4:12:29


package rs.ac.bg.etf.pp1.ast;

public class MethodCall implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private Designator Designator;
    private ActParsLeftParen ActParsLeftParen;
    private ActParsOption ActParsOption;
    private ActParsRightParen ActParsRightParen;

    public MethodCall (Designator Designator, ActParsLeftParen ActParsLeftParen, ActParsOption ActParsOption, ActParsRightParen ActParsRightParen) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ActParsLeftParen=ActParsLeftParen;
        if(ActParsLeftParen!=null) ActParsLeftParen.setParent(this);
        this.ActParsOption=ActParsOption;
        if(ActParsOption!=null) ActParsOption.setParent(this);
        this.ActParsRightParen=ActParsRightParen;
        if(ActParsRightParen!=null) ActParsRightParen.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ActParsLeftParen getActParsLeftParen() {
        return ActParsLeftParen;
    }

    public void setActParsLeftParen(ActParsLeftParen ActParsLeftParen) {
        this.ActParsLeftParen=ActParsLeftParen;
    }

    public ActParsOption getActParsOption() {
        return ActParsOption;
    }

    public void setActParsOption(ActParsOption ActParsOption) {
        this.ActParsOption=ActParsOption;
    }

    public ActParsRightParen getActParsRightParen() {
        return ActParsRightParen;
    }

    public void setActParsRightParen(ActParsRightParen ActParsRightParen) {
        this.ActParsRightParen=ActParsRightParen;
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
        if(Designator!=null) Designator.accept(visitor);
        if(ActParsLeftParen!=null) ActParsLeftParen.accept(visitor);
        if(ActParsOption!=null) ActParsOption.accept(visitor);
        if(ActParsRightParen!=null) ActParsRightParen.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ActParsLeftParen!=null) ActParsLeftParen.traverseTopDown(visitor);
        if(ActParsOption!=null) ActParsOption.traverseTopDown(visitor);
        if(ActParsRightParen!=null) ActParsRightParen.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ActParsLeftParen!=null) ActParsLeftParen.traverseBottomUp(visitor);
        if(ActParsOption!=null) ActParsOption.traverseBottomUp(visitor);
        if(ActParsRightParen!=null) ActParsRightParen.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodCall(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsLeftParen!=null)
            buffer.append(ActParsLeftParen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsOption!=null)
            buffer.append(ActParsOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsRightParen!=null)
            buffer.append(ActParsRightParen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodCall]");
        return buffer.toString();
    }
}

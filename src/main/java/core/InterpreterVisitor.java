package core;

public class InterpreterVisitor implements Expression.Visitor<Object> {

    @Override
    public Object visitBinary(Expression.BinaryExpression exp) {
        Object leftExp = eval(exp.leftOperand);
        Object rightExp = eval(exp.rightOperand);
        TokenType operator = exp.operator;

        switch (operator) {
            case PLUS:
                if (leftExp instanceof Double && rightExp instanceof Double) {
                    System.out.println((double) leftExp + (double) rightExp);
                    return (double) leftExp + (double) rightExp;
                }
            case MINUS:
                if (leftExp instanceof Double && rightExp instanceof Double) {
                    System.out.println((double) leftExp - (double) rightExp);
                    return (double) leftExp - (double) rightExp;
                }
            case STAR:
                if (leftExp instanceof Double && rightExp instanceof Double) {
                    System.out.println((double) leftExp * (double) rightExp);
                    return (double) leftExp * (double) rightExp;
                }
            case DIV:
                if (leftExp instanceof Double && rightExp instanceof Double) {
                    System.out.println((double) leftExp / (double) rightExp);
                    return (double) leftExp / (double) rightExp;
                }
            default:
                throw new RuntimeException("Error");
        }
    }

    @Override
    public Object visitGrouping(Expression.GroupingExpression exp) {
        return eval(exp.expression);
    }

    @Override
    public Object visitLiteral(Expression.LiteralExpression exp) {
        return exp.value;
    }

    public Object eval(Expression exp) {
        return exp.accept(this);
    }

}

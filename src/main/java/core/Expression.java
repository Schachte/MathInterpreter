package core;

public abstract class Expression {

    public interface Visitor<T> {
        T visitBinary(BinaryExpression exp);

        T visitGrouping(GroupingExpression exp);

        T visitLiteral(LiteralExpression exp);
    }

    static class BinaryExpression extends Expression {
        public BinaryExpression(TokenType operator, Expression leftOperand, Expression rightOperand) {
            this.operator = operator;
            this.leftOperand = leftOperand;
            this.rightOperand = rightOperand;
        }

        final TokenType operator;
        final Expression leftOperand;
        final Expression rightOperand;

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitBinary(this);
        }
    }

    static class GroupingExpression extends Expression {
        public GroupingExpression(Expression expression) {
            this.expression = expression;
        }

        final Expression expression;

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitGrouping(this);
        }
    }

    static class LiteralExpression extends Expression {
        public LiteralExpression(Object value) {
            this.value = value;
        }

        final Object value;

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitLiteral(this);
        }
    }

    abstract <T> T accept(Visitor<T> visitor);
}

package core;

import java.util.List;

import static core.TokenType.*;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expression parse() {
        return expression();
    }

    Expression expression() {
        Expression expression = multExpression();
        while (matchType(PLUS, MINUS)) {
            TokenType operator = getPrevious().tokenType;
            Expression rightExpression = multExpression();
            return new Expression.BinaryExpression(operator, expression, rightExpression);
        }
        return expression;
    }

    Expression multExpression() {
        Expression expression = terminal();
        while (matchType(STAR, DIV)) {
            TokenType operator = getPrevious().tokenType;
            Expression rightExpression = terminal();
            return new Expression.BinaryExpression(operator, expression, rightExpression);
        }
        return expression;
    }

    TokenType consume() {
        if (!isAtEnd()) current++;
        return getPrevious().tokenType;
    }

    Token getPrevious() {
        return tokens.get(current - 1);
    }

    Expression terminal() {
        if (matchType(NUMBER)) {
            return new Expression.LiteralExpression(getPrevious().literal);
        }

        if (matchType(LEFT_PAREN)) {
            Expression expression = expression();
            consume();
            return new Expression.GroupingExpression(expression);
        }

        throw new IllegalArgumentException("Invalid input");
    }

    private boolean isAtEnd() {
        return peek().tokenType == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    boolean matchType(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                consume();
                return true;
            }
        }
        return false;
    }

    boolean check(TokenType type) {
        return tokens.get(current).tokenType == type;
    }
}

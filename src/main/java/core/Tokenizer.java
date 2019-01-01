package core;

import java.util.ArrayList;
import java.util.List;

import static core.TokenType.*;
import static java.lang.Character.isDigit;

public class Tokenizer {
    private static final char DOT = '.';
    final List<Token> tokenList = new ArrayList<Token>();
    final String source;
    int current = 0;
    int start = 0;
    int line = 0;

    public Tokenizer(String source) {
        // Read in each character of our input source
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (sourceAlive()) {
            // Turn start into the start of the current token we are analyzing
            start = current;
            scanToken();
        }
        addToken(EOF);
        return tokenList;
    }

    private void scanToken() {
        char c = consumeToken();
        switch (c) {
            case '+':
                addToken(PLUS);
                break;
            case '-':
                addToken(MINUS);
                break;
            case '/':
                addToken(DIV);
                break;
            case '*':
                addToken(STAR);
                break;
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
            case '\n':
                line++;
                break;
            default:
                if (isDigit(c)) {
                    addNumber();
                }
                break;
        }
    }

    private char consumeToken() {
        current++;
        return source.charAt(current - 1);
    }

    /**
     * Allow us to look at the next character in the input sequence
     *
     * @return next character in the input sequence
     */
    private char peek() {
        if (!sourceAlive()) {
            return '\0';
        }
        return source.charAt(current);
    }

    /**
     * The goal is to consume all tokens pertinent to a single integer/floating point input value
     */
    private void addNumber() {
        while (isDigit(peek())) consumeToken();

        if (peek() == DOT) {
            consumeToken();
            while (isDigit(peek())) {
                consumeToken();
            }
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private void addToken(TokenType tokenType) {
        addToken(tokenType, null);
    }

    private void addToken(TokenType tokenType, Object literal) {
        String lexeme = source.substring(start, current);
        tokenList.add(new Token(tokenType, lexeme, literal, line));
    }

    private boolean sourceAlive() {
        return current < source.length();
    }
}

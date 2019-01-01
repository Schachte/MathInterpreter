package core;

/**
 * Data wrapper for the tokens we generate from the input source
 */
public class Token {

    final TokenType tokenType;
    final String lexeme;
    final Object literal;
    final int line;

    public Token(TokenType tokenType, String lexeme, Object literal, int line) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.line = line;
        this.literal = literal;
    }

    @Override
    public String toString() {
        return tokenType + " " + lexeme + " " + literal;
    }
}

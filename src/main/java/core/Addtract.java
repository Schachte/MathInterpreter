package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Addtract {
    public static void main(String[] args) throws IOException {
        runRepl();
    }

    // Initialize the REPL for Addtract
    private static void runRepl() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (; ; ) {
            System.out.print(">>>");
            run(reader.readLine());
        }
    }

    private static void run(String source) {
        Tokenizer tokenizer = new Tokenizer(source);
        List<Token> tokens = tokenizer.scanTokens();
        tokens
                .stream()
                .forEach(token -> System.out.println(token));
        Parser parser = new Parser(tokens);
        Expression exp = parser.parse();
        InterpreterVisitor vis = new InterpreterVisitor();
        vis.eval(exp);
    }
}

package ru.mirea;

import java.util.Queue;

public class Main {
    public static void main(String[] args) throws Exception {
        Queue<Token> tokens = Lexer.getTokenList("a = 0" +
                "value = 7" +
                "c = b * 2 - 1" +
                "d = (a + b) / 15.5");
        Parser parser = new Parser();
        parser.parse(tokens);
    }
}

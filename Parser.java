package ru.mirea;

import java.util.Queue;

public class Parser {
    private Queue<Token> tokens;
    private Token currentToken;

    private void checkToken(String appropriateType) {
        match();
        String currentTokenType = currentToken.getType();
        if (!currentTokenType.equals(appropriateType)) {
            error(currentTokenType, appropriateType);
        }
    }

    private void error(String currentTokenType, String appropriateType) {
        System.out.printf("Ошибка: ожидается %s, найден %s\n", appropriateType, currentTokenType);
    }

    private void match() {
        currentToken = tokens.poll();
    }

    public void parse(Queue<Token> tokens) {
        this.tokens = tokens;

        while (!tokens.isEmpty()) {
            lang();
        }
    }

    private void lang() {
        expr();
    }

    private void expr() {
        assignExpr();
    }

    private void assignExpr() {
        var();
        assignOp();
        assignValue();
    }

    private void assignValue() {
        arithmExpr();
    }

    private void inBr() {
        openBracket();
        arithmExpr();
        closeBracket();
    }

    private void arithmExpr() {
        operand();
        while (tokens.peek() != null && tokens.peek().getType().equals("ARITHMETIC_OP")) {
            arOp();
            operand();
        }
    }

    private void operand() {
        Token nextToken = tokens.peek();
        if (nextToken.getType().equals("OPEN_BRACKET")) {
            inBr();
        } else {
            singleOperand();
        }
    }

    private void singleOperand() {
        String type = tokens.peek().getType();
        switch (type) {
            case "VAR":
                var();
                break;
            case "CONST_INT":
                constInt();
                break;
            case "CONST_FLOAT":
                constFloat();
                break;
            default:
                error(type, "VAR | CONST_INT | CONST_FLOAT");
                tokens.remove();
        }
    }

    private void var() {
        checkToken("VAR");
    }

    private void assignOp() {
        checkToken("ASSIGN_OP");
    }

    private void openBracket() {
        checkToken("OPEN_BRACKET");
    }

    private void closeBracket() {
        checkToken("CLOSE_BRACKET");
    }

    private void arOp() {
        checkToken("ARITHMETIC_OP");
    }

    private void constInt() {
        checkToken("CONST_INT");
    }

    private void constFloat() {
        checkToken("CONST_FLOAT");
    }
}

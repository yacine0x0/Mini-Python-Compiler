package com.mini_compiler;

import java.util.List;

public class Syntax_analyzer {
    private static String[] currentToken;
    private static int index = 0;
    private static List<String[]> TOKENS = Lexical_analyzer.TOKENS;
    private static boolean success = false;

    private static void nextToken() {
        index++;
        if (index < TOKENS.size()) {
            currentToken = TOKENS.get(index);
        }
    }

    private static boolean AR_LO_OPERATOR() {
        String[] OPERATORS = { "and", "not", "<<=", ">>=", "**=", "//=", "or", "==", "!=", ">=", "<=", "//", "**", "<<",
                ">>", "=", "+", "-", "*", "/", "%", ">", "<", "!" };

        return false;
    }

    private static boolean COMBINED_OPERATOR() {
        String[] LONG_OPERATORS = { "+=", "%=", "-=", "*=", "/=" };

        return false;
    }

    public static void Analyzer() {
        TOKENS.add(new String[] { "#", "#" });
        index = 0;
        currentToken = TOKENS.get(index);

        Start();

        if (index == TOKENS.size() - 1 && currentToken[0] == "#" && Error_handler.getErrorCount() > 0) {
            success = true;
        }
    }

    private static void Start() {

        if (currentToken[0] == "def" || currentToken[0] == "class") {
            DECLARATION();
        } else if (currentToken[1] == "IDENTIFIER") {
            ASSIGNING();
        } else if (currentToken) {

        }

    }

}

package com.mini_compiler;

import java.util.List;
import java.util.Objects;

public class Syntax_analyzer {
    private static String[] currentToken;
    private static String[] currentPosition;
    private static int index = 0;

    // These reference the lists produced by the lexical analyzer.
    // NOTE: do NOT clear these here in reset() — the caller (GUI) should clear them
    // when appropriate
    // before calling Lexical_analyzer.Analyzer().
    private static List<String[]> TOKENS = Lexical_analyzer.TOKENS;
    private static List<String[]> POSITION = Lexical_analyzer.POSITION;

    private static final int UNEXPECTED_TOKEN = 0;
    private static final int EXPECTED_NEWLINE = 1;
    private static final int EXPECTED_PARANTHESE_CLOSING = 2;
    private static final int EXPECTED_COLON = 3;
    private static final int EXPECTED_LOGICAL_OP = 4;
    private static final int SPECIAL_KEYWORD = 5;
    private static final int EXPECTED_SPACE = 6;
    private static final int EXPECTED_DEDENT = 7;
    private static final int EXPECTED_CONDITION = 8;
    private static final int EXPECTED_ATLEAST_ONE_INSTRUCTION = 9;
    private static final int UNKNOWN_ERROR = -1;

    /**
     * Advance to next token. If we advance past the provided token lists,
     * populate a synthetic EOF token so parsing loops terminate gracefully.
     */
    private static void nextToken() {
        index++;
        if (index < TOKENS.size() && index < POSITION.size()) {
            currentToken = TOKENS.get(index);
            currentPosition = POSITION.get(index);
        } else {
            // Create a safe EOF marker if we run out of tokens
            currentToken = new String[] { "EOF", "EOF" };
            currentPosition = new String[] { "-1", "-1" };
        }
    }

    /**
     * Reset parser internal state (index/currentToken). Do NOT clear
     * TOKENS/POSITION here
     * because the lexical analyzer will refill them; clearing them here would lose
     * tokens.
     */
    public static void reset() {
        index = 0;
        currentToken = null;
        currentPosition = null;
        TOKENS.clear();
        POSITION.clear();
    }

    // Null-safe string equality
    private static boolean ifEquals(String a, String b) {
        return a != null && a.equals(b);
    }

    // Null-or-empty check
    private static boolean ifEmpty(String lexical_unit) {
        return lexical_unit == null || lexical_unit.isEmpty();
    }

    /**
     * Entry point of syntax analyzer.
     */
    public static void program() {
        index = 0;
        if (TOKENS == null || POSITION == null || TOKENS.isEmpty() || POSITION.isEmpty()) {
            return;
        }

        // Add EOF marker only if last token isn't EOF
        String[] lastTok = TOKENS.get(TOKENS.size() - 1);
        if (lastTok == null || !ifEquals(lastTok[0], "EOF")) {
            TOKENS.add(new String[] { "EOF", "EOF" });
            POSITION.add(new String[] { "-1", "-1" });
        }

        // initialize current token/pos
        currentToken = TOKENS.get(index);
        currentPosition = POSITION.get(index);

        // parse the program as a list of statements
        statementList();
    }

    private static void statementList() {
     /*   // If currentToken is null, set the safe EOF to avoid infinite loops
        if (currentToken == null) {
            currentToken = new String[] { "EOF", "EOF" };
            currentPosition = new String[] { "-1", "-1" };
        } */

        while (!ifEquals(currentToken[0], "EOF")) {
            statement();
            // defensive check: if parsing did not advance, break to avoid infinite loop
            if (index >= TOKENS.size() - 1 && ifEquals(currentToken[0], "EOF"))
                break;
        }
    }

    private static void statement() {
        if (currentToken == null || ifEquals(currentToken[0], "EOF")) {
            return; //
        }

        if (ifEquals(currentToken[1], "INDENT") || ifEquals(currentToken[1], "DEDENT")) {
            nextToken();
            return;
        }

        else if (ifEquals(currentToken[1], "SPECIAL_KEYWORD")) {
            Error_handler.setSyntaxErrorMessage(SPECIAL_KEYWORD,
                    currentToken != null ? currentToken[0] : "null",
                    currentPosition != null ? currentPosition[0] : "-1",
                    currentPosition != null ? currentPosition[1] : "-1");
            nextToken();
            return;
        }

        else if (ifEquals(currentToken[1], "IDENTIFIER")) {
            Assignement();
            return;
        } else if (ifEquals(currentToken[0], "while")) {
            Whileloop();
            return;
        } else if (ifEquals(currentToken[0], "NEWLINE")) {
            Emptyline();
            return;
        } else if (ifEquals(currentToken[0], "def") || ifEquals(currentToken[0], "class")) {
            // Declaration();
            nextToken();
            return;
        } else {
            Error_handler.setSyntaxErrorMessage(UNKNOWN_ERROR,
                    currentToken != null ? currentToken[0] : "null", currentPosition[0],
                    currentPosition != null ? currentPosition[1] : "-1");
            nextToken();
            return;
        }
    }

    private static void Emptyline() {
        if (ifEquals(currentToken[0], "NEWLINE")) {
            nextToken();
        }
    }

    private static void Assignement() {
        // current token is IDENTIFIER (lexical class in currentToken[1])
        if (ifEquals(currentToken[1], "IDENTIFIER")) {
            nextToken(); // consume identifier
            OperatorAssign(); // expect an assignment operator (or error)
            Expression(); // parse RHS expression
            if (ifEquals(currentToken[0], "NEWLINE") || ifEquals(currentToken[0], "EOF")) {
                nextToken();
            } else {
                Error_handler.setSyntaxErrorMessage(EXPECTED_NEWLINE,
                        currentToken != null ? currentToken[0] : "null",
                        currentPosition != null ? currentPosition[0] : "-1",
                        currentPosition != null ? currentPosition[1] : "-1");
                // try to recover by consuming the current token
                nextToken();
            }
        }
    }

    private static void OperatorAssign() {
        String[] operators = { "=", "+=", "-=", "*=", "/=", "%=" };

        for (String op : operators) {
            if (ifEquals(currentToken[0], op)) {
                nextToken();
                return;
            }
        }

        Error_handler.setSyntaxErrorMessage(UNEXPECTED_TOKEN,
                currentToken != null ? currentToken[0] : "null",
                currentPosition != null ? currentPosition[0] : "-1",
                currentPosition != null ? currentPosition[1] : "-1");

        // try to recover by consuming the token
        nextToken();
    }

    /**
     * Expression -> Term { ( + | - | * | / | % ) Term }
     * This is iterative and handles a chain of binary ops.
     */
    private static void Expression() {
        Term();

        // loop while current token is a binary arithmetic operator
        while (currentToken != null && (ifEquals(currentToken[0], "+") ||
                ifEquals(currentToken[0], "-") ||
                ifEquals(currentToken[0], "*") ||
                ifEquals(currentToken[0], "/") ||
                ifEquals(currentToken[0], "%"))) {
            // consume operator
            nextToken();
            // parse the next term (if missing, report error)
            if (currentToken == null ||
                    !(ifEquals(currentToken[1], "IDENTIFIER") ||
                            ifEquals(currentToken[1], "INTEGER") ||
                            ifEquals(currentToken[1], "FLOAT") ||
                            ifEquals(currentToken[1], "BOOLEAN") ||
                            ifEquals(currentToken[0], "None") ||
                            ifEquals(currentToken[1], "STRING") ||
                            ifEquals(currentToken[0], "("))) {
                Error_handler.setSyntaxErrorMessage(UNEXPECTED_TOKEN,
                        currentToken != null ? currentToken[0] : "null",
                        currentPosition != null ? currentPosition[0] : "-1",
                        currentPosition != null ? currentPosition[1] : "-1");
                // try to continue
            } else {
                Term();
            }
        }
    }

    private static void ExpressionPrime() {
        // no longer used (kept for compatibility). Expression() handles repetition
        // iteratively.
    }

    private static void Term() {
        if (currentToken == null) {
            // unexpected end

            return;
        }

        if (ifEquals(currentToken[1], "IDENTIFIER")
                || ifEquals(currentToken[1], "INTEGER")
                || ifEquals(currentToken[1], "FLOAT")
                || ifEquals(currentToken[1], "BOOLEAN")
                || ifEquals(currentToken[0], "None")
                || ifEquals(currentToken[1], "STRING")) {
            nextToken();
            return;
        } else if (ifEquals(currentToken[0], "(")) {
            nextToken();
            Expression();
            if (ifEquals(currentToken[0], ")")) {
                nextToken();
                return;
            } else {
                Error_handler.setSyntaxErrorMessage(EXPECTED_PARANTHESE_CLOSING,
                        currentToken != null ? currentToken[0] : "null",
                        currentPosition != null ? currentPosition[0] : "-1",
                        currentPosition != null ? currentPosition[1] : "-1");
                // attempt to recover
                nextToken();
                return;
            }
        } else {
            Error_handler.setSyntaxErrorMessage(UNEXPECTED_TOKEN,
                    currentToken != null ? currentToken[0] : "null",
                    currentPosition != null ? currentPosition[0] : "-1",
                    currentPosition != null ? currentPosition[1] : "-1");
            nextToken();
            return;
        }
    }

    private static void Whileloop() {
        if (ifEquals(currentToken[0], "while")) {
            nextToken();
            Condition();
            if (ifEquals(currentToken[0], ":")) {
                nextToken();
                if (ifEquals(currentToken[0], "NEWLINE")) {
                    nextToken();
                    Block();
                    return;
                } else {
                    Error_handler.setSyntaxErrorMessage(EXPECTED_NEWLINE,
                            currentToken != null ? currentToken[0] : "null",
                            currentPosition != null ? currentPosition[0] : "-1",
                            currentPosition != null ? currentPosition[1] : "-1");

                    nextToken();
                    return;
                }
            } else {
                Error_handler.setSyntaxErrorMessage(EXPECTED_COLON,
                        currentToken != null ? currentToken[0] : "null",
                        currentPosition != null ? currentPosition[0] : "-1",
                        currentPosition != null ? currentPosition[1] : "-1");
                nextToken();
                return;
            }
        }
    }

    private static void Condition() {
       if (ifEquals(currentToken[0], ":")) {
         Error_handler.setSyntaxErrorMessage(EXPECTED_CONDITION,
                        currentToken != null ? currentToken[0] : "null",
                        currentPosition != null ? currentPosition[0] : "-1",
                        currentPosition != null ? currentPosition[1] : "-1");
                return;
       }
       else if (ifEquals(currentToken[0], "not")) {
            nextToken();
            Expression();
        } else {
            Expression();
            CompOperator();
            Expression();
        }
    }

    private static void CompOperator() {
        String[] operators = { "==", "!=", "<", ">", "<=", ">=", "and", "or" };

        for (String op : operators) {
            if (ifEquals(currentToken[0], op)) {
                nextToken();
                return;
            }
        }

        Error_handler.setSyntaxErrorMessage(EXPECTED_LOGICAL_OP,
                currentToken != null ? currentToken[0] : "null",
                currentPosition != null ? currentPosition[0] : "-1",
                currentPosition != null ? currentPosition[1] : "-1");

        // try to recover
        nextToken();
    }

    private static void Block() {

        while (ifEquals(currentToken[0], "NEWLINE")) {
            nextToken();
        }

        // 1. Le bloc DOIT commencer par INDENT
        if (ifEquals(currentToken[0], "EOF")) {
             Error_handler.setSyntaxErrorMessage(EXPECTED_ATLEAST_ONE_INSTRUCTION, currentToken[0], currentPosition[0],
                    currentPosition[1]); 
        }

        if (!ifEquals(currentToken[0], "INDENT")) {
            if (ifEquals(currentToken[0], "DEDENT")) {
                nextToken();
            }
            Error_handler.setSyntaxErrorMessage(EXPECTED_SPACE, currentToken[0], currentPosition[0],
                    currentPosition[1]);
            return;
        }
        nextToken(); // consomme INDENT

     if (ifEquals(currentToken[0], "NEWLINE")) {
        Error_handler.setSyntaxErrorMessage(EXPECTED_ATLEAST_ONE_INSTRUCTION, currentToken[0], currentPosition[0],
                    currentPosition[1]); nextToken();
     }

        // 2. statementlist : on lit des statements jusqu'à DEDENT
        while (!ifEquals(currentToken[0], "DEDENT") &&
                !ifEquals(currentToken[0], "EOF")) {

                    
            statement();

        }


        if (ifEquals(currentToken[0], "EOF")) {
            return;
        }
        
        else if (ifEquals(currentToken[0], "DEDENT") || ifEquals(currentToken[0], "EOF")) {
                    nextToken(); // consomme DEDENT
            } 
            else {
            // Pas de DEDENT trouvé → erreur
            Error_handler.setSyntaxErrorMessage(EXPECTED_DEDENT, currentToken[0], currentPosition[0],
                    currentPosition[1]); nextToken();
                 }
   

        
   
    }

}

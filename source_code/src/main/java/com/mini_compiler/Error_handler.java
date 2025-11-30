package com.mini_compiler;

public class Error_handler {

private static String lexical_error_message = "Lexical Error: ";
private static String syntax_error_message = "Syntax Error: ";
private static int count_error = 0;

public static void setLexicalErrorMessage(int ERROR_CODE, String token, int line_number, int column_number) {
    lexical_error_message = "Lexical Error: ";
    count_error++;
    switch (ERROR_CODE) {
        case 0:
            lexical_error_message = "File not found!";
            break;
        case 1:
            lexical_error_message += "Unrecognized token '" + token + "' at line " + line_number + ", column " + column_number + ".";
            break;
        case 2:
            lexical_error_message += "Unclosed string literal starting at line " + line_number + ", column " + column_number + ".";
            break;
        default:
            lexical_error_message = "Unknown Error at line " + line_number + ", column " + column_number + ".";
            break;
    }
}

private static void setSyntaxErrorMessage(int ERROR_CODE, String token, int line_number, int column_number) {
    syntax_error_message = "Syntax Error: ";
    count_error++;
    switch (ERROR_CODE) {
        case 0:
            syntax_error_message += "Unexpected token '" + token + "' at line " + line_number + ", column " + column_number + ".";
            break;
    
        default:
            syntax_error_message = "Unknown Syntax Error at line " + line_number + ", column " + column_number + ".";
            break;
    }
}

}

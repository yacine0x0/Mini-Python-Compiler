package com.mini_compiler;

import java.util.ArrayList;

public class Error_handler {

private static String lexical_error_message = "Lexical Error: ";
private static String syntax_error_message = "Syntax Error: ";
private static ArrayList<String>  ALL_ERRORS = new ArrayList<>();
private static int count_error = 0;

public static void setLexicalErrorMessage(int ERROR_CODE, String token, int line_number, int column_number) {
    lexical_error_message = "Lexical Error: ";
    count_error++;
    switch (ERROR_CODE) {
        case 0:
            lexical_error_message = "File not found!\n";
            ALL_ERRORS.add(lexical_error_message);
            break;
        case 1:
            lexical_error_message += "Unrecognized token '" + token + "' at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(lexical_error_message);
            break;
        case 2:
            lexical_error_message += "Unclosed string literal starting at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(lexical_error_message);
            break;
        default:
            lexical_error_message = "Unknown Error at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(lexical_error_message);
            break;
    }
}

public static void setSyntaxErrorMessage(int ERROR_CODE, String token, int line_number, int column_number) {
    syntax_error_message = "Syntax Error: ";
    count_error++;
    switch (ERROR_CODE) {
        case 0:
            syntax_error_message += "Unexpected token '" + token + "' at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;
    
        default:
            syntax_error_message = "Unknown Syntax Error at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;
    }
}

public static ArrayList getErrors(){
    return ALL_ERRORS;
}

public static int getErrorCount(){
    return count_error;
}

public static void ResetALLERRORS(){
    ALL_ERRORS.clear();
}

public static void ResetCountError(){
    count_error = 0;
}

}

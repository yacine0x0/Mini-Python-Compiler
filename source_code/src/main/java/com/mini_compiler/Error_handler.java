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

public static void setSyntaxErrorMessage(int ERROR_CODE, String token, String line_number, String column_number) {
    syntax_error_message = "Syntax Error: ";
    count_error++;
    switch (ERROR_CODE) {
        case 0:
            if (token.equals("NEWLINE")) {
            syntax_error_message += "Expected something  at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;    
            }
            else{
            syntax_error_message += "Unexpected token '" + token + "' at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;}
    
        case 1 : if (token.equals("EOF") || token.equals("NEWLINE")) {
            syntax_error_message = ""; break;
        } 
    else{syntax_error_message += "Expected new line before '" + token + "' at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;}
        

        case 2 : 
        syntax_error_message += "Expected ')' around '" + token + "' at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;    

        case 3:
            if (token.equals("NEWLINE") || token.equals("EOF")) {
                syntax_error_message += "Expected ':' after def | class or while expression";
            ALL_ERRORS.add(syntax_error_message);
            break;
            }
            else{syntax_error_message += "Expected ':' around '" + token + "' at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;}
              

        case 4:    
          syntax_error_message += "Expected logical operator around '" + token + "' at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;  

        case 5:
            syntax_error_message = "Thanks for mentioning author's name '" + token + "' at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;  
        case 6:
            if (token.equals("EOF")) {
                syntax_error_message = "";
            }
            else{syntax_error_message += "Expected space before '" + token + "' at line " + line_number + ", column " + column_number + ".\n";}
            ALL_ERRORS.add(syntax_error_message);
            break;  

        case 7:
            syntax_error_message += "Expected return to line at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;      

        case 8:  syntax_error_message += "Expected condition before '" + token + "' at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;      

        case 9: if (token.equals("EOF")) {
            syntax_error_message += "Expected at least one instruction in while block.\n";

        }  
        else{syntax_error_message += "Expected at least one instruction in while block at line " + line_number + ", column " + column_number + ".\n";}
            ALL_ERRORS.add(syntax_error_message);
            break;      
            
        case 10: if (token.equals("EOF")) {
            syntax_error_message += "Unexpected factor.\n";
        }
                syntax_error_message += "Unexpected factor '" + token + "' in expression at line " + line_number + ", column " + column_number + ".\n";
            ALL_ERRORS.add(syntax_error_message);
            break;      

        case 11: if (token.equals("EOF")) {
            syntax_error_message += "Expected '(' first in declaration.\n";
        }
                syntax_error_message += "expected '(' before'" + token + "' in declaration at line " + line_number + ", column " + column_number + ".\n";
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

package com.mini_compiler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yacine Madani
 * @version 1.0
 * @since November 2025
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Lexical_analyzer {

    private static final String[] KEYWORDS = { "if", "else", "switch", "while", "do", "class", "import", "break",
            "contine", "def", "return", "for", "finally", "elif", "from", "try", "as", "except", "raise"};

    private static final String[] SPECIAL_KEYWORD = { "Yacine", "Madani" };
    private static final String[] LOGICAL_OPERATORS = {"and","or","not"};

    public static List<String[]> TOKENS = new ArrayList<>(); //list to store tokens

    private static final int LEXICAL_ERROR = 2;

    // current character checker for float indicator only
    private static int terme_float(char tc) {
        if (tc >= '0' && tc <= '9') {
            return 0;
        }
        if (tc == '.') {
            return 1;
        }
        return 2;
    }

    // Float data type indicator
    private static boolean constantIsFloat(String lexical_unit) {
        int[][] matrice = {
                { 1, -1, -1 },
                { 1, 2, -1 },
                { 3, -1, -1 },
                { 3, -1, -1 } };

        lexical_unit = lexical_unit + "#";
        int i = 0;
        int etat_courant = 0;
        int vf = 3;
        while (lexical_unit.charAt(i) != '#' && matrice[etat_courant][terme_float(lexical_unit.charAt(i))] != -1) {

            etat_courant = matrice[etat_courant][terme_float(lexical_unit.charAt(i))];

            i++;
        }

        if ((lexical_unit.charAt(i) == '#' && etat_courant == vf) && i == lexical_unit.length() - 1) {
            return true;
        } else {
            return false;
        }
    }

    // current character checker for integers only
    private static int terme_int(char tc) {
        if (tc >= '0' && tc <= '9') {
            return 0;
        }
        return 1;
    }

    // Integer data type indicator
    private static boolean constantIsInteger(String lexical_unit) {
        int[][] matrice = {
                { 1, -1 },
                { 1, -1 } };

        lexical_unit = lexical_unit + "#";
        int i = 0;
        int etat_courant = 0;
        int vf = 1;
        while (lexical_unit.charAt(i) != '#' && matrice[etat_courant][terme_int(lexical_unit.charAt(i))] != -1) {

            etat_courant = matrice[etat_courant][terme_int(lexical_unit.charAt(i))];

            i++;
        }

        if ((lexical_unit.charAt(i) == '#' && etat_courant == vf) && i == lexical_unit.length() - 1) {
            return true;
        } else {
            return false;
        }
    }

    // current character checker terme-courant for identifiers only
    private static int terme(char tc) {
        if (tc == '_') {
            return 0;
        }
        if ((tc >= 'a' && tc <= 'z') || (tc >= 'A' && tc <= 'Z')) {
            return 1;
        }
        if (tc >= '0' && tc <= '9') {
            return 2;
        }
        return 3;
    }

    // Identifier indicator using the matrix way
    private static boolean unitIsIdentifer(String lexical_unit) {
        int[][] matrice = {
                { 1, 2, -1, -1 },
                { 2, 2, 1, -1 },
                { 2, 2, 1, -1 } };

        lexical_unit = lexical_unit + "#";
        int i = 0;
        int etat_courant = 0;
        int vf = 2;
        while (lexical_unit.charAt(i) != '#' && matrice[etat_courant][terme(lexical_unit.charAt(i))] != -1) {

            etat_courant = matrice[etat_courant][terme(lexical_unit.charAt(i))];
            i++;
        }

        if ((lexical_unit.charAt(i) == '#' && etat_courant == vf) && i == lexical_unit.length() - 1) {
            return true;
        } else {
            return false;
        }
    }

    //testing if two strings are identical
    private static boolean ifEquals(String str1, String str2){
        int indexStr1 = 0;
        int indexStr2 = 0;
        if (str1.length()!=str2.length()) {
            return false;
        }
        do {
            if (str1.charAt(indexStr1) != str2.charAt(indexStr2)) {
                return false;
            }
            indexStr1++;
            indexStr2++;
        } while (indexStr1<str1.length() && indexStr2<str2.length());
        return true;
    }

    //testing if string is empty
    private static boolean ifEmpty (String lexical_unit){
        lexical_unit = lexical_unit + "#";
        if (lexical_unit == null || ifEquals(lexical_unit,"#")) {
            return true;
        }

        return false;
    }



    public static String Tokenizer(String lexical_unit) {
        if (!ifEmpty(lexical_unit) && !ifEquals(lexical_unit," ")) {
            
        
        for (String keyword : KEYWORDS) {
            if (ifEquals(lexical_unit,keyword)) {
                return "KEYWORD";
            }
        }
        for (String special : SPECIAL_KEYWORD) {
            if (ifEquals(lexical_unit,special)) {
                return "SPECIAL_KEYWORD";
            }
        } 
        for (String logical : LOGICAL_OPERATORS) {
            if (ifEquals(lexical_unit,logical)) {
                return "LOGICAL_OPERATOR";
                
            }
        }


        if (ifEquals(lexical_unit,"False") || ifEquals(lexical_unit,"True")) {
            return "BOOLEAN";
        }
        if (ifEquals(lexical_unit,"None")) {
            return "None";
        }
        if (unitIsIdentifer(lexical_unit)) {
            return "IDENTIFIER";
        }
        if (constantIsFloat(lexical_unit)) {
            return "FLOAT";
        }
        if (constantIsInteger(lexical_unit)) {
            return "INETEGER";
        }
        if (constantIsString(lexical_unit)) {
            return "STRING";
        }
        // CALL ERROR FUNCTION HERE !!!!

        return "error";
    }
    return "error";
    }

    //separator tokenizer it indicates if it's , or ; or ...etc and it extracts it as a token
    private static boolean terme_separator(char tc){
        if (tc == ';') {
            return true;
        }
        if (tc == ',') {
            return true;
        }
        if (tc == '\'') {
            return true;
        }
        if (tc == ':') {
            return true;
        }
        if (tc == '(') {
            return true;
        }
        if (tc == ')') {
            return true;
        }
        if (tc == '{') {
            return true;
        }
        if (tc == '}') {
            return true;
        }
        if (tc == '[') {
            return true;
        }
        if (tc == ']') {
            return true;
        }


        
        return false;
    }
    
    //operator tokenizer it indicates when there's a + or - or ...etc and extracts it as a token
    private static boolean terme_operator(char tc){
        if (tc == '+') {
            return true;
        }
        if (tc == '-') {
            return true;
        }
         if (tc == '*') {
            return true;
        }
         if (tc == '/') {
            return true;
        }
         if (tc == '%') {
            return true;
        }
         if (tc == '=') {
            return true;
        }
         if (tc == '!') {
            return true;
        }
         if (tc == '>') {
            return true;
        }
         if (tc == '<') {
            return true;
        }
        return false;
    }   

    private static int terme_string(char tc){
        if (tc == '"') {
            return 0;
        }
        return 1;
    }

    private static boolean constantIsString(String lexical_unit){
        lexical_unit = lexical_unit + "#";
        int i = 0;
        int etat_courant = 0;
        int vf = 2;
        int[][] matrice = {
            {1,-1},
            {2,1},
            {-1,-1}
        };
        while (lexical_unit.charAt(i) != '#' && matrice[etat_courant][terme_string(lexical_unit.charAt(i))] != -1) {

            etat_courant = matrice[etat_courant][terme_string(lexical_unit.charAt(i))];
            i++;
        }

        if ((lexical_unit.charAt(i) == '#' && etat_courant == vf) && i == lexical_unit.length() - 1) {
            return true;
        } else {
            return false;
        }
    }

    // --- Method to analyze the given code ---

    public static void Analyzer() throws FileNotFoundException, URISyntaxException {
        String lexical_unit = "";
        String input = "";
        char tc = ' ';
        int index = 0;
        boolean scanning_string = false; // to let strings data types have all entered characters including spaces

        int scanning_string_line = 0;
        int count_lines = 0;

        //setting up file path
        String jarDir = new File(
        Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();

        File file = new File(jarDir, "code.py");

       

        // Scanning input from a file "code.py" located in "executable" folder
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) { //extracting line by line from code.py
                input = scan.nextLine() + " ^"; //the character ^ means the end of a line
                index = 0;
                count_lines++;
                

                while (!ifEquals(input," ^") && input.charAt(index) != '^' && input.charAt(index) != '#') {

                if (scanning_string && (scanning_string_line != count_lines)) {
                    //error to call here: unclosed string
                }    
                 tc = input.charAt(index);

                              // Skip spaces
                  if (tc == ' ' || tc == '\t') {
                    if (!ifEmpty(lexical_unit)) {
                        TOKENS.add(new String[]{lexical_unit, Tokenizer(lexical_unit)});
                        lexical_unit = "";
                    }
                      index++;
                      continue;
                  }
                
                  // STRING START
                  if (tc == '"' && !scanning_string) {
                      scanning_string = true;
                      scanning_string_line = count_lines;
                      
                      if (!ifEmpty(lexical_unit)) {
                        TOKENS.add(new String[]{lexical_unit, Tokenizer(lexical_unit)});
                        lexical_unit = "";
                      }

                      lexical_unit += tc;
                      index++;
                      continue;
                  }

                    // STRING CONTENT
                    if (scanning_string) {
                        lexical_unit += tc;

                        if (tc == '"') {
                            scanning_string = false;
                            TOKENS.add(new String[]{lexical_unit, Tokenizer(lexical_unit)});
                            lexical_unit = "";
                        }
                    
                        index++;
                        continue;
                    }

                        // SEPARATOR
                        if (terme_separator(tc)) {
                            if (!ifEmpty(lexical_unit)) {
                                TOKENS.add(new String[]{lexical_unit, Tokenizer(lexical_unit)});
                                lexical_unit = "";
                            }
                            TOKENS.add(new String[]{Character.toString(tc), "SEPARATOR"});
                            index++;
                            continue;
                        }

                        // OPERATOR
                        if (terme_operator(tc)) {
                            if (!ifEmpty(lexical_unit)) {
                                TOKENS.add(new String[]{lexical_unit, Tokenizer(lexical_unit)});
                                lexical_unit = "";
                            }
                            TOKENS.add(new String[]{Character.toString(tc), "OPERATOR"});
                            index++;
                            continue;
                        }
                    
                        // DEFAULT: part of an identifier/keyword/number
                        lexical_unit += tc;
                        if (index < input.length()) {
                        index++;    
                        }
                        
                }

            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

    }

}

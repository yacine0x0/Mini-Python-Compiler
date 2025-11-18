package com.mini_compiler;

/**
 * @author Yacine Madani
 * @version 1.0
 * @since November 2025
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Lexical_analyzer {

    private static final String[] KEYWORDS = { "if", "else", "switch", "while", "do", "class", "import", "break",
            "contine", "def", "return", "for", "finally", "elif", "from", "try", "as", "except", "raise", "int",
            "float", "complex", "str", "bool" };

    private static final String[] SPECIAL_KEYWORD = { "Yacine", "Madani" };
    private static final String[] OPERATORS = { "+", "-", "*", "/", "%", "++", "--", "==", "!=", "<", ">", "<=", ">=",
            "and", "or", "!", "=" };

    private static final String[] SEPARATORS = { "(", ")", "{", "}", "[", "]", ",", ";", ":", ".", "'", "\"" };

    private static final int LEXICAL_ERROR = 2;

    // current character checker for complex indicator only
    private static int terme_complex(char tc) {
        if (tc >= '0' && tc <= '9') {
            return 0;
        }
        if (tc == '.') {
            return 1;
        }
        if (tc == '+') {
            return 2;
        }
        if (tc == 'j') {
            return 3;
        }
        return 4;
    }

    // Complex data type indicator
    private static boolean constantIsComplex(String lexical_unit) {
        int[][] matrice = {
                { 1, -1, -1, -1, -1 },
                { 1, 3, 2, -1, -1 },
                { 5, -1, -1, -1, -1 },
                { 4, -1, -1, -1, -1 },
                { 4, -1, 2, -1, -1 },
                { 5, 6, -1, 8, -1 },
                { 7, -1, -1, -1, -1 },
                { 7, -1, -1, 8, -1 },
                { -1, -1, -1, -1, -1 } };

        lexical_unit = lexical_unit + "#";
        int i = 0;
        int etat_courant = 0;
        int vf = 8;
        while (lexical_unit.charAt(i) != '#' && matrice[etat_courant][terme_complex(lexical_unit.charAt(i))] != -1) {

            etat_courant = matrice[etat_courant][terme_complex(lexical_unit.charAt(i))];

            i++;
        }

        if ((lexical_unit.charAt(i) == '#' && etat_courant == vf) && i == lexical_unit.length()-1) {
            return true;
        } else {
            return false;
        }
    }

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

    public static String Tokenizer(String lexical_unit) {

        for (String keyword : KEYWORDS) {
            if (lexical_unit == keyword) {
                return "KEYWORD";
            }
        }
        for (String special : SPECIAL_KEYWORD) {
            if (lexical_unit == special) {
                return "SPECIAL_KEYWORD";
            }
        }
        for (String operator : OPERATORS) {
            if (lexical_unit == operator) {
                return "OPERATOR";
            }
        }
        for (String separator : SEPARATORS) {
            if (lexical_unit == separator) {
                return "SEPARATOR";
            }
        }

        if (unitIsIdentifer(lexical_unit)) {
            return "IDENTIFIER";
        }
        if (constantIsComplex(lexical_unit)) {
            return "COMPLEX";
        }
        if (constantIsFloat(lexical_unit)) {
            return "FLOAT";
        }
        if (constantIsInteger(lexical_unit)) {
            return "INETEGER";
        }

        // CALL ERROR FUNCTION HERE !!!!

        return "error";
    }

    // --- Method to analyze the given code ---

    public static void Analyzer() throws FileNotFoundException {
        String lexical_unit = "";
        String input = "";
        int i = 0;

        // Reading input from a file "code.dat"
        try {
            Scanner scan = new Scanner(new File("Mini-Compilateur-Python/executable/code.dat"));

            while (scan.hasNextLine()) {
                input = scan.nextLine() + "#";
                i = 0;

                while (input.charAt(i) != '#') {

                }

            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

    }

}

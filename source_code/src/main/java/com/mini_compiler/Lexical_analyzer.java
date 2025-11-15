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

    private static final String[] KEYWORDS = {"if","else","switch","while","do","class","import","break","contine","def","return","for","finally","elif","from","try","as","except","raise","int","float","complex","str","bool"};
    
    private static final String[] SPECIAL_KEYWORD = {"Yacine","Madani"};
    private static final String[] OPERATORS = {"+","-","*","/","%","++","--","==","!=","<",">","<=",">=","and","or","!","="};
    
    private static final String[] SEPARATORS = {"(",")","{","}","[","]",",",";",":",".","'","\""};

    private static final int LEXICAL_ERROR = 2;
    

    public static String unitType(String lexical_unit){
        
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
        

        //CALL ERROR FUNCTION HERE !!!!

        return "error";
    }
    
    //--- Method to analyze the given code ---

    public static void Analyzer() throws FileNotFoundException{
        String lexical_unit = "";
        String input = "";
        int i = 0;

            //Reading input from a file "code.dat"
            try {
            Scanner scan = new Scanner(new File("Mini-Compilateur-Python/executable/code.dat"));
            
            while (scan.hasNextLine()){
                input = scan.nextLine() + "#";
                i = 0;

                while(input.charAt(i) != '#'){

                }
                

            }
                
            } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            }


    }



    
}

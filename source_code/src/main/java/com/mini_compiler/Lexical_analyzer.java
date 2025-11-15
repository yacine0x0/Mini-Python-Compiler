package com.mini_compiler;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Lexical_analyzer {

    private static final String[] KEYWORDS = {"if","else","switch","while","do","class","import","break","contine","def","return","for","finally","elif","from","try","as","except","raise","int","float","complex","str","bool"};
    
    private static final String[] SPECIAL_KEYWORD = {"Yacine","Madani"};
    private static final String[] OPERATORS = {"+","-","*","/","%","++","--","==","!=","<",">","<=",">=","and","or","!","="};
    
    private static final String[] SEPARATORS = {"(",")","{","}","[","]",",",";",":",".","'","\""};

    //--- Method to analyze the given code ---

    
    public static void Analyzer() throws FileNotFoundException{
        String lexical_unit = "";
        String input = "";
        int i = 0;

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

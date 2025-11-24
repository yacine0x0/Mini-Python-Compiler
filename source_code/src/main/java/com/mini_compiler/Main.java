package com.mini_compiler;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) {

        try {
            Lexical_analyzer.Analyzer();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (String[] token : Lexical_analyzer.TOKENS) {
        System.out.println(Arrays.toString(token));    
        }
        

    }
}
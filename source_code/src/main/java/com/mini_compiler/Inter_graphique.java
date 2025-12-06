package com.mini_compiler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.*;

public class Inter_graphique extends JFrame {
   private JTextArea codeArea;
   private JButton saveButton,compileButton,eraseButton;
   private JTextArea outputArea;
   private JPanel codePanel,logsPanel,buttonsPanel;

    private static ArrayList<String>  ALL_ERRORS = new ArrayList<>();
    private static int count_error = 0;

     
    private static void savingInFile(JTextArea codeArea, JTextArea outputArea){
            String input = codeArea.getText();  // Get text from JTextArea
                Path filePath = Paths.get("executable/code.py");

                try {
                        Files.write(filePath, input.getBytes());   // Write text into file
                        outputArea.setText("SAVED SUCCESSFULLY");
                    } catch (IOException e) {
                        e.printStackTrace();
                        outputArea.setText("ERROR WHILE SAVING *-* \n");
                    }
    }
 
    private static void emptyFile(JTextArea codeArea, JTextArea outputArea){
             Path filePath = Paths.get("executable/code.py");
        try {
                        Files.write(filePath, new byte[0]); // write nothing into file
                        codeArea.setText("");
                        outputArea.setText("ERASED SUCCESSFULLY");
                    } catch (IOException e) {
                        e.printStackTrace();
                        outputArea.setText("ERROR WHILE ERASING FILE CONTENT *-* \n");
                    }
    } 

    private static void updateLogs(JTextArea outputArea){
        String input = outputArea.getText();

        for (String error : ALL_ERRORS) {
            input = input + error;
        }

        outputArea.setText(input);
    }

    public Inter_graphique() {
        setTitle("Mini-Py-Compiler");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        
        saveButton = new JButton("Save");
        compileButton = new JButton("Compile");
        eraseButton = new JButton("Erase All");
        codeArea = new JTextArea();
        outputArea = new JTextArea(10,100);
        outputArea.setEditable(false);

        //Buttons postion at NORTH
        buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttonsPanel.add(saveButton);
        buttonsPanel.add(compileButton);
        buttonsPanel.add(eraseButton);
        add(buttonsPanel, BorderLayout.NORTH);
        
        //code area at CENTER 
        codePanel = new JPanel(new BorderLayout());
        codePanel.setBorder(BorderFactory.createTitledBorder("Code Editor"));
        JScrollPane codeScrollPane = new JScrollPane(codeArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        codePanel.add(codeScrollPane,BorderLayout.CENTER);
        add(codePanel, BorderLayout.CENTER);

        //logs area at SOUTH
        logsPanel = new JPanel();
        logsPanel.setLayout(new BorderLayout());
        logsPanel.setBorder(BorderFactory.createTitledBorder("Logs"));
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane logsScrollPane = new JScrollPane(outputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        logsPanel.add(logsScrollPane, BorderLayout.CENTER);
        add(logsPanel, BorderLayout.SOUTH);

        
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0){
                savingInFile(codeArea, outputArea);
                JOptionPane.showMessageDialog(null, "SAVED SUCCESSFULLY");
            }
        });
        
        //Compile Button action
        compileButton.addActionListener(e -> {
           savingInFile(codeArea, outputArea);
           ALL_ERRORS.clear();
           count_error = 0;
           Error_handler.ResetALLERRORS();
           Error_handler.ResetCountError();
           
           try {
            Lexical_analyzer.Analyzer();
        } catch (FileNotFoundException e1) {
            outputArea.setText("FILE NOT FOUND\n");
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            outputArea.setText("PROBLEM WHILE READING FILE\n");
            e1.printStackTrace();
        }
           count_error = Error_handler.getErrorCount();
            
           if (count_error > 0) {
            ALL_ERRORS = Error_handler.getErrors();
            outputArea.setText("");
            updateLogs(outputArea);
           }
           else{
            outputArea.setText("COMPILED SUCCESSFULLY\n--- NO ERRORS FOUND ---\n");
           }
        });


        //Erase All Button action
        eraseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int message  = JOptionPane.showConfirmDialog(null, "Are you sure ?");

                if (message == JOptionPane.YES_OPTION) {
                    emptyFile(codeArea, outputArea);
                    ALL_ERRORS.clear();
                    count_error = 0;
                    Error_handler.ResetALLERRORS();
                    Error_handler.ResetCountError();
                    JOptionPane.showMessageDialog(null, "ERASED SUCCESSFULLY");
                }
                
            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Inter_graphique gui = new Inter_graphique();
            gui.setVisible(true);
        });
    }

}

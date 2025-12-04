package com.mini_compiler;

import java.awt.*;
import javax.swing.*;

public class Inter_graphique extends JFrame {
    JTextArea codeArea;
    JButton compileButton,effacerButton;
    JTextArea outputArea;
    JPanel codePanel,logsPanel,buttonsPanel;

    public Inter_graphique() {
        setTitle("Mini-Py-Compiler");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        codeArea = new JTextArea();
        compileButton = new JButton("Compile");
        effacerButton = new JButton("Erase all");
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        //Buttons postion at NORTH
        buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttonsPanel.add(compileButton);
        buttonsPanel.add(effacerButton);
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

        // Button action
        compileButton.addActionListener(e -> {
            String code = codeArea.getText();
            // Here you would call your compiler logic
            outputArea.setText("Compilation output will be shown here.");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Inter_graphique gui = new Inter_graphique();
            gui.setVisible(true);
        });
    }

}

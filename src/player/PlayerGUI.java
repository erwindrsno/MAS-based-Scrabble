/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package player;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class PlayerGUI extends JFrame{
    private JPanel playerTilesPanel;
    private JButton submitButton;
    private JButton drawButton;
    private JTextField wordInputField;
    
    public PlayerGUI(){
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerTilesPanel = new JPanel();
        playerTilesPanel.setLayout(new FlowLayout());
        playerPanel.add(playerTilesPanel, BorderLayout.NORTH);

        wordInputField = new JTextField(20);
        playerPanel.add(wordInputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        submitButton = new JButton("Submit Word");
        drawButton = new JButton("Draw Tiles");
        buttonPanel.add(submitButton);
        buttonPanel.add(drawButton);
        playerPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(playerPanel, BorderLayout.SOUTH);
        
//        submitButton.addActionListener(e -> submitWord());

        setVisible(true);
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(PlayerGUI::new);
    }
}

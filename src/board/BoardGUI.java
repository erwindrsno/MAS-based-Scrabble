/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package board;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


/**
 *
 * @author erwin
 */
public class BoardGUI extends JFrame {
    private JPanel boardPanel;
    private JLabel statusLabel;
    
    public BoardGUI(){
        setTitle("Scrabble Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Status Panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Welcome to Scrabble! Waiting for players...");
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.NORTH);
        
        // Board Panel
        boardPanel = new JPanel(new GridLayout(15, 15));
        for (int i = 0; i < 15 * 15; i++) {
            JButton cell = new JButton();
            cell.setPreferredSize(new Dimension(40, 40));
            cell.setBackground(Color.LIGHT_GRAY);
            boardPanel.add(cell);
        }
        add(boardPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(BoardGUI::new);
    }
}

package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ScrabbleGUI extends JFrame {
    private JPanel boardPanel;
    private JPanel playerTilesPanel;
    private JLabel statusLabel;
    private JButton submitButton;
    private JButton drawButton;
    private JTextField wordInputField;

    public ScrabbleGUI() {
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

        // Player Panel
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

        // Button Actions
        submitButton.addActionListener(e -> submitWord());
        drawButton.addActionListener(e -> drawTiles());

        setVisible(true);
    }

    // Mock method to display tiles (replace with actual integration)
    public void updatePlayerTiles(List<String> tiles) {
        playerTilesPanel.removeAll();
        for (String tile : tiles) {
            JButton tileButton = new JButton(tile);
            playerTilesPanel.add(tileButton);
        }
        playerTilesPanel.revalidate();
        playerTilesPanel.repaint();
    }

    // Mock method to simulate submitting a word
    private void submitWord() {
        String word = wordInputField.getText();
        if (word.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a word!");
            return;
        }
        JOptionPane.showMessageDialog(this, "You submitted the word: " + word);
        wordInputField.setText("");
    }

    // Mock method to simulate drawing tiles
    private void drawTiles() {
        JOptionPane.showMessageDialog(this, "You drew new tiles!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScrabbleGUI::new);
    }
}

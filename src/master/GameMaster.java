/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package master;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

import java.util.ArrayList;
import java.util.List;

import dictionary.DictionaryService;

public class GameMaster extends Agent{
    private List<AID> players = new ArrayList<>();
    private final int REQUIRED_PLAYERS = 2; // Number of players to wait for
    
    private int currentPlayerIndex = 0;
    
    private DictionaryService ds = new DictionaryService();
    
    public void setup(){
        System.out.println("Game master has up");
        
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                System.out.println("Waiting for players");
                ACLMessage msg = receive(); // Receive a message
                if (msg != null) {
                    if (msg.getPerformative() == ACLMessage.INFORM && msg.getContent().equalsIgnoreCase("join")) {
                        AID sender = msg.getSender();

                        // Add the player to the set if not already present
                        if (players.add(sender)) {
                            System.out.println(sender.getLocalName() + " has joined the game. (" + players.size() + "/" + REQUIRED_PLAYERS + ")");
                        } else {
                            System.out.println(sender.getLocalName() + " has joined.");
                        }

                        // Check if we have enough players to start the game
                        if (players.size() == REQUIRED_PLAYERS) {
                            System.out.println("All players have joined. Starting the game!");
                            startTurn();
                            myAgent.removeBehaviour(this); // Stop the CyclicBehaviour
                        }
                    }
                } else {
                    block(); // Block the behaviour until a message arrives
                }
            }
        });
    }
    
        // Function to start the next player's turn
    private void startTurn() {
        // Get the current player
        AID currentPlayer = players.get(currentPlayerIndex);
        System.out.println("It's " + currentPlayer.getLocalName() + "'s turn!");

        // Send a message to the current player to start their turn
        ACLMessage turnMessage = new ACLMessage(ACLMessage.REQUEST);
        turnMessage.addReceiver(currentPlayer);
        turnMessage.setContent("TURN");
        send(turnMessage);
        
        addBehaviour(new CyclicBehaviour() {
            public void action() {
            ACLMessage msg = receive(); // Wait for the player to respond
            if (msg != null && (msg.getPerformative() == ACLMessage.INFORM) && msg.getOntology().equals("WORD")) {
                boolean isValid = ds.checkValid(msg.getContent());
                if(isValid){
                    System.out.println("That word is valid!");
                    
                    //ask board to update board
                } else{
                    System.out.println("Nope, next");
                }
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                startTurn();  // Start the next player's turn
            } else {
                block();  // Block until a message arrives
            }
        }
        });
    }
}

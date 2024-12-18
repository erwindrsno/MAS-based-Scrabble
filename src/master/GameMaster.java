/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package master;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

import java.util.ArrayList;
import java.util.List;

import dictionary.DictionaryService;

public class GameMaster extends Agent{
    private List<AID> players = new ArrayList<>();
    private final int REQUIRED_PLAYERS = 2; // Number of players to wait for
    
    private int currentPlayerIndex = 0;
    private int firstTurnIndex = 0;
    
    private DictionaryService ds = new DictionaryService();
    
    class WaitingPlayer extends CyclicBehaviour{
        public void action(){
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
                        drawTiles();
                        
                        myAgent.removeBehaviour(this); // Stop the CyclicBehaviour
                    }
                }
            } else {
                block(); // Block the behaviour until a message arrives
            }
        }
    }
    
    public void drawTiles(){
        for (AID player : players) {
            // Send a message to the current player to start their draw
            ACLMessage drawMessage = new ACLMessage(ACLMessage.REQUEST);
            drawMessage.addReceiver(player);
            drawMessage.setOntology("DRAW");
            drawMessage.setContent("FIRST");
            send(drawMessage);
        }
        
        startTurnCycle();
        
    }
    
    public void startTurnCycle(){
        // Get the current player
        AID currentPlayer = players.get(currentPlayerIndex);
        System.out.println("It's " + currentPlayer.getLocalName() + "'s turn!");

        ACLMessage showBoardMsg = new ACLMessage(ACLMessage.REQUEST);
        showBoardMsg.addReceiver(new AID("Board", AID.ISLOCALNAME));
        showBoardMsg.setOntology("SHOW");
        send(showBoardMsg);
        
        // Wait for a response from the Board agent
        ACLMessage boardResponse = blockingReceive(); // Blocks until a message is received
        
        // Send a message to the current player to start their turn
        ACLMessage turnMessage = new ACLMessage(ACLMessage.REQUEST);
        turnMessage.addReceiver(currentPlayer);
        turnMessage.setContent("TURN");
        send(turnMessage);
        
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive(); // Wait for the player to respond
                if(msg != null && (msg.getPerformative() == ACLMessage.REQUEST) && msg.getOntology().equals("TURN")){
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    startTurnCycle();  // Start the next player's turn
                }
                else{
                    block();
                }
            }
        });
    }
    
    public void setup(){
        System.out.println("Game master has up");
        
        addBehaviour(new WaitingPlayer());
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package board;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.List;

import tile.Tile;

public class Board extends Agent{
    Tile[][] board = new Tile[15][15];

    public void initBoard(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = null;
            }
        }
    }

    public Tile[][] getBoard(){
        return board;
    }

    public boolean placeTile(Tile tile, int i, int j){
        if(board[i][j] == null){
            board[i][j] = tile;
            return true;
        }
        return false;
    }

    public Tile getPlacedTile(int i, int j){
        return board[i][j];
    }
    
    public void showBoard(){
        System.out.println("CURRENT STATE OF BOARD");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] == null){
                    System.out.print('*' + " ");
                } else{
                    System.out.println(board[i][j] + " ");
                }
            }
            System.out.println("");
        }
    }
    
    protected void setup(){
        System.out.println("Board agent is up");
        
        initBoard();
        
        addBehaviour(new CyclicBehaviour() {
            public void action(){
                ACLMessage msg = receive();
                if(msg != null){
                    if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals("WORD")){
                        System.out.println("Board - To validate : " + msg.getContent());
                        ACLMessage valMsg = new ACLMessage(ACLMessage.REQUEST);
                        valMsg.addReceiver(new AID("DS", AID.ISLOCALNAME));
                        valMsg.setOntology("VALIDATE");
                        valMsg.setContent(msg.getContent());
                        send(valMsg);
                    }
                    else if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals("VALIDATION")){
                        boolean isValid = Boolean.parseBoolean(msg.getContent());
                        if(isValid){
                            System.out.println("IS VALID!");
                        } else{
                            System.out.println("No it is not");
                        }
                        
                        //set score
                        
                        
                        ACLMessage turnMsg = new ACLMessage(ACLMessage.REQUEST);
                        turnMsg.addReceiver(new AID("GM", AID.ISLOCALNAME));
                        turnMsg.setOntology("TURN");
                        send(turnMsg);
                    } else if(msg.getPerformative() == ACLMessage.REQUEST && msg.getOntology().equals("SHOW")){
                        showBoard();
                        
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("OK");
                        send(reply);
                    }
                }
            }
        });
    }
}
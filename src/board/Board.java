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
import model.TileWrapper;

import tile.Tile;

public class Board extends Agent{
    Tile[][] board = new Tile[15][15];
    String tempScore;

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
                    System.out.print(board[i][j].getLetter() + " ");
                }
            }
            System.out.println("");
        }
    }
    
    protected void setup(){
        System.out.println("Board agent is up");
        
        initBoard();
        
        addBehaviour(new CyclicBehaviour() {
            TileWrapper wrapper = null;
            
            public void action(){
                ACLMessage msg = receive();
                if(msg != null){
                    if(msg.getPerformative() == ACLMessage.INFORM){
                        if(msg.getOntology().equals("VALIDATION")){
                            boolean isValid = Boolean.parseBoolean(msg.getContent());
                            if(isValid){
                                System.out.println("Status: VALID");
                                ACLMessage scoreMsg = new ACLMessage(ACLMessage.REQUEST);
                                scoreMsg.addReceiver(new AID("GM", AID.ISLOCALNAME));
                                scoreMsg.setOntology("SCORE");
                                scoreMsg.setContent(this.wrapper.getPoints()+"");
                                send(scoreMsg);
                                
                                if(this.wrapper.getPos() == 0){
                                    int wordIdx = 0;
                                    int j = this.wrapper.getCol();
                                    while(wordIdx < this.wrapper.getTilesSize()){
                                        if(getPlacedTile(this.wrapper.getRow(), j) != null){
                                            continue;
                                        }
                                        placeTile(this.wrapper.getTile(wordIdx),this.wrapper.getRow(), j);
                                        j++;
                                        wordIdx++;
                                    }
                                } else if(this.wrapper.getPos() == 1){
                                    int wordIdx = 0;
                                    int i = this.wrapper.getRow();
                                    while(wordIdx < this.wrapper.getTilesSize()){
                                        if(getPlacedTile(i, this.wrapper.getCol()) != null){
                                            continue;
                                        }
                                        placeTile(this.wrapper.getTile(wordIdx), i,this.wrapper.getCol());
                                        i++;
                                        wordIdx++;
                                    }
                                }
                                showBoard();
                                
                                ACLMessage turnMsg = new ACLMessage(ACLMessage.REQUEST);
                                turnMsg.addReceiver(new AID("GM", AID.ISLOCALNAME));
                                turnMsg.setOntology("TURN");
                                send(turnMsg);
                            } else{
                                //apabila kata yang disusun tidak valid, maka kartu akan hilang dan skor tidak tambah
//                                word = "";
                                System.out.println("STATUS: NOT VALID");
                            }      
                        }
                    }
                    else if(msg.getPerformative() == ACLMessage.REQUEST){
                        if(msg.getOntology().equals("SHOW")){
                            showBoard();
                        
                            ACLMessage reply = msg.createReply();
                            reply.setPerformative(ACLMessage.INFORM);
                            reply.setContent("OK");
                            send(reply);
                        }
                        else if(msg.getOntology().equals("WRAPPER")){
                            try{
                                this.wrapper = (TileWrapper) msg.getContentObject();
                                String word = "";
                                int points = 0;
                                List<Tile> tileList = this.wrapper.getTiles();
                                for(Tile tile : tileList){
                                    word += tile.getLetter();
                                    points += tile.getPoints();
                                }
                                
                                ACLMessage valMsg = new ACLMessage(ACLMessage.REQUEST);
                                valMsg.addReceiver(new AID("DS", AID.ISLOCALNAME));
                                valMsg.setOntology("VALIDATE");
                                valMsg.setContent(word);
                                send(valMsg);
                                
                            } catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    block();
                }
            }
        });
    }
}
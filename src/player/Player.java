package player;

import jade.core.AID;
import java.util.ArrayList;
import java.util.List;

import tile.Tile;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Scanner;

public class Player extends Agent {
    int id;
    int score;
    List<Tile> tiles = new ArrayList<Tile>();
    Scanner sc = new Scanner (System.in);

//    public Player(int id){
//        this.id = id;
//    }

    public void drawTile(Tile tile){
        this.tiles.add(tile);
    }

    public Tile putTile(int idx){
        if(this.tiles.isEmpty()) return null;
        
        return this.tiles.remove(idx);
    }
    
    public void drawTiles(List<Tile> tiles){
        this.tiles = tiles;
    }

    public int getId(){
        return this.id;
    }

    public int getScore(){
        return this.score;
    }

    public List<Tile> getTiles(){
        return this.tiles;
    }
    
    public Tile getTile(int idx){
        return this.tiles.get(idx);
    }
    
    public int getIdx(char letter){
        for (int i = 0; i < this.tiles.size(); i++) {
            if(letter == this.tiles.get(i).getLetter()){
                return i;
            }
        }
        return -1;
    }
    
    public void printFormattedTiles(){
        System.out.println("You have : ");
        for(Tile tile: this.tiles){
            System.out.print(tile.getLetter()+"-"+tile.getPoints()+" ");
        }
    }
    
    public void setup(){
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            String strArgs = (String) args[0];
            this.id = Integer.parseInt(strArgs);
        }
        else {
            doDelete();
        }  
        ACLMessage joinMsg = new ACLMessage(ACLMessage.INFORM);
        joinMsg.addReceiver(new AID("GM", AID.ISLOCALNAME));
        joinMsg.setContent("join");
        send(joinMsg);
        
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                // Wait for the message from the BoardAgent
                ACLMessage msg = receive();
                if (msg != null && msg.getPerformative() == ACLMessage.REQUEST) {
                    if (msg.getContent().equals("TURN")) {
                        printFormattedTiles();
                        
                        String sequence = sc.next();   
                        
                        String word = "";
                        
                        //untuk store kata ke variabel word
                        for(int i = 0; i < sequence.length(); i++){
                            char charIdx = sequence.charAt(i);
                            int idx = charIdx - '0';
                            word += getTile(idx).getLetter();
                        }
                        
                        //untuk buang tile yang sudah dipakai untuk menyusun kata
                        System.out.println("The word is : " + word);
                        
                        for (int i = 0; i < word.length(); i++) {
                            int idx = getIdx(word.charAt(i));
                            putTile(idx);
                        }
                        
                        ACLMessage wordMessage = new ACLMessage(ACLMessage.INFORM);
                        wordMessage.addReceiver(new AID("Board", AID.ISLOCALNAME));
                        wordMessage.setOntology("WORD");
                        wordMessage.setContent(word);
                        send(wordMessage);
                    }
                    else if(msg.getContent().equals("FIRST") && msg.getOntology().equals("DRAW")){
                        ACLMessage drawReqMsg = new ACLMessage(ACLMessage.REQUEST);
                        drawReqMsg.addReceiver(new AID("TB", AID.ISLOCALNAME)); // Send to the TileBag agent
                        drawReqMsg.setOntology("DRAW"); // Request 7 tiles
                        drawReqMsg.setContent("7");
                        send(drawReqMsg);
                    }
//                    else if(msg.getOntology().equals("DRAW")){
//                        ACLMessage drawReqMsg = new ACLMessage(ACLMessage.REQUEST);
//                        drawReqMsg.addReceiver(new AID("TB", AID.ISLOCALNAME)); // Send to the TileBag agent
//                        drawReqMsg.setOntology("DRAW"); // Request 7 tiles
//                        drawReqMsg.setContent("7");
//                        send(drawReqMsg);
//                    }
                }
                else if(msg != null && msg.getPerformative() == ACLMessage.INFORM) {
                    if (msg.getOntology().equals("TILE")){
                        drawTile(new Tile(msg.getContent().charAt(0), Integer.parseInt(msg.getContent().substring(1))));
                    }
                }
                else {
                    block();  // Block until a message arrives
                }
            }
        });
    } 
}
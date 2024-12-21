package player;

import jade.core.AID;
import java.util.ArrayList;
import java.util.List;

import tile.Tile;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Scanner;
import model.TileWrapper;

public class Player extends Agent {
    int id;
    int score;
    int tempScore;
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
    
    public void addScore(int score){
        this.score += score;
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
        
        for (int i = 0; i < this.tiles.size(); i++) {
            Tile tile = this.tiles.get(i);
            System.out.print(i+1+")" + tile.getLetter()+"-"+tile.getPoints()+" ");
        }
    }
    
    public void setup(){
//        Object[] args = getArguments();
//        if (args != null && args.length > 0) {
//            String strArgs = (String) args[0];
//            this.id = Integer.parseInt(strArgs);
//        }
//        else {
//            doDelete();
//        }  
        ACLMessage joinMsg = new ACLMessage(ACLMessage.INFORM);
        joinMsg.addReceiver(new AID("GM", AID.ISLOCALNAME));
        joinMsg.setContent("join");
        send(joinMsg);
        
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                // Wait for the message from the BoardAgent
                ACLMessage msg = receive();
                if(msg != null){
                    if(msg.getPerformative() == ACLMessage.REQUEST){
                        if (msg.getContent().equals("TURN")) {
                            printFormattedTiles();

                            String sequence = sc.next();  
                            System.out.println("State the row and col:");
                            System.out.print("Row : ");
                            int row = sc.nextInt();
                            System.out.print("Col : ");
                            int col = sc.nextInt();
                            System.out.print("HOR/VER(0/1)");
                            int pos = sc.nextInt();

                            String word = "";

                            //untuk store kata ke variabel word dan akumulasi score untuk kata yang disusun
                            for(int i = 0; i < sequence.length(); i++){
                                char charIdx = sequence.charAt(i);
                                int idx = (charIdx - '0')-1;
                                word += getTile(idx).getLetter();
                                tempScore += getTile(idx).getPoints();
                            }

                            System.out.println("The word is : " + word + " and the score is : " + tempScore);
                            
                            TileWrapper wrapper = new TileWrapper(row-1,col-1,pos);

                            //untuk buang tile yang sudah dipakai untuk menyusun kata
                            for (int i = 0; i < word.length(); i++) {
                                int idx = getIdx(word.charAt(i));
                                wrapper.addTileToWrapper(putTile(idx));
                            }
                            
                            try{
                                ACLMessage wrapperMessage =  new ACLMessage(ACLMessage.REQUEST);
                                wrapperMessage.addReceiver(new AID("BOARD", AID.ISLOCALNAME));
                                wrapperMessage.setOntology("WRAPPER");
                                wrapperMessage.setContentObject(wrapper);
                                send(wrapperMessage);
                            } catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                        }
                        else if(msg.getContent().equals("FIRST") && msg.getOntology().equals("DRAW")){
                            ACLMessage drawReqMsg = new ACLMessage(ACLMessage.REQUEST);
                            drawReqMsg.addReceiver(new AID("TB", AID.ISLOCALNAME)); // Send to the TileBag agent
                            drawReqMsg.setOntology("DRAW"); // Request 7 tiles
                            drawReqMsg.setContent("7");
                            send(drawReqMsg);
                        }
                        else if(msg.getOntology().equals("SCORE")){
                            int score = Integer.parseInt(msg.getContent());
                            addScore(score);
//                            System.out.println("My score currently is : " + getScore());
                        }
                    }
                    else if(msg.getPerformative() == ACLMessage.INFORM){
                        if (msg.getOntology().equals("TILE")){
                            drawTile(new Tile(msg.getContent().charAt(0), Integer.parseInt(msg.getContent().substring(1))));
                        }
                    }
                }
                else {
                    block();  // Block until a message arrives
                }
            }
        });
    } 
}
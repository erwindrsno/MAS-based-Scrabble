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

    public Tile setTile(int idx){
        if(this.tiles.isEmpty()) return null;
        
        return this.tiles.remove(idx);
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
        joinMsg.addReceiver(new AID("GameMaster", AID.ISLOCALNAME));
        joinMsg.setContent("join");
        send(joinMsg);
        
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                // Wait for the message from the BoardAgent
                ACLMessage msg = receive();
                if (msg != null && msg.getPerformative() == ACLMessage.REQUEST) {
                    if (msg.getContent().equals("TURN")) {
                        String word = sc.next();
                        
                        ACLMessage moveMessage = new ACLMessage(ACLMessage.INFORM);
                        moveMessage.addReceiver(msg.getSender());
                        moveMessage.setOntology("WORD");
                        moveMessage.setContent(word);
                        send(moveMessage);
                    }
                } else {
                    block();  // Block until a message arrives
                }
            }
        });
    } 
}
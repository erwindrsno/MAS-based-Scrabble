/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tilebag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

import tile.Tile;

public class Tilebag extends Agent {
    private final List<Tile> tiles = new ArrayList<>();
    private final Random random = new Random();
    
    private void initializeBag(){
        addTiles('A', 1, 9);  // Letter A, 1 point, 9 tiles
        addTiles('B', 3, 2);
        addTiles('C', 3, 2);
        addTiles('D', 2, 4);
        addTiles('E', 1, 12);
        addTiles('F', 4, 2);
        addTiles('G', 2, 3);
        addTiles('H', 4, 2);
        addTiles('I', 1, 9);
        addTiles('J', 8, 1);
        addTiles('K', 5, 1);
        addTiles('L', 1, 4);
        addTiles('M', 3, 2);
        addTiles('N', 1, 6);
        addTiles('O', 1, 8);
        addTiles('P', 3, 2);
        addTiles('Q', 10, 1);
        addTiles('R', 1, 6);
        addTiles('S', 1, 4);
        addTiles('T', 1, 6);
        addTiles('U', 1, 4);
        addTiles('V', 4, 2);
        addTiles('W', 4, 2);
        addTiles('X', 8, 1);
        addTiles('Y', 4, 2);
        addTiles('Z', 10, 1);
        addTiles(' ', 0, 2);
        // Collections.shuffle(tiles, random);
    }
    
    private void addTiles(char letter, int points, int count){
        for (int i = 0; i < count; i++) {
            tiles.add(new Tile(letter, points));
        }
    }

    public Tile getTile() {
        if (tiles.isEmpty()) {
            throw new IllegalStateException("The tile bag is empty!");
        }
        int randNum = random.nextInt(tiles.size());
        return tiles.remove(randNum);
    }
    
    public List<Tile> getTiles(int num) {
        List<Tile> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(getTile());
        }
        return list;
    }
    
    public boolean isEmpty(){
        return tiles.isEmpty();
    }
    
    public int getTilebagSize(){
        return tiles.size();
    }
    
    public void setup(){
        System.out.println("Tile bag agent is up");
        initializeBag();
        
        addBehaviour(new CyclicBehaviour() {
            public void action(){
                ACLMessage msg = receive();
                if(msg != null){
                    if(msg.getPerformative() == ACLMessage.REQUEST){
                        if(msg.getOntology().equals("DRAW")){
                            int numTiles = Integer.parseInt(msg.getContent());
                            List<Tile> drawnTiles = getTiles(numTiles);

                            //respond to the player with the tiles
                            for (Tile tile: drawnTiles){
                                ACLMessage reply = msg.createReply();
                                reply.setPerformative(ACLMessage.INFORM);
                                reply.setOntology("TILE");
                                reply.setContent(tile.getLetter()+""+tile.getPoints());
                                send(reply);
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
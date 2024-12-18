/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tilebag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tile.Tile;

public class Tilebag {
    private final List<Tile> tiles;
    private final Random random;
    
    public Tilebag(){
        tiles = new ArrayList<>();
        random = new Random();
        initializeBag();
    }
    
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
    
    public boolean isEmpty(){
        return tiles.isEmpty();
    }
    
    public int getTilebagSize(){
        return tiles.size();
    }
}
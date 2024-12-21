/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import tile.Tile;

public class TileWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    List<Tile> tiles;
    int row;
    int col;
    int pos;
    
    public TileWrapper(int row, int col, int pos){
        this.tiles = new ArrayList<>();
        this.row = row;
        this.col = col;
        this.pos = pos;
    }
    
    public void addTileToWrapper(Tile tile){
        this.tiles.add(tile);
    }
    
    public List<Tile> getTiles(){
        return this.tiles;
    }
    
    public Tile getTile(int idx){
        return this.tiles.get(idx);
    }
    
    public int getTilesSize(){
        return this.tiles.size();
    }
    
    public int getRow(){
        return this.row;
    }
    
    public int getCol(){
        return this.col;
    }
    
    public int getPos(){
        return this.pos;
    }
    
    public int getPoints(){
        int points = 0 ;
        for(Tile tile : this.tiles){
            points += tile.getPoints();
        }
        return points;
    }
}

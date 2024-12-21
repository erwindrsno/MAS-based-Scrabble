package tile;

import java.io.Serializable;

public class Tile implements Serializable{
    private static final long serialVersionUID = 1L;
    private char letter;
    private int points;
    
    public Tile(char letter, int points){
        this.letter = letter;
        this.points = points;
    }
    
    public Tile(char letter){
        this.letter = letter;
    }
    
    public Tile(){}
    
    public char getLetter(){
        return this.letter;
    }
    
    public int getPoints(){
        return this.points;
    }
}
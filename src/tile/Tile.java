package tile;

public class Tile {
    private char letter;
    private int points;
    
    public Tile(char letter, int points){
        this.letter = letter;
        this.points = points;
    }
    
    public char getLetter(){
        return this.letter;
    }
    
    public int getPoints(){
        return this.points;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package board;

import jade.core.Agent;

import tile.Tile;

public class Board extends Agent{
    Tile[][] board = new Tile[15][15];

    public Board(){
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
        
//        showBoard();
    }
}
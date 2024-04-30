package Game;
import java.util.Arrays;

import Game.Block.Block;

public class Board {

    private int[][] gameBoard = new int[9][9];

    public Board(){
        this.clearBoard();
    }

    public int[][] getBoard(){ return gameBoard;}

    public void placeBlock(int x, int y ,Block block){
        int[][] blockArray = block.getBlockArray();
        for(int i = 0 ; i<blockArray.length ; i++){
            for(int j = 0 ; j<blockArray[0].length ; j++){
                if(blockArray[i][j] != 0)
                    gameBoard[y+i][x+j] = blockArray[i][j];
            }
        }
    }

    public void removeBlock(int x, int y, Block block){
        int[][] blockArray = block.getBlockArray();
        for(int i = 0 ; i<blockArray.length ; i++){
            for(int j = 0 ; j<blockArray[0].length ; j++){
                if(blockArray[i][j] != 0)
                    gameBoard[y+i][x+j] = 0;
            }
        }
    }

    public boolean fits(int x, int y, Block block){
        int[][] blockArray = block.getBlockArray();
        //System.out.println("Block width: " + block.getWidth());
        //System.out.println("Block height: " + block.getHeight());
        if(x<0 || x>8 || y<0 || y>8 || block.getWidth()+x > 9 || block.getHeight()+y > 9)
            return false;
        for(int i = 0 ; i < block.getHeight() ; i++){
            for(int j = 0 ; j < block.getWidth() ; j++){
                if(blockArray[i][j] != 0 && gameBoard[y+i][x+j] != 0)
                    return false;
            }
        }

        return true;
    }

    public void clearBoard(){
        for(int x = 0 ; x < 9 ; x++){
            for(int y = 0 ; y < 9 ; y++){
                gameBoard[x][y] = 0;
            }
        }
    }

    public void print(){
        System.out.println("-- Board Visualization --");
        System.out.println("x--------------------x");
        for(int x = 0 ; x < 9 ; x++){
            System.out.print("| ");
            for(int y = 0 ; y < 9 ; y++){
                if(gameBoard[x][y] != 0)
                    System.out.print("■ ");
                else
                    System.out.print("  ");
            }
            System.out.print(" |");
            System.out.println();
        }
        System.out.println("x--------------------x");
    }

     // Method to clone the Board's current state
     public Board clone() {
        Board newBoard = new Board();
        // You'll need to implement a deeper copy if your board contains more complex objects
        for (int i = 0; i < this.gameBoard.length; i++) {
            newBoard.gameBoard[i] = this.gameBoard[i].clone();
        }
        return newBoard;
    }

}

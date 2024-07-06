package Game;

import java.util.ArrayList;
import java.util.List;

public class TestBoardEvaluator extends BoardEvaluator{


    @Override
    public double getScore() {
        return super.chromosome.a * getBubbles() + //Amount of closed pockets
               super.chromosome.b * getEmptyCells() + //Amount of filled cells on the board
               //super.chromosome.c * getBorderCells() + //amount of cells at the border
               super.chromosome.d * getSmoothness() +
               //super.chromosome.f + 
               super.chromosome.g * getCenterConcentration() +
               //super.chromosome.h * get() +
               //super.chromosome.i * getP();
    }

    private double getSmoothness() {
    boolean[][] visited = new boolean[9][9];
    int[][] board = super.board.getBoard();
    List<Integer> clusterSizes = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j] == 1 && !visited[i][j]) {
                int size = countRegion(board, i, j, visited,false);
                clusterSizes.add(size);
                System.out.println(size);  // Optionally print size for debugging
            }
        }
    }
    return calculateStandardDeviation(clusterSizes) * -1;
}

private double calculateStandardDeviation(List<Integer> sizes) {
    double mean = sizes.stream().mapToDouble(a -> a).average().orElse(0.0);
    double variance = sizes.stream().mapToDouble(a -> (a - mean) * (a - mean)).sum() / sizes.size();
    return Math.sqrt(variance);
}

    private int getBorderCells() {
        int count = 0;
        int[][] board = super.board.getBoard();
        for(int y = 0 ; y<9 ; y++){
            if(y == 0 || y == 8){
                for(int x = 0 ; x < 9 ; x++){
                    if(board[y][x] == 1){
                        count++;
                    }
                }
            }else{
                if(board[y][0] == 1){count++;}
                if(board[y][8] == 1){count++;}
            }
        }
        return count;
    }

    //return sum of occupied cells 
    private int getEmptyCells(){
        int count = 0;
        int[][] board = super.board.getBoard();
        for(int y = 0 ; y<9 ; y++){
            for(int x = 0 ; x< 9 ; x++){
                if(board[y][x] == 0){ count++;}
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Board a = new Board();
        //a.getBoard()[0][1] = 1;
        a.getBoard()[0][0] = 1;
        //a.getBoard()[1][1] = 1;
        a.getBoard()[2][0] = 1;
        a.getBoard()[0][3] = 1;
        //a.getBoard()[1][2] = 1;
        a.getBoard()[1][0] = 1;
        a.getBoard()[0][2] = 1;
        a.print();
        TestBoardEvaluator testing = new TestBoardEvaluator();
        testing.setBoard(a);
        System.out.println(testing.getSmoothness());
    }

    //return sum of bubbles, where area of it is less than 4
    //diagonal cell is not concidered to be adjecent
    private int getBubbles() {
        boolean[][] visited = new boolean[9][9];
        int[][] board = super.board.getBoard();
        int bubblesCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0 && !visited[i][j]) {
                    int size = countRegion(board, i, j, visited,true);
                    if (size < 4) {
                        bubblesCount+=size;
                    }
                }
            }
        }
        return bubblesCount;
    }

    private static int countRegion(int[][] field, int r, int c, boolean[][] visited, boolean emptyMode) {
        if(emptyMode){
            if (r < 0 || r >= 9 || c < 0 || c >= 9 || visited[r][c] || field[r][c] != 0) {
                return 0;
            }
            visited[r][c] = true;
        }else{
            if (r < 0 || r >= 9 || c < 0 || c >= 9 || visited[r][c] || field[r][c] != 1) {
                return 0;
            }
            visited[r][c] = true;
        }
        return 1 +
            countRegion(field, r+1, c, visited,emptyMode) +
            countRegion(field, r-1, c, visited,emptyMode) +
            countRegion(field, r, c+1, visited,emptyMode) +
            countRegion(field, r, c-1, visited,emptyMode);
    }

}

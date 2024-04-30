// Thank you <3
// Not stolen from: https://hashempour.github.io/GaBlockSudoku/
// Formula: aX + bY + cZ + dW + eQ + f + gT + hS + iP  = 0
// X: cols integrity (%) TODO
// Y: rows integrity (%) TODO
// Z: 9-blocks integrity (%) TODO 
// W: total occupation (%) TODO
// Q: selected element occupation (%) 
// T: row/col/blockSet completeness (%) TODO
// S: board total integrity (%) TODO
// P: priority of sides and corner occupation (%) TODO

package Game;

import Game.Block.Chromosome;

public class BoardEvaluator {

    public Board board;
    public Chromosome chromosome;

    public BoardEvaluator() {}

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setChromosome(Chromosome chromosome){
        this.chromosome = chromosome;
    }

    public double getScore() {
        return chromosome.a * getX() +
               chromosome.b * getY() +
               chromosome.c * getZ() +
               chromosome.d * getW() +
               chromosome.f +
               chromosome.g * getT() +
               chromosome.h * getS() +
               chromosome.i * getP();
    }

    //TODO X
    public double getX() {
        double integrityValue = 0;
        int gapSize;
        int[] gapCount = new int[10];

        for (int i = 0; i < board.getBoard().length; i++) {
            gapSize = 0;
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                if (board.getBoard()[j][i] == 1 && gapSize > 0) {
                    gapCount[gapSize]++;
                    gapSize = 0;
                }
                if (board.getBoard()[j][i] == 0) {
                    gapSize++;
                }
            }
            if (gapSize > 0) {
                gapCount[gapSize]++;
            }
        }

        for (int k = 1; k < gapCount.length; k++) {
            integrityValue += (10 - k) * gapCount[k];
        }

        integrityValue /= board.getBoard().length * 45;
        return roundTwoDecimals(integrityValue);
    }

    //TODO Y
    public double getY() {
        double integrityValue = 0;
        int gapSize;
        int[] gapCount = new int[10]; // array to count gaps from 1 to 9
    
        for (int i = 0; i < board.getBoard().length; i++) {
            gapSize = 0;
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j] == 1 && gapSize > 0) {
                    gapCount[gapSize]++;
                    gapSize = 0; // reset gap size
                }
                if (board.getBoard()[i][j] == 0) {
                    gapSize++;
                }
            }
            if (gapSize > 0) {
                gapCount[gapSize]++;
            }
        }
    
        for (int k = 1; k < gapCount.length; k++) {
            integrityValue += (10 - k) * gapCount[k];
        }
    
        integrityValue /= board.getBoard().length * 45; // normalise the value
        return roundTwoDecimals(integrityValue);
    }

    //TODO Z
    public double getZ() {
        double integrityValue = 0;
        int[] gapCount = new int[10]; // array to count gaps from 1 to 9
        int blocksPerRow = board.getBoard().length / 3;
        int blocksPerCol = board.getBoard()[0].length / 3;
    
        for (int blockRow = 0; blockRow < blocksPerRow; blockRow++) {
            for (int blockCol = 0; blockCol < blocksPerCol; blockCol++) {
                int gapSize = 0;
                for (int row = blockRow * 3; row < (blockRow + 1) * 3; row++) {
                    for (int col = blockCol * 3; col < (blockCol + 1) * 3; col++) {
                        if (board.getBoard()[row][col] == 1 && gapSize > 0) {
                            gapCount[gapSize]++;
                            gapSize = 0; // reset gap size
                        }
                        if (board.getBoard()[row][col] == 0) {
                            gapSize++;
                        }
                    }
                }
                if (gapSize > 0) {
                    gapCount[gapSize]++;
                }
            }
        }
    
        for (int k = 1; k < gapCount.length; k++) {
            integrityValue += (10 - k) * gapCount[k];
        }
    
        integrityValue /= 9 * 45; // normalise the value based on 9 3x3 blocks
        return roundTwoDecimals(integrityValue);
    }

    //TODO W
    // Total occupation percentage of the board
    public double getW() {
        int totalFilled = 0;
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j] != 0) {
                    totalFilled++;
                }
            }
        }
        double result = (double) totalFilled / (board.getBoard().length * board.getBoard()[0].length);
        return roundTwoDecimals(result);
    }

    //TODO T
    // Total completion rate of rows, columns, and blocks
    public double getT() {
        int totalComplete = 0;
        totalComplete += countCompleteRows();
        totalComplete += countCompleteColumns();
        totalComplete += countCompleteBlocks();
        double result = (double) totalComplete / (3 * board.getBoard().length);
        return roundTwoDecimals(result);
    }

    private int countCompleteRows() {
        int completeRows = 0;
        for (int i = 0; i < board.getBoard().length; i++) {
            boolean complete = true;
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j] == 0) {
                    complete = false;
                    break;
                }
            }
            if (complete) completeRows++;
        }
        return completeRows;
    }

    private int countCompleteColumns() {
        int completeCols = 0;
        for (int j = 0; j < board.getBoard()[0].length; j++) {
            boolean complete = true;
            for (int i = 0; i < board.getBoard().length; i++) {
                if (board.getBoard()[i][j] == 0) {
                    complete = false;
                    break;
                }
            }
            if (complete) completeCols++;
        }
        return completeCols;
    }

    private int countCompleteBlocks() {
        int blockSize = 3;  // Assuming a standard block size of 3x3 for Sudoku-like games
        int completeBlocks = 0;
        for (int blockRow = 0; blockRow < board.getBoard().length; blockRow += blockSize) {
            for (int blockCol = 0; blockCol < board.getBoard()[0].length; blockCol += blockSize) {
                if (isBlockComplete(blockRow, blockCol, blockSize)) {
                    completeBlocks++;
                }
            }
        }
        return completeBlocks;
    }

    private boolean isBlockComplete(int startRow, int startCol, int blockSize) {
        for (int i = startRow; i < startRow + blockSize; i++) {
            for (int j = startCol; j < startCol + blockSize; j++) {
                if (board.getBoard()[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // TODO S
    // Total board integrity calculation (similar to getS but adapted)
    public double getS() {
        int[] gapCounts = new int[board.getBoard().length * board.getBoard()[0].length + 1];
        boolean[] visited = new boolean[board.getBoard().length * board.getBoard()[0].length];
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (!visited[i * board.getBoard().length + j] && board.getBoard()[i][j] != 0) {
                    int size = fillAndCount(i, j, visited);
                    gapCounts[size]++;
                }
            }
        }
        double divValue = 0;
        for (int size = 1; size < gapCounts.length; size++) {
            divValue += (board.getBoard().length * board.getBoard()[0].length + 1 - size) * gapCounts[size];
        }
        double result = divValue / (board.getBoard().length * board.getBoard()[0].length * 45);
        return roundTwoDecimals(result);
    }
    //TODO P
    public double getP() {
        double result = 0.0;
        int center = board.getBoard().length / 2;  // Assumes square board for simplicity
        
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                int distanceToCenter = Math.abs(i - center) + Math.abs(j - center);
                result += distanceToCenter * board.getBoard()[i][j];
            }
        }
        
        // Normalize the value
        double totalPossibleScore = 0;
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                int distanceToCenter = Math.abs(i - center) + Math.abs(j - center);
                totalPossibleScore += distanceToCenter;
            }
        }
        result /= totalPossibleScore;  // Normalize based on the total possible score
        return roundTwoDecimals(result);
    }

    private int fillAndCount(int row, int col, boolean[] visited) {
        if (row < 0 || col < 0 || row >= board.getBoard().length || col >= board.getBoard()[0].length || visited[row * board.getBoard().length + col] || board.getBoard()[row][col] == 0) {
            return 0;
        }
        visited[row * board.getBoard().length + col] = true;
        return 1 + fillAndCount(row + 1, col, visited) + fillAndCount(row, col + 1, visited) + fillAndCount(row - 1, col, visited) + fillAndCount(row, col - 1, visited);
    }

    private double roundTwoDecimals(double value) {
        return (double) Math.round(value * 100) / 100;
    }

    // Additional methods for calculating other features...
}

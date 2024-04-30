package Game;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import Game.Block.Block;
import Game.Block.BlockFactory;
import Game.Block.Chromosome;
import Game.Move.OnePlacementInstruction;
import Game.Move.SetOfMoves;

public class Game {
    private Board gameBoard;
    private UI window;
    private double fitnessScore;
    private List<SetOfMoves> allPossibleMoves;
    private static Board initialBoard;
    private static Board firstCheckpoint;
    private static Board secondCheckpoint;
    private BoardEvaluator boardEvaluator = new BoardEvaluator();

    //TODO WARZONE
    public static int simulateGame(Chromosome chromosome){
        while(true){
            Game game = new Game();
            BlockFactory factory = new BlockFactory();
            Block[] blocks = factory.createBlocks();
            return 0;
        }
    }

    public Game() {
        gameBoard = new Board();
        //window = new UI(gameBoard);
        allPossibleMoves = new ArrayList<>();
    }

    public void setBoard(Board newBoard) {
        this.gameBoard = newBoard;
        initialBoard = gameBoard;
        gameBoard.print();
        //updateUI();
    }

    private void simulateGame() {
        BlockFactory blockFactory = new BlockFactory();
        List<Block> blocks = Arrays.asList(blockFactory.createBlocks());
        simulateMoves(blocks, 0, new ArrayList<>());
        displayBestMoves();
    }

    //Modified simulateMoves method to accumulate moves and evaluate score.
    public void simulateMoves(List<Block> blocks, int index, List<OnePlacementInstruction> placements) {
        // Instead of using static variables for checkpoints, we will clone the board state locally
        Board boardBeforePlacement = gameBoard.clone(); // You need to implement the clone method

        if (index == blocks.size()) {
            double evaluatedScore = evaluateFitnessScore(); // Your method to calculate the score of the board
            SetOfMoves setOfMoves = new SetOfMoves(placements, evaluatedScore);
            allPossibleMoves.add(setOfMoves);
            return;
        }

        Block currentBlock = blocks.get(index);
        for (int y = 0; y < 9 - currentBlock.getHeight() + 1; y++) {
            for (int x = 0; x < 9 - currentBlock.getWidth() + 1; x++) {
                if (gameBoard.fits(x, y, currentBlock)) {
                    // Place the block and evaluate the score after potential destruction
                    placeBlock(x, y, currentBlock, gameBoard);
                    simulateDestruction(gameBoard); // This will modify the gameBoard according to destruction rules

                    // Recurse with the new board state
                    List<OnePlacementInstruction> newPlacements = new ArrayList<>(placements);
                    newPlacements.add(new OnePlacementInstruction(x, y, index, currentBlock));
                    simulateMoves(blocks, index + 1, newPlacements);

                    // Restore the board to its state before the block was placed
                    gameBoard = boardBeforePlacement.clone(); // Restore the snapshot
                }
            }
        }
    }

    //public void simulateMoves(List<Block> blocks, int index, List<OnePlacementInstruction> placements) {
    //    if (index == blocks.size()) {
    //        SetOfMoves setOfMoves = new SetOfMoves(new ArrayList<>(placements), evaluateFitnessScore());
    //        allPossibleMoves.add(setOfMoves);
    //        return;
    //    }
//
    //    Block currentBlock = blocks.get(index);
    //    for (int y = 0; y <= gameBoard.getBoard().length - currentBlock.getHeight(); y++) {
    //        for (int x = 0; x <= gameBoard.getBoard()[0].length - currentBlock.getWidth(); x++) {
    //            if (gameBoard.fits(x, y, currentBlock)) {
    //                Board cloneBoard = gameBoard.clone(); // Clone the board for simulation
    //                cloneBoard.placeBlock(x, y, currentBlock);
    //                simulateDestruction(cloneBoard);
    //                List<OnePlacementInstruction> newPlacements = new ArrayList<>(placements);
    //                newPlacements.add(new OnePlacementInstruction(x, y, index, currentBlock));
    //                simulateMoves(blocks, index + 1, newPlacements);
    //                
    //            }
    //        }
    //    }
    //}

    private double evaluateFitnessScore() {
        boardEvaluator.setBoard(gameBoard);
        boardEvaluator.setChromosome(Chromosome.bestChromosome);
        fitnessScore = boardEvaluator.getScore();
        return fitnessScore;
    }

    private void placeBlock(int x, int y, Block blockToPlace, Board board){
        board.placeBlock(x, y, blockToPlace);
    }

    private void displayBestMoves() {
        Optional<SetOfMoves> bestMoves = allPossibleMoves.stream()
                .max((move1, move2) -> Double.compare(move1.getScore(), move2.getScore()));

        if (bestMoves.isPresent()) {
            SetOfMoves best = bestMoves.get();
            System.out.println("Best score: " + best.getScore());
            System.out.println(best.toString());
            System.out.println("Movement Instructions: ");
            System.out.println("XXXXX");
            best.printInstructions();
        } else {
            System.out.println("No valid moves found.");
        }
    }

    // Helper function to generate permutations and simulate each permutation
    private void permuteAndSimulate(List<Block> blocks, int start) {
        if (start == blocks.size() - 1) {
            simulateMoves(new ArrayList<>(blocks), 0, new ArrayList<>()); // Start with an empty placements list
        } else {
            for (int i = start; i < blocks.size(); i++) {
                Collections.swap(blocks, start, i); // Swap blocks to create a new permutation
                permuteAndSimulate(blocks, start + 1);
                Collections.swap(blocks, start, i); // Swap back for the next permutation
            }
        }
    }

    public void simulateMoves(Block[] blockPool){
        List<Block> blocks = Arrays.asList(blockPool);
        permuteAndSimulate(blocks, 0);
        //System.out.println("Best move:");
        displayBestMoves();
    }

    private Board getBoard(){ return gameBoard;}

    private void updateUI(){
        window.setState(gameBoard);
    }

    public static void main(String[] args) {
        Game game = new Game();
        BlockFactory factory = new BlockFactory();
        Block[] blocks = factory.createBlocks();
    }


    public void sleep(int howLong){
        updateUI();
        try {
            Thread.sleep(howLong);
        } catch (Exception e) {
            System.out.println("Sleep interupted!");
        }
    }

    private void simulateDestruction(Board board) {
        boolean[][] clearBoard = new boolean[9][9]; // Default is false, meaning no cell is cleared initially
    
        // Check for complete rows and columns
        for (int i = 0; i < 9; i++) {
            boolean fullRow = true;
            boolean fullColumn = true;
    
            for (int j = 0; j < 9; j++) {
                if (board.getBoard()[i][j] == 0) {
                    fullRow = false;
                }
                if (board.getBoard()[j][i] == 0) {
                    fullColumn = false;
                }
            }
    
            if (fullRow) {
                Arrays.fill(clearBoard[i], true); // Mark entire row for clearance
            }
            if (fullColumn) {
                for (int j = 0; j < 9; j++) {
                    clearBoard[j][i] = true; // Mark entire column for clearance
                }
            }
        }
    
        // Check for complete 3x3 squares
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                boolean fullSquare = true;
                for (int i = row; i < row + 3; i++) {
                    for (int j = col; j < col + 3; j++) {
                        if (gameBoard.getBoard()[i][j] == 0) {
                            fullSquare = false;
                            break;
                        }
                    }
                    if (!fullSquare) break;
                }
                if (fullSquare) {
                    for (int i = row; i < row + 3; i++) {
                        for (int j = col; j < col + 3; j++) {
                            clearBoard[i][j] = true; // Mark cells in 3x3 square for clearance
                        }
                    }
                }
            }
        }
    
        // Clear marked cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (clearBoard[i][j]) {
                    gameBoard.getBoard()[i][j] = 0; // Clear the cell
                }
            }
        }
        //sleep(1000);
    }
}

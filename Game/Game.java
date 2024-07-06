package Game;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Game.Block.Block;
import Game.Block.BlockFactory;
import Game.Block.Chromosome;
import Game.Move.OnePlacementInstruction;
import Game.Move.SetOfMoves;

public class Game {
    private static boolean printMode = true;
    private Board gameBoard;
    private UI window;
    private double fitnessScore;
    private int simulationGameScore = 0;
    private int currentCombo = 0;
    private List<SetOfMoves> allPossibleMoves;
    private BoardEvaluator boardEvaluator = new BoardEvaluator();
    private Chromosome chromosome;

    //TODO WARZONE
    public static int getGameScore(Chromosome givenChromosome){
        Game game = new Game();
        game.chromosome = givenChromosome;
        game.printMode = false;
        game.simulationGameScore = 0;
        game.currentCombo = 0;
        game.allPossibleMoves.clear();

        do{
            BlockFactory factory = new BlockFactory();
            factory.printMode = false;
            Block[] blocks = factory.createBlocks();
            game.simulateMoves(blocks);
            placeBlocks(game, game.allPossibleMoves);
        }while(game.allPossibleMoves.size() != 0);

        return game.simulationGameScore;
    }

    private static void placeBlocks(Game game, List<SetOfMoves> allPossibleMoves2) {
        Optional<SetOfMoves> bestMoves = allPossibleMoves2.stream()
                .max((move1, move2) -> Double.compare(move1.getScore(), move2.getScore()));

        if (bestMoves.isPresent()) {
            SetOfMoves best = bestMoves.get();

            List<OnePlacementInstruction> moveList = best.getPlacements();
            for (OnePlacementInstruction a : moveList) {
                game.placeBlock(a.x, a.y, a.block, game.getBoard());
                game.simulationGameScore += game.simulateDestruction(game.getBoard() , a.block , a.x , a.y);
            }

        } else {
            return;
        }
    }

    public Game() {
        gameBoard = new Board();
        //window = new UI(gameBoard);
        allPossibleMoves = new ArrayList<>();
    }

    public void setBoard(Board newBoard) {
        this.gameBoard = newBoard;
        if(printMode)
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

                    simulateDestruction(gameBoard,currentBlock,x,y); // This will modify the gameBoard according to destruction rules

                    // Recurse with the new board state
                    List<OnePlacementInstruction> newPlacements = new ArrayList<>(placements);
                    newPlacements.add(new OnePlacementInstruction(x, y, blockIndexMap.get(currentBlock), currentBlock));
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

        if(chromosome==null)
            boardEvaluator.setChromosome(Chromosome.bestChromosome);
        else
            boardEvaluator.setChromosome(chromosome);

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
            //System.out.println("Best score: " + best.getScore());
            //System.out.println(best.toString());
            if(printMode){
                System.out.println("Movement Instructions: ");
                System.out.println(best.toString());
                System.out.println("XXXXX");
                best.printInstructions();
            }
        } else {
            if (printMode) {
                System.out.println("No valid moves found.");   
            }
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

    public static Map<Block, Integer> blockIndexMap; 

    public void simulateMoves(Block[] blockPool){
        allPossibleMoves.clear();
        //temSimScore = 0;
        if(printMode)
            gameBoard.print();

        List<Block> blocks = Arrays.asList(blockPool);

        blockIndexMap = mapBlockToOriginalIndex(blocks);

        permuteAndSimulate(blocks, 0);
        //System.out.println("Best move:");
        displayBestMoves();
    }

    private Map<Block, Integer> mapBlockToOriginalIndex(List<Block> blocks) {
        Map<Block, Integer> blockIndexMap = new HashMap<>();
        for (int i = 0; i < blocks.size(); i++) {
            blockIndexMap.put(blocks.get(i), i);
        }
        //System.out.println(blockIndexMap.toString());
        
        return blockIndexMap;
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

    private int simulateDestruction(Board board, Block placedBlock, int x, int y) {
        boolean[][] clearBoard = new boolean[9][9]; // Default is false, meaning no cell is cleared initially
        boolean clearedThisTurn = false; // To check if any line was cleared in this turn
        int scoreToGain = 0;
    
        // Initially, add points for the cells of the block that was just placed
        for (int i = 0; i < placedBlock.getHeight(); i++) {
            for (int j = 0; j < placedBlock.getWidth(); j++) {
                if (board.getBoard()[y + i][x + j] != 0) { // Ensure the cell is part of the block
                    scoreToGain++; // Add points for each cell of the block that is placed
                }
            }
        }
    
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
                Arrays.fill(clearBoard[i], true);
                scoreToGain += 18; // 9*2 points for each cleared row
                clearedThisTurn = true;
            }
            if (fullColumn) {
                for (int j = 0; j < 9; j++) {
                    clearBoard[j][i] = true;
                }
                scoreToGain += 18; // 9*2 points for each cleared column
                clearedThisTurn = true;
            }
        }
    
        // Check for complete 3x3 squares
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                boolean fullSquare = true;
                for (int i = row; i < row + 3; i++) {
                    for (int j = col; j < col + 3; j++) {
                        if (board.getBoard()[i][j] == 0) {
                            fullSquare = false;
                            break;
                        }
                    }
                    if (!fullSquare) break;
                }
                if (fullSquare) {
                    for (int i = row; i < row + 3; i++) {
                        for (int j = col; j < col + 3; j++) {
                            clearBoard[i][j] = true;
                        }
                    }
                    scoreToGain += 18; // 9*2 points for each cleared 3x3 square
                    clearedThisTurn = true;
                }
            }
        }
    
        // Apply combo score if any row, column, or square was cleared
        if (clearedThisTurn) {
            currentCombo++; // Increase combo count
            scoreToGain += 9 * (currentCombo - 1); // Apply combo score
        } else {
            currentCombo = 0; // Reset combo count if no clearing happened
        }
    
        // Clear marked cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (clearBoard[i][j]) {
                    board.getBoard()[i][j] = 0; // Clear the cell
                }
            }
        }
    
        return scoreToGain;
    }
    
}

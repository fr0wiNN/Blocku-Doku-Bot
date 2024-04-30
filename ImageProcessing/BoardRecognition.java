package ImageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Game.Board; // Import your Board class

public class BoardRecognition {

    // Threshold to decide if a cell is occupied or not
    private static final int OCCUPANCY_THRESHOLD = 128; // This threshold might need to be adjusted

    public static Board getBoardFromImage(File boardFile) {
        Board board = new Board();

        try {
            BufferedImage boardImage = ImageIO.read(boardFile);

            int cellWidth = boardImage.getWidth() / 9;
            int cellHeight = boardImage.getHeight() / 9;

            // Assuming the sampling dot is in the center-top of each cell, adjust as necessary
            int offsetX = cellWidth / 2; 
            int offsetY = cellHeight / 2; // Adjust this if the dot is not exactly 1/10th down from the top

            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    // Calculate the position of the sample point within the cell
                    int sampleX = x * cellWidth + offsetX;
                    int sampleY = y * cellHeight + offsetY;

                    // Get the color of the sample point
                    Color color = new Color(boardImage.getRGB(sampleX, sampleY));

                    // Set a condition for checking occupancy based on the color of your occupied cells
                    if (color.getRed() < OCCUPANCY_THRESHOLD && color.getGreen() < OCCUPANCY_THRESHOLD && color.getBlue() > OCCUPANCY_THRESHOLD) {
                        board.getBoard()[y][x] = 1; // Occupied
                    } else {
                        board.getBoard()[y][x] = 0; // Empty
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return board;
    }
    
}

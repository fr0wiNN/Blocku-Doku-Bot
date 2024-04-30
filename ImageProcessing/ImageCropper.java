package ImageProcessing;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game.Game;
import Game.Block.Block;
import Game.Block.BlockFactory;
import Game.Block.BlocksDatabase;
import java.awt.image.*;

public class ImageCropper {

    public static void main(String[] args) {
        ImageCropper a = new ImageCropper("C:/Users/Max/Desktop/Projects/BlokuDoku/Server/Server_Img/20240422235512.png");
    }

    private static final int x = 30;
    private static final int y = 1720;
    private static final int width = 340*3;
    private static final int height = 340;
    private static final int blockWidth = width/3;

    private static final int boardX = 46;
    private static final int boardY = 596;
    private static final int boardWidth = 1033-boardX;
    private static final int boardHeight = 1583-boardY;


    /**
     * Creates a rectangle ,that contains all 3 blocks while removing board and other
     * unecessary stuff from image
     * @param filePath 
     * @return
     */
    public static BufferedImage refineImage(String filePath) {
        try {
            // Read the original image from the file
            BufferedImage notRefinedImage = ImageIO.read(new File(filePath));
            // Create a new BufferedImage that represents the cropped area
            BufferedImage croppedImage = notRefinedImage.getSubimage(x, y, width, height);

            BufferedImage boardImage = notRefinedImage.getSubimage(boardX, boardY, boardWidth, boardHeight);
            saveBoard(boardImage);

            return croppedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // or handle more gracefully
        }
    }
    /**
     * Creates a rectangle ,that contains all 3 blocks while removing board and other
     * unecessary stuff from image
     * @param filePath 
     * @return
     */
    public static BufferedImage refineImage(BufferedImage notRefinedImage) {
        // Create a new BufferedImage that represents the cropped area
        BufferedImage croppedImage = notRefinedImage.getSubimage(x, y, width, height);
        return croppedImage;
    }

    /**
     * 
     * @param image splits given BufferedImage into 3 parts with hardcoded positions
     * @return returns array of length 3 of those cropped images
     */
    public static BufferedImage[] splitImage(BufferedImage image){
        BufferedImage[] images = new BufferedImage[3];
        BufferedImage block1 = cropImage(0,image);
        images[0] = block1;
        BufferedImage block2 = cropImage(1,image);
        images[1] = block2;
        BufferedImage block3 = cropImage(2,image);
        images[2] = block3;

        return images;
    }

    /**
     * @param blockIndex creates separate image of one block
     * @param image of given BufferedImage
     * @return returns this immage
     */
    private static BufferedImage cropImage(int blockIndex, BufferedImage image){
        // indexes of: 0, 1, 2
        BufferedImage croppedImage = image.getSubimage(blockWidth*blockIndex , 0, blockWidth, height);
        return croppedImage;
    }

    /**
     * Saves a BufferedImage to disk. This method attempts to write the provided
     * BufferedImage to a file using the filename provided.
     *
     * @param image The BufferedImage object to save.
     * @param name The name and path of the file where the image is to be saved. 
     *             The format is inferred from the file extension.
     */
    public static void saveImage(BufferedImage image, String name) {
        try {
            String formatName = name.substring(name.lastIndexOf('.') + 1);
            File outputFile = new File(name);
            ImageIO.write(image, formatName, outputFile);
            //System.out.println(name + " file saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the file: " + name);
            e.printStackTrace();
        }
    }

    public static void saveBoard(BufferedImage image){
        try {
            String name = "Img/board.png";
            File outputFile = new File(name);
            // Ensure the directory exists
            outputFile.getParentFile().mkdirs();
            // Corrected to use just the format "png"
            ImageIO.write(image, "png", outputFile);
            //System.out.println(name + " file saved successfully!");
        } catch (IOException ex) {
            System.out.println("Error saving board file");
            ex.printStackTrace();
        }
    }

    public static BufferedImage[] getImgArray(String imagePath){
        BufferedImage processedImg = refineImage(imagePath);
        BufferedImage[] colorBlocks = splitImage(processedImg);

        return colorBlocks;
    }

    public static BufferedImage[] getImgArray(BufferedImage imageToCrop){
        BufferedImage processedImg = refineImage(imageToCrop);
        BufferedImage[] colorBlocks = splitImage(processedImg);

        return colorBlocks;
    }

    private static int[] indexes = new int[3];
    public static Block[] detectedBlocks = new Block[3];

    public ImageCropper(String imagePath) {   
        // Process the image and get the individual blocks.
        BufferedImage[] images = getImgArray(imagePath);
        
        // Initialize ImageFinder.
        ImageFinder finder = new ImageFinder();
    
        // Save individual block images.
        for (int i = 0; i < images.length; i++) {
            String fileName = "Img/block_" + i + ".png";
            saveImage(images[i], fileName);
        }
    
        // Wait for files to be written to disk.
        for (int i = 0; i < images.length; i++) {
            File imageFile = new File("Img/block_" + i + ".png");
            if (!imageFile.exists()) {
                //System.out.println("File does not exist: " + imageFile.getAbsolutePath());
                continue;
            }
            
            // Compare with indexed images and get the best match.
            int index = finder.getIndex(imageFile);
            if (index == -1) {
                //System.out.println("No similar image found for file: " + imageFile.getAbsolutePath());
                detectedBlocks[i] = null; // Assign null to indicate no block detected.
            } else {
                // If a similar image is found, create the corresponding Block.
                BlockFactory factory = new BlockFactory();
                detectedBlocks[i] = factory.createBlock(index);
                if (detectedBlocks[i] != null) {
                    //detectedBlocks[i].printBlock();
                }
            }
        }
    }

    public static int[] getImgIndexes(){
        return indexes;
    }

}
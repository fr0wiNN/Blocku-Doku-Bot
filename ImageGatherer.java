import javax.imageio.ImageIO;

import ImageProcessing.ImageCropper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGatherer {

    private static int counter = 1; // Static counter to keep track of the file naming

    public static void main(String[] args) {
        File fromDirectory = new File("Server/Server_Img/");
        File toDirectory = new File("Img/index");

        // Ensure the target directory exists
        if (!toDirectory.exists()) {
            toDirectory.mkdirs();
        }

        File[] files = fromDirectory.listFiles();
        counter = files.length;
        if (files != null) {
            for (File file : files) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    if (image != null) {
                        saveImage(image, toDirectory);
                    }
                } catch (IOException e) {
                    System.out.println("Error processing " + file.getName() + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("The directory is empty or it does not exist.");
        }
    }

    /**
     * Saves the provided BufferedImage to the specified directory with a name based on a static counter.
     *
     * @param image the BufferedImage to save.
     * @param directory the directory where the image will be saved.
     * @throws IOException if an error occurs during the file writing process.
     */
    private static void saveImage(BufferedImage image, File directory) throws IOException {
        BufferedImage[] blocks = new BufferedImage[3];
        blocks = ImageCropper.getImgArray(image);
        for(int x = 0 ; x<3 ; x++){
            File outputFile = new File(directory, "ZZZ" + counter + ".png");
            ImageIO.write(blocks[x], "png", outputFile);
            System.out.println(outputFile.getName() + " saved successfully.");
            counter++;// Increment the counter after saving the file
        }   
    }
}

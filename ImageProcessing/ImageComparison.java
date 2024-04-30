package ImageProcessing;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The ImageComparison class provides a method to compare two images
 * and determine their similarity.
 */
public class ImageComparison {

    private static final int TOLERANCE = 13; // You can adjust this value for tolerance

    /**
     * Compares two images and calculates a similarity score.
     *
     * @param file1 the first image file
     * @param file2 the second image file
     * @return similarity score as a double
     * @throws IOException if an error occurs during reading of the images
     */
    public static double compareImages(File file1, File file2) throws IOException {
        // Load the images
        BufferedImage img1 = ImageIO.read(file1);
        BufferedImage img2 = ImageIO.read(file2);

        // Check if both images have the same dimensions
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return 0.0;
        }

        long similarPixels = 0;
        long totalPixels = img1.getWidth() * img1.getHeight();

        // Compare pixels with tolerance
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                if (isSimilar(rgb1, rgb2, TOLERANCE)) {
                    similarPixels++;
                }
            }
        }

        // Calculate similarity percentage
        return (double) similarPixels / totalPixels;
    }

    /**
     * Determines if two RGB values are similar within a given tolerance.
     *
     * @param rgb1      the first RGB value
     * @param rgb2      the second RGB value
     * @param tolerance the tolerance level for similarity
     * @return true if the RGB values are similar; false otherwise
     */
    private static boolean isSimilar(int rgb1, int rgb2, int tolerance) {
        Color c1 = new Color(rgb1);
        Color c2 = new Color(rgb2);

        return Math.abs(c1.getRed() - c2.getRed()) <= tolerance &&
               Math.abs(c1.getGreen() - c2.getGreen()) <= tolerance &&
               Math.abs(c1.getBlue() - c2.getBlue()) <= tolerance;
    }
}

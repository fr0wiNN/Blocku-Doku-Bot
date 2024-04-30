package ImageProcessing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageFinder {

    private static String indexPath = "Img/index";
    private static File folder = new File(indexPath);
    private static File[] listOfFiles = folder.listFiles();
    private static ArrayList<File> indexPaths = new ArrayList<>();

    public ImageFinder(){
        for (File file : listOfFiles) {
            if (file.isFile()) {
                indexPaths.add(file);
            } else if (file.isDirectory()) {
                // This block will execute for each directory in the folder
                // If you only want to process files and ignore subdirectories, you can omit this part
                System.out.println(file.getName() + " is a directory.");
            }
        }
    }

    /**
     * Finds the index of the file that is most similar to the given file.
     *
     * @param file the file to compare with the indexed files
     * @return the index of the most similar file, or -1 if no similar file is found
     */
    public static int getIndex(File file){
        double bestScore = -1;
        int bestIndex = -1;

        for (File baseFile : indexPaths) {
            double score = 0.0;
            try {
                score = ImageComparison.compareImages(baseFile, file);
                if (score > bestScore) {
                    bestScore = score;
                    bestIndex = getIndexFromName(baseFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bestIndex != -1) {
            //System.out.println("Most similar picture index: " + bestIndex);
        } else {
            //System.out.println("Picture not found");
        }

        return bestIndex;
    }

    /**
     * Extracts the index from the file name assuming the file name contains the index
     * as the prefix followed by ".png".
     *
     * @param file the file from which to extract the index
     * @return the extracted index as an integer
     */
    private static int getIndexFromName(File file){
        String[] number = file.getName().split(".png");
        int index = -1;
        try {
            index = Integer.parseInt(number[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return index;
    }
}

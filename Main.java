import java.io.File;
import java.util.Arrays;

import Game.*;
import Game.Block.Block;
import ImageProcessing.*;

public class Main {

    public static void main(String[] args) {

        String ssPath;

        if(args.length ==0)
            ssPath = "C:\\Users\\Max\\Desktop\\Projects\\BlokuDoku\\Server\\Server_Img\\20240424235322.png";
        else ssPath = args[0];//System.out.println("Given args: " + args[0]);}
        //System.out.println("-- Image Cropper init --");
        ImageCropper cropper = new ImageCropper(ssPath);
        Game moveSimulation = new Game();
        moveSimulation.setBoard(BoardRecognition.getBoardFromImage(new File("Img/board.png")));
        try {
            Thread.sleep(300);
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }

        moveSimulation.simulateMoves(cropper.detectedBlocks);
        //Block[] a = cropper.detectedBlocks;
        //for (Block b : a) {
        //    System.out.println(b.getSize());
        //}
    }
}

package Game.Block;
import java.util.Random;

public class BlocksDatabase {

    public int[][] getBlock(int index){
        return blockset[index];
    }

    public int[][] getRandomBlock(){
        Random random = new Random();
        return blockset[random.nextInt(48)];
    }

    public static void main(String[] args) {
        BlockFactory a = new BlockFactory();
        Block testBlock = a.createBlock(1);
        System.out.println(testBlock.getWidth());
    }

    public static int getSize(int index){
        return blockSize[index];
    }

    //I know... It's stupid
    private static int[] blockSize = {
        1,2,3,4,5,2,3,4,5,5,5,5,5,5,5,5,5,5,3,3,3,3,4,4,4,4,4,4,4,4,4,2,3,4,2,3,4,4,4,4,4,5,5,5,5,5,5,5,5
    };

    private static int[][][] blockset = {
        {{1}},

        {{1},
         {1}},

        {{1},
         {1},
         {1}},
        
        {{1},
         {1},
         {1},
         {1}},

        {{1},
         {1},
         {1},
         {1},
         {1}},

        {{1,1}},
        
        {{1,1,1}},

        //first col finished

        {{1,1,1,1}},

        {{1,1,1,1,1}},

        {{0,1,0},
         {1,1,1},
         {0,1,0}},
        
        {{1,1,1},
         {0,1,0},
         {0,1,0}},
        
        {{0,1,0},
         {0,1,0},
         {1,1,1}},
        
        {{0,0,1},
         {1,1,1},
         {0,0,1}},
        
        {{1,0,0},
         {1,1,1},
         {1,0,0}},

        //second col finished

        {{1,1,1},{0,0,1},{0,0,1}},

        {{1,1,1},{1,0,0},{1,0,0}},

        {{1,0,0},{1,0,0},{1,1,1}},

        {{0,0,1},{0,0,1},{1,1,1}},

        {{1,1},{0,1}},

        {{1,1},{1,0}},

        {{1,0},{1,1}},

        //third col finished

        {{0,1},{1,1}},

        {{1,1},{1,1}},

        {{1,1},{1,0},{1,0}},

        {{1,1},{0,1},{0,1}},

        {{0,1},{0,1},{1,1}},

        {{1,0},{1,0},{1,1}},

        {{0,0,1},{1,1,1}},

        //fourth col finished

        {{1,0,0},{1,1,1}},
        {{1,1,1},{1,0,0}},
        {{1,1,1},{0,0,1}},
        {{1,0},{0,1}},
        {{1,0,0},{0,1,0},{0,0,1}},
        {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}},
        {{0,1},{1,0}},

        //fifth col finished

        {{0,0,1},{0,1,0},{1,0,0}},

        {{0,0,0,1},{0,0,1,0},{0,1,0,0},{1,0,0,0}},

        {{1,1,0},{0,1,1}},

        {{0,1},{1,1},{1,0}},

        {{0,1,1},{1,1,0}},

        {{1,0},{1,1},{0,1}},

        {{1,0,1},{1,1,1}},
        
        //sixth col finished

        {{1,1,1},{1,0,1}},

        {{1,1},{1,0},{1,1}},

        {{1,1},{0,1},{1,1}},

        {{0,1,0},{1,1,1}},

        {{1,1,1},{0,1,0}},
        
        {{1,0},{1,1},{1,0}},

        {{0,1},{1,1},{0,1}},

        //seventh col finished
    };


}
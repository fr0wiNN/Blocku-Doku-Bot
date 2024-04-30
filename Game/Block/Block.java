package Game.Block;

public class Block {
    private int[][] blockArray;
    private static BlocksDatabase database = new BlocksDatabase();
    private int index;

    Block(int index){
        this.index = index;
        blockArray = database.getBlock(index);
    }

    Block(){
        database = new BlocksDatabase();
        blockArray = database.getRandomBlock();
    }

    public int getWidth(){
        return blockArray[0].length;
    }

    public int getHeight(){
        return blockArray.length;
    }

    public int[][] getBlockArray(){
        return blockArray;
    }

    public void printBlock(){
        for(int y = 0 ; y < blockArray.length ; y++){
            for(int x = 0 ; x< blockArray[0].length ; x++){
                if(blockArray[y][x] != 0)
                    System.out.print("■ ");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println("---------");
    }

    @Override
    public String toString() {
        String text = ""; 
        for(int y = 0 ; y < blockArray.length ; y++){
            for(int x = 0 ; x< blockArray[0].length ; x++){
                if(blockArray[y][x] != 0)
                text+=("■ ");
                else
                text+=("  ");
            }
            text+="\n";
        }
        return text;
    }

    public int getSize(){
        return database.getSize(index);
    }

}

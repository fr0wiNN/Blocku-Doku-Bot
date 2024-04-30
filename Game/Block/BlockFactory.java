package Game.Block;

public class BlockFactory {
    public BlockFactory(){
         
    }

    public Block[] createBlocks(){
        Block[] blocks = new Block[3];
        blocks[0] = new Block();
        blocks[1] = new Block();
        blocks[2] = new Block();
        blocks[0].printBlock();
        blocks[1].printBlock();
        blocks[2].printBlock();
        return blocks;
    }

    public Block createBlock(int index){
        return new Block(index);
    }

    public Block[] createBlocksByIndex(int x, int y, int z){
        Block[] blocks = new Block[3];
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("z: " + z);
        blocks[0] = new Block(x);
        blocks[1] = new Block(y);
        blocks[2] = new Block(z);
        blocks[0].printBlock();
        blocks[1].printBlock();
        blocks[2].printBlock();
        return blocks;
    }
}

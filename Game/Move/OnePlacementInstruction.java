package Game.Move;

import java.util.ArrayList;
import java.util.List;

import Game.Block.Block;

public class OnePlacementInstruction {
    
    public int blockIndex;
    public Block block;
    public int x;
    public int y;

    public OnePlacementInstruction(int x, int y, int blockIndex, Block block){
        this.x = x;
        this.y = y;
        this.blockIndex = blockIndex;
        this.block = block;
    }

    public OnePlacementInstruction(){}

    @Override
    public String toString() {
        String text = "-- Placement Instruction --\n" +
        "Block index: " + blockIndex + "\n"+
        "Block x: " + x + "\n"+
        "Block y: " + y + "\n"+
        "Block: \n" +
        block.toString();
        return text;
    }

}

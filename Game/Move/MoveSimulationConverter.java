package Game.Move;

public class MoveSimulationConverter {

    private static int blockSize = 110;
    private static int xOffset = 46;
    private static int yOffset = 600;


    public static void convertToSimulationInstruction(OnePlacementInstruction instruction){
        printStartLocation(instruction.blockIndex);
        printEndLocation(instruction);
    }

    private static void printStartLocation(int index){
        switch (index) {
            case 0:
                //System.out.print("210 1887 "); -83
                System.out.print("210 "+ (1887 + 83 + 43) +" ");
                break;
            case 1:
                System.out.print("540 "+ (1887 + 83 + 43) +" ");
                break;
            case 2:
                System.out.print("870 "+ (1887 + 83 + 43) +" ");
                break;
        }
    }

    private static void printEndLocation(OnePlacementInstruction instruction) {
        int x = centerX(instruction.block.getWidth());
        int y = centerY(instruction.block.getHeight());
        x+=(instruction.x*blockSize);
        y+=(instruction.y*blockSize);

        //System.out.print("("+ x+instruction.x*blockSize +","+ y+instruction.y*blockSize +")}\n");
        System.out.print(x +" "+ y +" ");
    }

    private static int centerX(int blockWidth){
        return xOffset + (blockWidth*110)/2 ;
    }

    private static int centerY(int blockHeight){
        return yOffset + (blockHeight*110) + ((5*110 - blockHeight*110)/2) + 83;
    }
}

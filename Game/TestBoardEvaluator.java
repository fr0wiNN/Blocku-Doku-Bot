package Game;

public class TestBoardEvaluator extends BoardEvaluator{


    @Override
    public double getScore() {
        return super.chromosome.a * getBubbles() +
               super.chromosome.b * getOccupiedCells() +
               super.chromosome.c * getZ() +
               super.chromosome.d * getW() +
               super.chromosome.f +
               super.chromosome.g * getT() +
               super.chromosome.h * getS() +
               super.chromosome.i * getP();
    }

    //return sum of bubbles, where area of it is less than 4
    //diagonal cell is not concidered to be adjecent
    private int getBubbles() {
        return 0;
    }

    //return sum of occupied cells 
    private int getOccupiedCells(){
        return 0;
    }

}

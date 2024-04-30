package Training;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Game.Game;
import Game.Block.Chromosome;

public class Train {
    private static final int POPULATION_SIZE = 100;
    private static List<Chromosome> generationChromosomes = new ArrayList<>();


    public static void main(String[] args) {
        setUpStartingChromosomes();
        while(true){
            simulateGen();
            generationChromosomes.clear();
        }
    }

    private static void simulateGen(){
        for (Chromosome chromosome : generationChromosomes) {
            int simScore = Game.simulateGame(chromosome);
        }
    }

    private static void setUpStartingChromosomes() {
        for(int x = 0 ; x < POPULATION_SIZE ; x++){
            generationChromosomes.set(x, new Chromosome(0,0,0,0,0,0,0,0));
        }
    }
}

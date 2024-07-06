package Training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Game.Game;
import Game.Block.Chromosome;

public class Train {
    private static final int POPULATION_SIZE = 10;
    private static final double CROSSOVER_RATE = 0.75;
    private static final double MUTATION_RATE = 0.01;
    private static final int ELITISM_COUNT = 10;
    private static final int TOURNAMENT_SIZE = 5;
    private static List<Chromosome> generationChromosomes = new ArrayList<>();
    private static Random random = new Random();

    public static void main(String[] args) {

        //train();

        testChromosome(getBestChromosome());
    }

    private static Chromosome getBestChromosome(){
        double a = -83.26; 
        double b = -5.36;
        double c = -5.28;
        double d = -43.44; 
        double f = -77.72;
        double g = 71.12;
        double h = 7.17; 
        double i = -31.90; 
        return new Chromosome(a, b, c, d, f, g, h, i);
    }

    private static void train(){
        //setUpBestSoFarChromosome();
        setUpBestSoFarChromosomeRandom();
        for (Chromosome a : generationChromosomes) {
            //System.out.println(a.toString());
        }
        int generations = 1000;
        for (int gen = 0; gen < generations; gen++) {
            System.out.println("- Start of " + (gen + 1) + " generation -");
            simulateGen();
            evolve();
            System.out.println("-- End of " + (gen + 1) + " generation --\n\n");
        }
    }

    private static void testChromosome(Chromosome chromosomeToTest){
        while (true) {
            int simScore = Game.getGameScore(chromosomeToTest);
            chromosomeToTest.setFitness(simScore);
            System.out.println("Chromosome details: " + chromosomeToTest.toString());
        }
    }

    private static void simulateGen() {
        int scoreSum = 0;
        int highestScore = Integer.MIN_VALUE;
        int lowestScore = Integer.MAX_VALUE;


        for (Chromosome chromosome : generationChromosomes) {
            int simScore = Game.getGameScore(chromosome);
            scoreSum+=simScore;
            if(simScore>highestScore){highestScore = simScore;}
            if(lowestScore>simScore){lowestScore = simScore;}
            chromosome.setFitness(simScore);
            System.out.println("Chromosome details: " + chromosome.toString());
        }
        System.out.println("\n|| Average Score: " + (scoreSum/POPULATION_SIZE) + " ||");
        System.out.println("|| Highest Score: " + highestScore + " ||");
        System.out.println("|| Lowest Score: " + lowestScore + " ||");
    }

    private static void evolve() {
        List<Chromosome> newGeneration = new ArrayList<>();
        // Implement elitism
        generationChromosomes.sort((c1, c2) -> Integer.compare(c2.getFitness(), c1.getFitness()));
        for (int i = 0; i < ELITISM_COUNT; i++) {
            newGeneration.add(generationChromosomes.get(i));
        }
        // Crossover and mutation
        while (newGeneration.size() < POPULATION_SIZE) {
            Chromosome parent1 = selectParent();
            Chromosome parent2 = selectParent();
            if (random.nextDouble() < CROSSOVER_RATE) {
                newGeneration.addAll(crossover(parent1, parent2));
            }
            if (random.nextDouble() < MUTATION_RATE) {
                mutate(parent1);
                mutate(parent2);
            }
        }
        generationChromosomes = newGeneration;
    }

    private static Chromosome selectParent() {
        // Tournament selection
        Chromosome best = null;
        for (int i = 0; i < TOURNAMENT_SIZE; i++) { // Tournament size of 5
            int randomIndex = random.nextInt(generationChromosomes.size());
            Chromosome temp = generationChromosomes.get(randomIndex);
            if (best == null || temp.getFitness() > best.getFitness()) {
                best = temp;
            }
        }
        return best;
    }

    private static List<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        if (random.nextDouble() > CROSSOVER_RATE) {
            // Returning deep copies of parents if no crossover occurs
            return List.of(new Chromosome(parent1.a, parent1.b, parent1.c, parent1.d, parent1.f, parent1.g, parent1.h, parent1.i),
                           new Chromosome(parent2.a, parent2.b, parent2.c, parent2.d, parent2.f, parent2.g, parent2.h, parent2.i));
        }
        int crossoverPoint = random.nextInt(8); // Assuming 8 genes from a to i, excluding 'e'
        double[] genes1 = {parent1.a, parent1.b, parent1.c, parent1.d, parent1.f, parent1.g, parent1.h, parent1.i};
        double[] genes2 = {parent2.a, parent2.b, parent2.c, parent2.d, parent2.f, parent2.g, parent2.h, parent2.i};

        // Performing crossover
        for (int i = crossoverPoint; i < genes1.length; i++) {
            double temp = genes1[i];
            genes1[i] = genes2[i];
            genes2[i] = temp;
        }

        return List.of(new Chromosome(genes1[0], genes1[1], genes1[2], genes1[3], genes1[4], genes1[5], genes1[6], genes1[7]),
                       new Chromosome(genes2[0], genes2[1], genes2[2], genes2[3], genes2[4], genes2[5], genes2[6], genes2[7]));
    }

    private static void mutate(Chromosome chromosome) {
        if (random.nextDouble() < MUTATION_RATE) {
            int geneIndex = random.nextInt(8); // Random gene index
            double mutationValue = randomDoubleInRange(-100.0, 100.0); // New mutation value
            switch (geneIndex) {
                case 0:
                    chromosome.a = mutationValue;
                    break;
                case 1:
                    chromosome.b = mutationValue;
                    break;
                case 2:
                    chromosome.c = mutationValue;
                    break;
                case 3:
                    chromosome.d = mutationValue;
                    break;
                case 4:
                    chromosome.f = mutationValue;
                    break;
                case 5:
                    chromosome.g = mutationValue;
                    break;
                case 6:
                    chromosome.h = mutationValue;
                    break;
                case 7:
                    chromosome.i = mutationValue;
                    break;
            }
        }
    }

    private static void setUpStartingChromosomes() {
        for (int j = 0; j < POPULATION_SIZE; j++) {
            double a = randomDoubleInRange(-100.0, 100.0);
            double b = randomDoubleInRange(-100.0, 100.0);
            double c = randomDoubleInRange(-100.0, 100.0);
            double d = randomDoubleInRange(-100.0, 100.0);
            double f = randomDoubleInRange(-100.0, 100.0);
            double g = randomDoubleInRange(-100.0, 100.0);
            double h = randomDoubleInRange(-100.0, 100.0);
            double i = randomDoubleInRange(-100.0, 100.0);
            generationChromosomes.add(new Chromosome(a, b, c, d, f, g, h, i));
        }
    }

    //Chromosome details: { a: -83,26, b: -5,36, c: -5,28, d: -43,44, f: -77,72, g: 71,12, h: 7,17, i: -31,90, Fitness: 40871 }
    private static void setUpBestSoFarChromosomeRandom(){
        for (int j = 0; j < POPULATION_SIZE; j++) {
            double a = -77.67 + randomDoubleInRange(-10.0, 10.0);
            double b = -4.96 + randomDoubleInRange(-10.0, 10.0);
            double c = 2.60 + randomDoubleInRange(-10.0, 10.0);
            double d = -36.49 + randomDoubleInRange(-10.0, 10.0);
            double f = -84.35 + randomDoubleInRange(-10.0, 10.0);
            double g = 78.82 + randomDoubleInRange(-10.0, 10.0);
            double h = 2.51 + randomDoubleInRange(-10.0, 10.0);
            double i = -26.32 + randomDoubleInRange(-10.0, 10.0);
            generationChromosomes.add(new Chromosome(a, b, c, d, f, g, h, i));
        }
    }

    private static void setUpBestSoFarChromosome(){
        for (int j = 0; j < POPULATION_SIZE; j++) {
            double a = -83.26; 
            double b = -5.36;
            double c = -5.28;
            double d = -43.44; 
            double f = -77.72;
            double g = 71.12;
            double h = 7.17; 
            double i = -31.90; 
            generationChromosomes.add(new Chromosome(a, b, c, d, f, g, h, i));
        }
    }

    private static double randomDoubleInRange(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}

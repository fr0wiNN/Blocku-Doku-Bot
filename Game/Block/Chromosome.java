package Game.Block;

public class Chromosome {
    public double a, b, c, d, f, g, h, i;

    public Chromosome(double a, double b, double c, double d, double f, double g, double h, double i) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
    }

    //public static Chromosome bestChromosome = new Chromosome(48.55, 8.04, 17.2, 2.69, 31.23, -40.37, -2.42, -20.74);

    public static double aa = -83.26; 
    public static double ba = -5.36;
    public static double ca = -5.28;
    public static double da = -43.44; 
    public static double fa = -77.72;
    public static double ga = 71.12;
    public static double ha = 7.17; 
    public static double ia = -31.90; 
    public static Chromosome bestChromosome = new Chromosome(aa, ba, ca, da, fa, ga, ha, ia);

    @Override
    public String toString() {
        return String.format("{ a: %.2f, b: %.2f, c: %.2f, d: %.2f, f: %.2f, g: %.2f, h: %.2f, i: %.2f, Fitness: %d }",
                             a, b, c, d, f, g, h, i, fitness);
    }

    private int fitness = 0;

    public void setFitness(int simScore) {
        fitness = simScore;
    }

    public int getFitness() {
        return fitness;
    }
}



//public static Chromosome bestChromosome = new Chromosome(-25.86, -14.21, -20.49, -23.71, -39.9, 31.13, -15.36, 26.54);

package Game.Block;

public class Chromosome {
    public double a,b,c,d,f,g,h,i;

    public Chromosome(double a,double b, double c, double d, double f, double g, double h, double i){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
    }

    //public static Chromosome bestChromosome = new Chromosome(-25.86, -14.21, -20.49, -23.71, -39.9, 31.13, -15.36, 26.54);
    //public static Chromosome bestChromosome = new Chromosome(-26, -7.63, 5.18, -7, 9.02, 28.86, -4.36, -8.88);
    //Best Chromosome: { a: 44.05, b: 14.42, c: 25.37, d: 50.05, e: -23.17, f: 44.72, g: -32.32, h: -35.73, i: 37.35 }
    //Best Chromosome: { a: 3.09, b: 15.2, c: 9.61, d: -5.22, e: 47.4, f: -13.95, g: -43.75, h: -14.51, i: -10.51 }

    //Best Chromosome: { a: -25.86, b: -14.21, c: -20.49, d: -23.71, e: -20.13, f: -39.9, g: 31.13, h: -15.36, i: 26.54 }
    //Best Chromosome: { a: 10.16, b: 44.36, c: 34.08, d: 24.49, e: -40.76, f: 39.61, g: -34.18, h: -23.56, i: 11.35 }
    public static Chromosome bestChromosome = new Chromosome(48.55, 8.04, 17.2, 2.69, 31.23, -40.37, -2.42, -20.74);
    //Best Chromosome: { a: -36.74, b: -26.07, c: -14.4, d: -14.9, e: -48.57, f: -41.43, g: 27.05, h: 0.56, i: -7.21 }

    //a: 48.55, b: 8.04, c: 17.2, d: 2.69, e: 19.83, f: 31.23, g: -40.37, h: -2.42, i: -20.74
}

/*----------------------------------------------------------------
 * Author:       Emma He
 * Written:      09/20/2015
 * Last updated: 09/21/2015
 * 
 * Perform T independent computational experiments on an N-by-N grid, 
 * prints the mean, standard deviation, and the 95% confidence 
 * interval for the percolation threshold. 
 * 
 * console of DrJava 
 * % run PercolationStats 2 10000
 * mean = 0.6648
 * stddev = 0.11850048200924009
 * 95% confidence interval = 0.6624773905526189, 0.667122609447381
 * 
 * ----------------------------------------------------------------*/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] threshold; // array of threshold for each experiment
    private int t;              // numbers of experiments
    private double mean;        // mean of threshold[]
    private double stddev;      // standard deviation of threshold[]
    private double hi;          // high endpoint of 95% confidence interval
    private double lo;          // low endpoint of 95% confidence interval
    
    /**
     * Perform T independent experiments on an N-by-N grid.
     * Conduct Monte Carlo simulation to estimate the percolation threshold.
     * All results go to threshold[] array.
     * 
     * Throw a java.lang.IllegalArgumentException if either N <= 0 or T <= 0.
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("Illegal argument");
        t = T;
        threshold = new double[T];
        for (int i = 0; i < T; i++)
        {
            Percolation per = new Percolation(N);
            int count = 0;
            while (!per.percolates())
            {
                int a, b;
                
                do {
                a = StdRandom.uniform(1, N+1);
                b = StdRandom.uniform(1, N+1);
                } while (per.isOpen(a, b));
                
                count++;
                per.open(a, b);
                
            }
            threshold[i] = ((double) count)/(N*N);     
        }
        this.mean = StdStats.mean(threshold);
        this.stddev = StdStats.stddev(threshold);
        this.hi = this.mean + 1.96*stddev/Math.sqrt(t);
        this.lo = this.mean - 1.96*stddev/Math.sqrt(t);
    }
    /**
     * Returns sample mean of percolation threshold
     */
    public double mean() {
        return this.mean;
    }
    
    /**
     * Returns sample standard deviation of percolation threshold
     */
    public double stddev() {
        return this.stddev;
    }
    
    /**
     * Returns low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return this.lo;
    }
    
    /**
     * Returns high endpoint of 95% confidence interval
     */
    public double confidenceHi() { 
        return this.hi;
    }
    
    /**
     * Test client to perform the experiments
     * Print the mean, standard deviation, and the 95% confidence 
     * interval for the percolation threshold. 
     */
    public static void main(String[] args) {
    int n = StdIn.readInt();
    int t = StdIn.readInt();
    PercolationStats stats = new PercolationStats(n, t);
    StdOut.println("mean = " + stats.mean());
    StdOut.println("stddev = " + stats.stddev());
    StdOut.print("95% confidence interval = ");
    StdOut.println(stats.confidenceLo() + ", "+ stats.confidenceHi());
    }
}
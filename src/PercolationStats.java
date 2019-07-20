import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation percTestRun;

    private int[] percThreshold; // percolation threshold for each run

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        //Check for valid inputs
        if(n <= 0)
            throw new  IndexOutOfBoundsException("Grid size must be > 0");
        if(trials <= 0)
            throw new  IndexOutOfBoundsException("Trials must be > 0");

        percThreshold = new int[trials]; // one spot for each trial
        int rowToOpen;
        int colToOpen;

        for (int currTrial = 1; currTrial <= trials ; currTrial++) {
            percTestRun = new Percolation(n); // new percolation object

            while(!percTestRun.percolates()){
                rowToOpen = StdRandom.uniform(1,n+1);   // random num in interval [a;b)
                colToOpen = StdRandom.uniform(1, n+1);
                percTestRun.open(rowToOpen,colToOpen);
            }

            percThreshold[currTrial] = percTestRun.numberOfOpenSites(); // save threshold to array

        }
        System.out.print("Monte Carlo simulation complete with " + trials + " on a " + n + "x" + n + " grid.");
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(percThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(percThreshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()

    // high endpoint of 95% confidence interval
    public double confidenceHi()

    // test client (see below)
    public static void main(String[] args)
}

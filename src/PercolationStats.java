import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    private final int trials; // store number of trials
    private final double[] percThreshold; // will store percolation threshold for each run

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        // Check for valid inputs
        if (n <= 0)
            throw new  IndexOutOfBoundsException("Grid size must be > 0");
        if (trials <= 0)
            throw new  IndexOutOfBoundsException("Trials must be > 0");

        this.trials = trials;
        percThreshold = new double[trials]; // one spot for each trial
        int rowToOpen;  // random row to open
        int colToOpen;  // random column to open

        for (int currTrial = 0; currTrial < trials; currTrial++) {
            Percolation percTestRun = new Percolation(n); // new percolation object

            while (!percTestRun.percolates()) {
                rowToOpen = StdRandom.uniform(1, n+1);   // random num in interval [a;b)
                colToOpen = StdRandom.uniform(1, n+1);
                percTestRun.open(rowToOpen, colToOpen);
            }

            percThreshold[currTrial] = (double) percTestRun.numberOfOpenSites()/(n*n); // save threshold (site vacancy ration) to array

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percThreshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * Math.sqrt(stddev()))/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * Math.sqrt(stddev()))/Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        // Check for valid arguments
        if (args.length < 2) {
            System.err.println("Requires two argument: <grid size> <trials>");
          }

        // parse command line arguments
        int gridSizeN = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        // Run Monte Carlo sim
        PercolationStats percStats = new PercolationStats(gridSizeN, trials);

        // print results
        // System.out.println("Monte Carlo simulation complete with " + trials + " trials on a " + gridSizeN + "x" + gridSizeN + " grid.\n");
        System.out.println("mean \t\t\t\t\t= " + percStats.mean());
        System.out.println("stddev \t\t\t\t\t= " + percStats.stddev());
        System.out.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
    }
}

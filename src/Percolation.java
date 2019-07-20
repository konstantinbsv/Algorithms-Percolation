import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF weightedQuickUF;

    private boolean[][] grid;
    private final int n;
    private int numOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n < 1)
            throw new IllegalArgumentException("Grid size must be > 0");

        this.n = n; // save value for n*n grid
        grid = new boolean[n][n];   // all false (blocked) by default

        // Initialize Weighted QUF with n*n sites; add 2 extra spots for virtual roots
        weightedQuickUF = new WeightedQuickUnionUF(n*n+2);

    }


    /** opens the site (row, col)
     * <p>
     * By convention, the row and column indices are integers
     * between 1 and n, where (1, 1) is the upper-left site
     * <p/>
     * @param row row of site to open
     * @param col column of site to open
     */
    public void open(int row, int col){
        throwIfNotValid(row, col);

        if (isOpen(row,col))
            return;

        /* First and Last rows */
        if(row == 1) // if site to open is on first row
            weightedQuickUF.union(xyTo1D(row,col), 0); // union with top virtual site
        else
            if(isOpen(row-1, col)) // if site on previous is open
                weightedQuickUF.union(xyTo1D(row,col), xyTo1D(row-1,col));

        if(row == n) // if site to open is on last row
            weightedQuickUF.union(xyTo1D(row,col), n*n+1); // union with bottom virtual site
        else
            if(isOpen(row+1, col)) // if site on next row is open
                weightedQuickUF.union(xyTo1D(row,col), xyTo1D(row+1,col));

        /* Left and Right side columns */
        if(col > 1) // if site is not on left-most column
            if(isOpen(row, col-1)) // if previous site on same row is open
                weightedQuickUF.union(xyTo1D(row,col), xyTo1D(row,col-1));

        if(col < n) // if site is not on right-most column
            if(isOpen(row, col+1)) // if next site on same row is open
                weightedQuickUF.union(xyTo1D(row,col), xyTo1D(row,col+1));


        grid[row - 1][col - 1] = true;  //Set this site as open in boolean grid
        numOpenSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        throwIfNotValid(row, col);

        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    // a full site is an open site that can be connected to an open
    // site at the top via a chain of neighbouring
    public boolean isFull(int row, int col){
        throwIfNotValid(row, col);
        return weightedQuickUF.connected(xyTo1D(row,col), 0); //0 is top virtual site
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return numOpenSites;
    }

    // does the system percolate?
    // i.e., is there a full site in the bottom row?
    public boolean percolates(){
        return weightedQuickUF.connected(0, n*n+1); // is top virtual connected to bottom virtual site
    }

    /**
     * Given 2D site location, returns index for 1D roots array
     *
     * @param row row of site
     * @param col column of site
     * @return  index for 1D array
     */
    private int xyTo1D(int row, int col){
        return (row-1)*n + col;
    }

    // Throws IndexOutOfBoundsException exception if the specified site is not valid
    private void throwIfNotValid(int row, int col){
        if (row <= 0 || row > n)
            throw new IllegalArgumentException("Row index i out of bounds");
        if (col <= 0 || col > n)
            throw new IllegalArgumentException("Column index i out of bounds");
    }

    // test client (optional)
    public static void main(String[] args) {

        final int N = 100; //size of grid

        Percolation percTest = new Percolation(N);
        int rowToOpen;
        int colToOpen;

        System.out.println("n: " + N);

        while(!percTest.percolates()){
            rowToOpen = StdRandom.uniform(1,N+1);
            colToOpen = StdRandom.uniform(1, N+1);

            percTest.open(rowToOpen, colToOpen);
            System.out.println("Open sites: " + percTest.numberOfOpenSites());

            System.out.println("n: " + N);
            System.out.println("isOpen: " + percTest.isOpen(rowToOpen, colToOpen));
            System.out.println("isFull: " + percTest.isFull(rowToOpen, colToOpen));

            System.out.println("Opening.... ");
            percTest.open(rowToOpen, colToOpen);
            System.out.println("isOpen: " + percTest.isOpen(rowToOpen, colToOpen));
            System.out.println("isFull: " + percTest.isFull(rowToOpen, colToOpen));
            System.out.println("Open sites: " + percTest.numberOfOpenSites());

            // Draw a grid
            System.out.println("Printing grid.....");
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (percTest.isOpen(i, j))
                        System.out.print("1");
                    else
                        System.out.print("0");
                    System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println("percolates = " + percTest.percolates());
            System.out.println();

        }

        System.out.println("Percolation!");
        System.out.println("Site vacancy ratio " + (double) percTest.numberOfOpenSites()/ Math.pow(N,2));


    }
}

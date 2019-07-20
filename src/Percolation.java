import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF weightedQuickUF;

    private boolean[][] grid;
    private boolean percolates;
    private int n;
    private int numOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        this.n = n; // save value for n*n grid
        grid = new boolean[n][n];   // all false (blocked) by default

        // Initialize Weighted QUF with n*n sites; add 2 extra spots for virtual roots
        weightedQuickUF = new WeightedQuickUnionUF(n*n+2);

    }

    // grid getter
    public boolean[][] getGrid(){
        return grid;
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
    public int xyTo1D(int row, int col){
        return (row-1)*n + col;
    }

    // Throws an exception if the specified site is not valid
    private void throwIfNotValid(int row, int col){
        if (row <= 0 || row > n)
            throw new IndexOutOfBoundsException("Row index i out of bounds");
        if (col <= 0 || col > n)
            throw new IndexOutOfBoundsException("Column index i out of bounds");
    }

    // test client (optional)
    public static void main(String[] args){

    }
}

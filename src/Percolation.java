import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF wquf;

    private boolean[][] grid;
    private boolean percolates;
    private int n;
    private int numOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        this.n = n; // save value for n*n grid
        grid = new boolean[n][n];   // all false (blocked) by default

        // Initialize Weighted QUF with n*n sites; add 2 extra spots for virtual roots
        wquf = new WeightedQuickUnionUF(n*n+2);

    }

    // grid getter
    public boolean[][] getGrid(){
        return grid;
    }

    // roots array getter
    public int[] getRoots(){
        return roots;
    }

    // get specific root
    public int getRoot(int rootId){
        return roots[rootId];
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

    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return numOpenSites;
    }

    // does the system percolate?
    // i.e., is there a full site in the bottom row?
    public boolean percolates(){

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

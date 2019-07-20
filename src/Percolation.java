public class Percolation {
    private boolean[][] grid;
    private boolean percolates;
    private int[] roots;
    private int n;
    private int numOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        this.n = n; // save value for n*n grid
        grid = new boolean[n][n];   // all false (blocked) by default
        roots = new int[n*n];       // n*n array for roots
        // initialize roots[] array so that each square is its own root
        for(int i = 0; i < n*n; i++){
            roots[i] = i;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)

    // is the site (row, col) full?
    public boolean isFull(int row, int col)

    // returns the number of open sites
    public int numberOfOpenSites()

    // does the system percolate?
    public boolean percolates()

    // test client (optional)
    public static void main(String[] args)
}
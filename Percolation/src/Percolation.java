import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // (1,1) 是左上角而非 (0,0)
    private int [][] grid;
    private int Opensize;
    private int size;
    private int top = 0;
    private int bottom;
    private WeightedQuickUnionUF weightedQuickUnionUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        grid = new int[n+1][n+1];
        size = n;
        bottom = n*n+1;

        // cause 1,1 is the 0,0 , we need n*n+1 indexs and 0 is the top and n*n+2 is the bottom
        weightedQuickUnionUF = new WeightedQuickUnionUF(n*n+2);

        // 0 represents close and 1 reprents open
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = 0;
            }
        }
    }


    private void isLegal(int row ,int col){
        if (row <= 0 || col <= 0 || row > size || col > size){
            throw new IllegalArgumentException("IllegalArgumentException");
        }
    }

    private int index(int row,int col){
        return (row - 1) * size + col;  // make (1,1) is 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        isLegal(row,col);

        if (grid[row][col] == 0){
            grid[row][col] = 1;

        // up
        if (row != 1){
            int target = index(row-1,col);
            if (isOpen(row-1,col)) {
                weightedQuickUnionUF.union(index(row, col),target);
            }
        }

        // down
        if (row != size){
            int target = index(row+1,col);
            if (isOpen(row+1,col)){
                weightedQuickUnionUF.union(index(row,col),target);
            }
        }

        // left
        if (col != 1){
            int target = index(row,col-1);
            if (isOpen(row,col-1)){
                weightedQuickUnionUF.union(index(row, col),target);
            }
        }

        //right
        if (col != size){
            int target = index(row,col+1);
            if (isOpen(row,col+1)){
                weightedQuickUnionUF.union(index(row, col),target);
            }
        }

        // top
        if (row == 1){
            weightedQuickUnionUF.union(top,index(row,col));
        }

        // bottom
        if (row == size){
            weightedQuickUnionUF.union(bottom,index(row,col));
        }

        Opensize ++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        isLegal(row,col);
        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        isLegal(row,col);
        if (weightedQuickUnionUF.find(index(row, col)) == weightedQuickUnionUF.find(top) && isOpen(row, col)){
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return Opensize;
    }

    // does the system percolate?
    public boolean percolates(){
        if (weightedQuickUnionUF.find(top) == weightedQuickUnionUF.find(bottom)){
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args){}
}

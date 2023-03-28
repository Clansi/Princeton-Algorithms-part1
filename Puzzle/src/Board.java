import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        this.tiles = tiles;
        n = tiles.length;
    }

    // string representation of this board
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(n);
        res.append("\n ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res.append(tiles[i][j]);
                res.append(" ");
            }
            res.append("\n ");
        }
        return res.toString();
    }

    // board dimension n
    public int dimension(){
        return n;
    }

    // number of tiles out of place
    public int hamming(){
        int num = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != n*i+j+1 && tiles[i][j] != 0){
                    num++;
                }
            }
        }
        return num;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int sumofdistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = tiles[i][j];
                if (value != n*i+j+1 && value != 0){
                    int row = getgoalrow(value);
                    int col = getgoalcol(value,row);
                    sumofdistance += Math.abs(row-i) + Math.abs(col-j);
                }
            }
        }
        return sumofdistance;
    }

    private int getgoalrow(int value) {
        return (int) Math.ceil((double) value/n)-1; // 向上取整.
    }

    private int getgoalcol(int value,int row) {
        return value - n*row -1;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (y instanceof Board){
            Board other = (Board) y;
            if (this.n != other.n) {
                return false;
            }
            else {
            for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (this.tiles[i][j] != other.tiles[i][j]){
                            return false;
                        }
                    }
                }
            }
        }
        else{
            return false;
        }
        return true;
    }
    private int[][] copytile(Board board){
        int[][] copy = new int[board.n][board.n];
        for (int i = 0; i < board.n; i++) {
            for (int j = 0; j < board.n; j++) {
                copy[i][j] = board.tiles[i][j];
            }
        }
        return copy;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> boards = new ArrayList<>();
        int blankrow = 0;
        int blankcol = 0;
        // search for the blank
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankrow = i;
                    blankcol = j;
                }
            }
        }
        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        for (int[] dir : directions) {
            int newrow = blankrow + dir[0];
            int newcol = blankcol + dir[1];
            if (newrow >= 0 && newrow < n && newcol >=0 && newcol < n){  // neighbor exists
                int[][] newtile = copytile(this);
                newtile[blankrow][blankcol] = newtile[newrow][newcol];
                newtile[newrow][newcol] = 0;
                boards.add(new Board(newtile));
            }
        }
            return boards;
    }
    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int [][] newtile = copytile(this);
        int row1 = StdRandom.uniformInt(n);
        int col1 = StdRandom.uniformInt(n);
        int row2 = StdRandom.uniformInt(n);
        int col2 = StdRandom.uniformInt(n);

        while (newtile[row1][col1] == 0 || newtile[row2][col2] == 0 || (row1 == row2 && col1 == col2)){
             row1 = StdRandom.uniformInt(n);
             col1 = StdRandom.uniformInt(n);
             row2 = StdRandom.uniformInt(n);
             col2 = StdRandom.uniformInt(n);
        }

        int temp = newtile[row1][col1];
        newtile[row1][col1] = newtile[row2][col2];
        newtile[row2][col2] = temp;

        return new Board(newtile);
    }

    // unit testing (not graded)
    public static void main(String[] args){

    }

}
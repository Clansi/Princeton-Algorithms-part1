import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        int len = tiles.length;
        this.tiles = new int[len][len];
        for (int i = 0; i < len; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, len);
        }
        n = len;
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
        if (!(y instanceof Board)) {
            return false;
        }
            Board other = (Board) y;
            if (this.n != other.n) {
                return false;
            }
            for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (this.tiles[i][j] != other.tiles[i][j]){
                            return false;
                        }
                    }
                }
        return true;
    }
    private int[][] copytile() {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
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
            if (newrow >= 0 && newrow < n && newcol >= 0 && newcol < n) {  // neighbor exists
                int[][] newtile = copytile();
                newtile[blankrow][blankcol] = newtile[newrow][newcol];
                newtile[newrow][newcol] = 0;
                boards.add(new Board(newtile));
            }
        }
        return boards;
    }
    // a board that is obtained by exchanging any pair of tiles

    // 文档把一个关键点漏了 ，这里twin一定不能是随机的 测试里要看经过若干次twin后 board跟twin后的board是否相同。
    public Board twin() {
        int [][] newtile = copytile();

        int row = 0;
        while (row < n && (newtile[row][0] == 0 || newtile[row][1] == 0)) {
            row++;
        }

        // Swap the first two elements of the row
        int temp = newtile[row][0];
        newtile[row][0] = newtile[row][1];
        newtile[row][1] = temp;

        return new Board(newtile);
    }

    // unit testing (not graded)
    public static void main(String[] args){

    }

}
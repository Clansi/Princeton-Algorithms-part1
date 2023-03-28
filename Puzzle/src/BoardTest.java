public class BoardTest {
    public static void main(String[] args) {
        testToString();
        testEquals();
        testHamming();
        testManhattan();
        testIsGoal();
        testDimension();
        testNeighbors();
        testTwin();
    }

    // 测试toString()方法
    private static void testToString() {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);
        String expected = "3\n 1 2 3 \n 4 5 6 \n 7 8 0 \n";
        String actual = board.toString();
        assert actual.equals(expected) : "toString() method test failed";
    }

    // 测试equals()方法
    private static void testEquals() {
        int[][] blocks1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][] blocks2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][] blocks3 = {{1, 2, 3}, {4, 6, 5}, {7, 8, 0}};
        Board board1 = new Board(blocks1);
        Board board2 = new Board(blocks2);
        Board board3 = new Board(blocks3);
        assert board1.equals(board2) : "equals() method test failed";
        assert !board1.equals(board3) : "equals() method test failed";
    }

    // 测试hamming()方法
    private static void testHamming() {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        int expected = 5;
        int actual = board.hamming();
        assert actual == expected : "hamming() method test failed";
    }

    // 测试manhattan()方法
    private static void testManhattan() {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        int expected = 10;
        int actual = board.manhattan();
        assert actual == expected : "manhattan() method test failed";
    }

    // 测试isGoal()方法
    private static void testIsGoal() {
        int[][] blocks1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][] blocks2 = {{1, 2, 3}, {4, 6, 5}, {7, 8, 0}};
        Board board1 = new Board(blocks1);
        Board board2 = new Board(blocks2);
        assert board1.isGoal() : "isGoal() method test failed";
        assert !board2.isGoal() : "isGoal() method test failed";
    }

    // 测试dimension()方法
    private static void testDimension() {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Board board = new Board(blocks);
        int expected = 3;
        int actual = board.dimension();
        assert actual == expected : "dimension() method test failed";
    }

    // 测试neighbors()方法
    private static void testNeighbors() {
        int[][] blocks = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Board board = new Board(blocks);
        Iterable<Board> neighbors = board.neighbors();
        int count = 0;
        for (Board neighbor : neighbors) {
            count++;
        }
        assert count == 4 : "neighbors() method test failed";
    }

    // 测试twin()方法
    private static void testTwin() {
        int[][] blocks = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Board board = new Board(blocks);
        Board twin = board.twin();
        assert !board.equals(twin) : "twin() method test failed";
    }
}
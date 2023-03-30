import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
    private static class State implements Comparable<State>{
        private int moves;
        private int manhattan;
        private Board board;
        private State lastState;
        private int priority;


        // create initial state
        public State(Board initialboard){
            moves = 0;
            manhattan = initialboard.manhattan();
            board = initialboard;
            lastState = null;
            priority = priority();
        }

        // create state commonly
        public State(State lastState,Board thisboard){
            this.lastState = lastState;
            this.board = thisboard;
            this.manhattan = board.manhattan();
            this.moves = lastState.moves + 1;
            this.priority = priority();
        }

        public int priority() {
            this.priority = moves + manhattan;
            return priority;
        }

        public int compareTo(State that) {
            return Integer.compare(this.priority(), that.priority());
        }

    }



    private List <Board> solveBoard = new ArrayList<>();
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if (initial == null){
            throw new IllegalArgumentException("intial board is null");
        }


        MinPQ<State> pq = new MinPQ<>();
        MinPQ<State> twinpq = new MinPQ<>();

        State InitialState = new State(initial);
        State twinInitialState = new State(initial.twin());

        pq.insert(InitialState);
        twinpq.insert(twinInitialState);

        while (true){

            // for real
            State laststate = pq.delMin();
            if (laststate.board.isGoal()){
                solveBoard = getSolveBoard(laststate);
                break;
            }

            for (Board i : laststate.board.neighbors()){
                State newstate = new State(laststate,i);

                // first optimization : judge if board is same.
                if (laststate.lastState == null || !newstate.board.equals(laststate.lastState.board) ) {
                    pq.insert(newstate);
                }
            }


            // for twin
            State twinlaststate = twinpq.delMin();
            if (twinlaststate.board.isGoal()){
                break;
            }

            for (Board i : twinlaststate.board.neighbors()){
                State twinnewstate = new State(twinlaststate,i);

                // first optimization : judge if board is same.
                if (twinlaststate.lastState == null || !twinnewstate.board.equals(twinlaststate.lastState.board)) {
                    twinpq.insert(twinnewstate);
                }
            }

        }

    }

    private List<Board> getSolveBoard(State goalstate){
        List<Board> solveboard = new ArrayList<>();
        State state = goalstate;
        while (state.moves != 0){
            solveboard.add(state.board);
            state = state.lastState;
        }
        solveboard.add(state.board);
        Collections.reverse(solveboard);
        return solveboard;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return solveBoard.size() != 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if (isSolvable()){
            return solveBoard.size() - 1;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if (isSolvable()) {
            return solveBoard;
        }
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        int[][] tiles;
        tiles = new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};

        Board initial = new Board(tiles);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int size = 1;
        String champion = ""; // result
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (StdRandom.bernoulli(1 / size)) {
                champion = input;
            }
            size++;
        }

        StdOut.println(champion);
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private int number;
    private List<LineSegment> Collinearsegments = new ArrayList<>();
    public BruteCollinearPoints(Point[] points){    // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("argument to constructor is null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("one point is null");
            }
        }
        int len = points.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("repeated point");
                }
            }
        }
        if (len < 4) {
            return;
        }

        Point[] points1 = Arrays.copyOf(points,len);
        Arrays.sort(points1);
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len ; j++) {
                for (int k = j+1; k < len ; k++) {
                    for (int l = k+1; l < len; l++) {
                        Point comparpoint = points1[i];
                        double slope1 = comparpoint.slopeTo(points1[j]);
                        double slope2 = comparpoint.slopeTo(points1[k]);
                        double slope3 = comparpoint.slopeTo(points1[l]);
                        if ((slope1 == slope2) && (slope1 == slope3)){
                            Collinearsegments.add(new LineSegment(comparpoint,points1[l]));
                            number ++;
                        }
                    }
                }
            }
        }

    }
    public int numberOfSegments() {        // the number of line segments
        return number;
    }
    public LineSegment[] segments() {                // the line segments
        LineSegment[] segments = new LineSegment[numberOfSegments()];
        for (int i = 0; i < numberOfSegments(); i++) {
            segments[i] = Collinearsegments.get(i);
        }
        return segments;
    }
}

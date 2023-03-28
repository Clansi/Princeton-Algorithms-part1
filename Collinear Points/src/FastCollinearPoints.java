import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class FastCollinearPoints {

    private List<LineSegment> Collinearsegments = new ArrayList<>();
    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
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




        Point[] points1 = Arrays.copyOf(points, len);
        for (Point i : points) {
            Arrays.sort(points1, i.slopeOrder());    // 对points 以slope进行排序
            for (int j = 1; j < points1.length;) {
                int k = j+1;
                while (k < points1.length && i.slopeTo(points1[j]) == i.slopeTo(points1[k])){
                    k++;
                }
                if (k-j >= 3 && i.compareTo(min(points1,j,k-1)) < 0){        // 保证i是线段中最小的点
                    Collinearsegments.add(new LineSegment(i,max(points1,j,k-1)));  // 找到线段中最大的点
                }
                j = k;
                if (j == points1.length) break;
            }
        }
    }


    private Point min(Point[] points,int i,int j){
        Point min = points[i];
        for (int x = i+1; x <= j ;x++){
            if (min.compareTo(points[x]) > 0){
                min = points[x];
            }
        }
        return min;
    }


    private Point max(Point[] points,int i,int j){
        Point max = points[i];
        for (int x = i+1; x <= j ;x++){
            if (max.compareTo(points[x]) < 0){
                max = points[x];
            }
        }
        return max;
    }
    public           int numberOfSegments() {        // the number of line segments
        return Collinearsegments.size();
    }
    public LineSegment[] segments() {                // the line segments
        LineSegment[] segments = new LineSegment[numberOfSegments()];


        for (int i = 0; i < numberOfSegments(); i++) {
            segments[i] = Collinearsegments.get(i);
        }
        return segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        int n = 5;
        Point[] points = new Point[n];
        points[0] = new Point(4,4);
        points[1] = new Point(1,1);
        points[2] = new Point(2,2);
        points[3] = new Point(3,3);
        points[4] = new Point(0,0);


        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
        StdOut.println(collinear.Collinearsegments.size());
    }
}

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

/*
 * inputs that will work with BruteCollinearPoints: input8.txt and input40.txt
 */
/**
 *
 * @author bruceoutdoors
 */
public class BruteCollinearPoints {

    private LineSegment[] segmentsArr;

    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        for (Point p : points) {
            if (p == null) {
                throw new java.lang.NullPointerException();
            }
        }
        
        Point[] pointsCopy = points.clone(); // another points array

        ArrayList<LineSegment> segs = new ArrayList<>();
        Quick.sort(pointsCopy);

        // all points must be unique:
        for (int i = 0; i < pointsCopy.length - 1; i++) { // (n-1)*n compares
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        // compare the slope, choose the segment
        for (int i = 0; i < pointsCopy.length; i++) {
            for (int j = 1; j < pointsCopy.length; j++) {
                for (int k = 2; k < pointsCopy.length; k++) {
                    for (int l = 3; l < pointsCopy.length; l++) {
                        // with a mess choose, we get p1, p2, p3 and p4
                        if (i < j && j < k && k < l) {
                            Point p1 = pointsCopy[i];
                            Point p2 = pointsCopy[j];
                            Point p3 = pointsCopy[k];
                            Point p4 = pointsCopy[l];

                            double s1 = p1.slopeTo(p2);
                            if (p1.slopeTo(p3) == s1
                                    && p1.slopeTo(p4) == s1) {
                                segs.add(new LineSegment(p1, p4)); // if the three slopes are the same, add new LineSegment
                                                                   // note that two points specify one segment
                            }
                        }
                    }
                }
            }
        }

        segmentsArr = segs.toArray(new LineSegment[segs.size()]); // segmentsArr for number of segments
    }

    public int numberOfSegments() // the number of line segments
    {
        return segmentsArr.length;
    }

    public LineSegment[] segments() // the line segments
    {
        return segmentsArr.clone(); // all the segments
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}

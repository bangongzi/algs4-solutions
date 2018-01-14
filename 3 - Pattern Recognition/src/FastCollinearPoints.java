
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bruceoutdoors
 */
public class FastCollinearPoints {

    private LineSegment[] segmentsArr;

    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
    {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        for (Point p : points) {
            if (p == null) {
                throw new java.lang.NullPointerException();
            }
        }

        Point[] pointsCopy = points.clone();

        ArrayList<LineSegment> segs = new ArrayList<>();
        Quick.sort(pointsCopy); // by the compareTo() function, I guess

        // all points must be unique:
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        // Hashmap used to solve maximal line. Can't use this because grader
        // forbids use of hashcode()
        HashMap<Point, ArrayList<Double>> pointToSlopes = new HashMap<>();

        for (int i = 0; i < pointsCopy.length - 1; i++) { // 'i' is the pointer of origin point
            Point origin = pointsCopy[i];
            ArrayList<Point> pts = new ArrayList<>();

            for (int j = i + 1; j < pointsCopy.length; j++) {
                pts.add(pointsCopy[j]);
            } //copy everything except for the origin

            Point[] ptsArr = pts.toArray(new Point[pts.size()]);
            MergeX.sort(ptsArr, origin.slopeOrder());

            /*// print all slopes
            for (int j = 0; j < ptsArr.length; j++) {
                Double s1 = origin.slopeTo(ptsArr[j]);
                StdOut.println(s1);
            } StdOut.println("==="); */

            // find all the required segments with the base of the origin
            int j = 0;
            while (j < ptsArr.length - 2) { // 'j' is the local finder
                double s1 = origin.slopeTo(ptsArr[j]);
                int k = j + 1; // k is the helper of 'j'
                int counter = 1;
                while (origin.slopeTo(ptsArr[k]) == s1) {
                    counter++;
                    k++;
                    if ( k >= ptsArr.length) {
                        break;
                    } // make sure k won't go beyond the length of the array
                } // find the adjacent points that have equal slopes

                if (counter >= 3) {
                    boolean exHas = false;
                    for(int ex = 0; ex < i; ex++) {
                        if(s1 == pointsCopy[ex].slopeTo(pointsCopy[i])) {
                            exHas = true;
                            break;
                        }
                    }
                    if(!exHas) {
                        segs.add(new LineSegment(origin, ptsArr[k-1]));
                    }
                } // if counter >= 3, we have a required line segment
                j = k; // continue to find next adjacent couples
            }
        }
        segmentsArr = segs.toArray(new LineSegment[segs.size()]);
    }

    public int numberOfSegments() // the number of line segments
    {
        return segmentsArr.length;
    }

    public LineSegment[] segments() // the line segments
    {
        return segmentsArr.clone();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}

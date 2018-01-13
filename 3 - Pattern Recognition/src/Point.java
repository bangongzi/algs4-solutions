
/**
 * ****************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 *With reader's comments
 *****************************************************************************
 */
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Objects;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y); // draw the point in some figure, I guess
//        StdDraw.filledCircle(x, y, 150);
    }

    /**
     * Draws the line segment between this point and the specified point to
     * standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally,
     * if the two points are (x0, y0) and (x1, y1), then the slope is (y1 - y0)
     * / (x1 - x0). For completeness, the slope is defined to be +0.0 if the
     * line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical; and
     * Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that.x == this.x && that.y == this.y) {
            return Double.NEGATIVE_INFINITY;
        } //Meet the same point
        
        if (that.y == this.y) {
            return 0;
        } // horizontal

        if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        } // vertical

        return (double) (that.y - this.y) / (double) (that.x - this.x); // normal situation
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point
     * (x0 = x1 and y0 = y1); a negative integer if this point is less than the
     * argument point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (that.x == this.x && that.y == this.y) {
            return 0;
        }

        if (this.y < that.y
                || (this.y == that.y && this.x < that.x)) {
            return -1;
        }

        return 1; // Brilliant, the last situation do not need a judge sentence
    }

    /**
     * Compares two points by the slope they make with this point. The slope is
     * defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        Point dis = this;
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                double s1 = o1.slopeTo(dis); // o1.slopeTo(dis) = dis.slopeTo(o1)
                double s2 = o2.slopeTo(dis);

                if (Objects.equals(s1, s2)
                        || Math.abs(s1 - s2) < 0.000001) {
                    return 0;
                }

                if (o1.slopeTo(dis) < o2.slopeTo(dis)) {
                    return -1;
                }

                return 1;
            }
        };
    }

    /**
     * Returns a string representation of this point. This method is provide for
     * debugging; your program should not rely on the format of the string
     * representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point a = new Point(0, 0);
        Point b = new Point(1, 2);
        Point c = new Point(1, 2);
        Point d = new Point(1, 0);
        Point e = new Point(2, 2);

        assert a.slopeTo(b) == 2;
        assert b.slopeTo(c) == Double.NEGATIVE_INFINITY;
        assert c.slopeTo(d) == Double.POSITIVE_INFINITY;
        assert a.slopeTo(d) == 0;

        assert b.compareTo(c) == 0;
        assert a.compareTo(b) < 0;
        assert c.compareTo(e) < 0;
        assert b.compareTo(a) > 0;

        Point p = new Point(5, 1);
        Point q = new Point(4, 1);
        Point r = new Point(9, 1);
        assert p.slopeOrder().compare(q, r) == 0;
    }
}

package week3;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x; // x-coordinate of this point
    private final int y; // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException("point can't be a null");
        final int dx = that.x - this.x;
        final int dy = that.y - this.y;
        if (dy == 0 && dx == 0) return Double.NEGATIVE_INFINITY;
        else if (dx == 0) return Double.POSITIVE_INFINITY;
        else if (dy == 0) return +0.0d;
        else return (double) dy / dx;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    @Override
    public int compareTo(Point that) {
        if (that.y > this.y || (that.y == this.y && that.x > this.x)) return -1;
        else if (that.y == this.y && that.x == this.x) return 0;
        else return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (p1, p2) -> {
            final double firstSlope = slopeTo(p1);
            final double secondSlope = slopeTo(p2);
            if ((firstSlope == Double.POSITIVE_INFINITY && secondSlope == Double.POSITIVE_INFINITY) ||
                    (firstSlope == Double.NEGATIVE_INFINITY && secondSlope == Double.NEGATIVE_INFINITY)) return 0;
            final double diff = firstSlope - secondSlope;
            if (Math.abs(diff) < 1e-10) return 0;
            else if (diff < 0) return -1;
            else return 1;
        };
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    public static void main(String[] args) {
        Point y1 = new Point(3864, 14430);
        Point y2 = new Point(19026, 9244);
        Point y3 = new Point(9392, 9244);
        Point y4 = new Point(6683, 14703);
        Point y5 = new Point(13335, 10324);
        Point y6 = new Point(14191, 9244);
        Point y7 = new Point(3061, 14430);
        Point y8 = new Point(17401, 14703);
        Point y9 = new Point(16399, 14430);
        Point y10 = new Point(3586, 2917);
        Point y11 = new Point(3641, 14703);
        Point y12 = new Point(17507, 10324);
        Point y13 = new Point(18754, 10324);
        Point y14 = new Point(10982, 14430);
        Point y15 = new Point(10892, 2917);
        Point y16 = new Point(13995, 10324);
        Point y17 = new Point(14223, 14703);
        Point y18 = new Point(10479, 2917);
        Point y19 = new Point(5510, 9244);
        Point y20 = new Point(7309, 2917);
        Point[] points = {y1, y2, y3, y4, y5, y6, y7, y8, y9, y10, y11, y12, y13, y14, y15, y16, y17, y18, y19, y20};
        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        for (LineSegment segment : fastCollinearPoints.segments()) {
            System.out.println(segment);
        }
    }

}

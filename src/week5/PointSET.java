package week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    private Set<Point2D> bst;

    // construct an empty set of points
    public PointSET() {
        bst = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // number of points in the set
    public int size() {
        return bst.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("point is null");
        if (!contains(p)) bst.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("point is null");
        return bst.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : bst) {
            point.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("rectangle is null");
        List<Point2D> pointsInRange = new ArrayList<>(bst.size());
        for (Point2D point : bst) {
            if (rect.contains(point)) pointsInRange.add(point);
        }
        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("point is null");
        Point2D nearest = null;
        for (Point2D point : bst) {
            if (nearest == null) nearest = point;
            else if (point.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) nearest = point;
        }
        return nearest;
    }

}

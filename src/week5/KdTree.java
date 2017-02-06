package week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node root;
    private int treeSize;

    private static class Node {
        private Point2D point;      // the point
        private RectHV rect;        // the axis-aligned rectangle corresponding to this node
        private Node lb;            // the left/bottom subtree
        private Node rt;            // the right/top subtree
        private boolean isVertical;

        private Node(Point2D point, RectHV rect, boolean isVertical) {
            this.point = point;
            this.rect = rect;
            this.lb = null;
            this.rt = null;
            this.isVertical = isVertical;
        }
    }

    public KdTree() {
        root = null;
        treeSize = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return treeSize == 0;
    }

    // number of points in the set
    public int size() {
        return treeSize;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D point) {
        if (point == null) throw new NullPointerException("point is null");
        if (isEmpty()) {
            treeSize++;
            root = new Node(point, new RectHV(0.0, 0.0, 1.0, 1.0), true);
        }
        if (point.x() == root.point.x() && point.y() == root.point.y()) return;
        if (point.x() < root.point.x()) root.lb = put(root.lb, point, root);
        else root.rt = put(root.rt, point, root);
    }

    private Node put(Node node, Point2D point, Node prevNode) {

        if (node == null) {
            RectHV rect;
            treeSize++;
            if (prevNode.isVertical) {
                if (point.x() < prevNode.point.x()) {
                    rect = new RectHV(
                            prevNode.rect.xmin(), prevNode.rect.ymin(),
                            prevNode.point.x(), prevNode.rect.ymax()
                    );
                } else {
                    rect = new RectHV(
                            prevNode.point.x(), prevNode.rect.ymin(),
                            prevNode.rect.xmax(), prevNode.rect.ymax()
                    );
                }
            } else {
                if (point.y() < prevNode.point.y()) rect = new RectHV(
                        prevNode.rect.xmin(), prevNode.rect.ymin(),
                        prevNode.rect.xmax(), prevNode.point.y()
                );
                else rect = new RectHV(
                        prevNode.rect.xmin(), prevNode.point.y(),
                        prevNode.rect.xmax(), prevNode.rect.ymax()
                );
            }
            return new Node(point, rect, !prevNode.isVertical);
        }

        if (node.point.x() == point.x() && node.point.y() == point.y()) return node;
        else if (node.isVertical) {
            if (point.x() - node.point.x() < 0) node.lb = put(node.lb, point, node);
            else node.rt = put(node.rt, point, node);
        } else {
            if (point.y() - node.point.y() < 0) node.lb = put(node.lb, point, node);
            else node.rt = put(node.rt, point, node);
        }
        return node;

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("point is null");
        else return !isEmpty() && get(root, p) != null;
    }

    private Node get(Node node, Point2D point) {
        if (node == null) return null;
        else if (node.point.x() == point.x() && node.point.y() == point.y()) return node;
        else if (node.isVertical) {
            if (point.x() - node.point.x() < 0) return get(node.lb, point);
            else return get(node.rt, point);
        } else {
            if (point.y() - node.point.y() < 0) return get(node.lb, point);
            else return get(node.rt, point);
        }
    }

    // draw all points to standard draw
    public void draw() {
        drawRectsAndPoints(root);
    }

    private void drawRectsAndPoints(Node node) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.point.draw();
            StdDraw.setPenRadius(0.001);
            if (node.isVertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
                drawRectsAndPoints(node.lb);
                drawRectsAndPoints(node.rt);
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
                drawRectsAndPoints(node.lb);
                drawRectsAndPoints(node.rt);
            }
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("rectangle is null");
        List<Point2D> pointsInRange = new ArrayList<>(size());
        findPointsInRange(root, rect, pointsInRange);
        return pointsInRange;
    }

    private void findPointsInRange(Node node, RectHV rect, List<Point2D> list) {
        if (node != null) {
            if (rect.contains(node.point)) list.add(node.point);
            if (rect.intersects(node.rect)) {
                findPointsInRange(node.lb, rect, list);
                findPointsInRange(node.rt, rect, list);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("point is null");
        if (root == null) return null;
        return findNearestPoint(root, p, root.point);
    }

    private Point2D findNearestPoint(Node node, Point2D targetPoint, Point2D nearestPoint) {
        if (node == null) return nearestPoint;
        if (node.point.distanceSquaredTo(targetPoint) < nearestPoint.distanceSquaredTo(targetPoint)) {
            nearestPoint = node.point;
        }
        if (node.rect.distanceSquaredTo(targetPoint) < nearestPoint.distanceSquaredTo(targetPoint)) {
            if ((node.isVertical && targetPoint.x() < node.point.x()) ||
                    (!node.isVertical && targetPoint.y() < node.point.y())) {
                nearestPoint = findNearestPoint(node.lb, targetPoint, nearestPoint);
                nearestPoint = findNearestPoint(node.rt, targetPoint, nearestPoint);
            } else {
                nearestPoint = findNearestPoint(node.rt, targetPoint, nearestPoint);
                nearestPoint = findNearestPoint(node.lb, targetPoint, nearestPoint);
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.1, 0.3));
        kdTree.insert(new Point2D(0.1312, 0.45));
        kdTree.insert(new Point2D(0.0311, 0.8));
        kdTree.insert(new Point2D(0.321, 0.983));
        kdTree.insert(new Point2D(0.13, 0.13));
        kdTree.insert(new Point2D(0.54, 0.532));
        kdTree.insert(new Point2D(0.523, 0.35));
        kdTree.insert(new Point2D(0.523, 0.6456));
        kdTree.insert(new Point2D(0.523, 0.6436));
        kdTree.insert(new Point2D(0.23, 0.36));
        kdTree.insert(new Point2D(0.643, 0.123));
        System.out.println(kdTree.range(new RectHV(0.0, 0.0, 0.156, 0.421)));
        System.out.println(kdTree.nearest(new Point2D(0.32, 0.634)));
    }

}

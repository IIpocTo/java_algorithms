package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("points array can't be a null");
        lineSegments = new ArrayList<>();
        if (points[0] == null) throw new NullPointerException("point can't be a null");
        for (int i = 0; i < points.length; i++) {
            Point[] sortedPoints = points.clone();
            Arrays.sort(sortedPoints, i + 1, points.length,  points[i].slopeOrder());
            for (int j = i + 1; j < points.length - 2;) {
                final double slopeToFirst = points[i].slopeTo(sortedPoints[j]);
                final double slopeToSecond = points[i].slopeTo(sortedPoints[j + 1]);
                final double slopeToThird = points[i].slopeTo(sortedPoints[j + 2]);
                if (slopeToFirst == Double.NEGATIVE_INFINITY || slopeToSecond == Double.NEGATIVE_INFINITY ||
                        slopeToThird == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException("duplicate points");
                else if ((slopeToFirst == slopeToSecond) && (slopeToFirst == slopeToThird)) {
                    List<Point> collinearPoints = new ArrayList<>();
                    collinearPoints.add(sortedPoints[i]);
                    collinearPoints.add(sortedPoints[j]);
                    collinearPoints.add(sortedPoints[j + 1]);
                    collinearPoints.add(sortedPoints[j + 2]);
                    if (j + 3 < points.length) {
                        for (int k = j + 3; k < points.length; k++) {
                            j = k;
                            if (slopeToFirst == points[i].slopeTo(sortedPoints[k])) {
                                collinearPoints.add(sortedPoints[k]);
                            } else break;
                        }
                    } else j++;
                    Collections.sort(collinearPoints);
                    final LineSegment lineSegment =
                            new LineSegment(collinearPoints.get(0), collinearPoints.get(collinearPoints.size() - 1));
                    lineSegments.add(lineSegment);
                } else j++;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

}

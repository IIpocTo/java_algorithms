package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("points array can't be a null");
        lineSegments = new ArrayList<>();
        if (points[0] == null) throw new NullPointerException("point can't be a null");
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                final double slopeIToJ = points[i].slopeTo(points[j]);
                if (slopeIToJ == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException("duplicate points");
                for (int k = j + 1; k < points.length; k++) {
                    final double slopeIToK = points[i].slopeTo(points[k]);
                    final double slopeJToK = points[j].slopeTo(points[k]);
                    if (slopeIToK == Double.NEGATIVE_INFINITY || slopeJToK == Double.NEGATIVE_INFINITY)
                        throw new IllegalArgumentException("duplicate points");
                    for (int l = k + 1; l < points.length; l++) {
                        final double slopeIToL = points[i].slopeTo(points[l]);
                        final double slopeJToL = points[j].slopeTo(points[l]);
                        final double slopeKToL = points[k].slopeTo(points[l]);
                        if (slopeIToL == Double.NEGATIVE_INFINITY || slopeJToL == Double.NEGATIVE_INFINITY ||
                                slopeKToL == Double.NEGATIVE_INFINITY)
                            throw new IllegalArgumentException("duplicate points");
                        if (slopeIToJ == slopeIToK && slopeIToJ == slopeIToL) {
                            Point[] collinearPoints = { points[i], points[j], points[k], points[l] };
                            Arrays.sort(collinearPoints);
                            final LineSegment lineSegment = new LineSegment(collinearPoints[0], collinearPoints[3]);
                            lineSegments.add(lineSegment);
                        }
                    }
                }
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

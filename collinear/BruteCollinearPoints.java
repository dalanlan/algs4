/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints [input-file]
 *  Dependencies: Point.java; LineSegment.java
 *  
 ******************************************************************************/
import java.util.Arrays;
import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    
    private int numberOfSegments = 0;
    private LineSegment[] lineSegment;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("null arguement");
        
        // sort to detect duplicate elements
        

        int sizeOfPoints = points.length;
        Point[] pointsTmp = new Point[sizeOfPoints];
        for (int n = 0; n < sizeOfPoints; n++) {
            if (points[n] == null) 
                throw new NullPointerException("null arguement");
            pointsTmp[n] = points[n];
        }
//        
//        for (Point p : pointsTmp) {
//            StdOut.println(p.toString());
//        }
        
        Arrays.sort(pointsTmp);
        LineSegment[] lineSegmentTmp = new LineSegment[sizeOfPoints*
                                                       (sizeOfPoints-1)/2];
        double res1, res2, res3; //res1: [i,j]; res2:[i,k]; res3:[i,m];
        int i, j, k, m;
        
        for (i = 0; i < sizeOfPoints; i++) { // points[i]
            for (j = i + 1; j < sizeOfPoints; j++) {
                res1 = pointsTmp[i].slopeTo(pointsTmp[j]);
                if (res1 == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("repeated point");
                }
                for (k = j + 1; k < sizeOfPoints; k++) {
                    res2 = pointsTmp[i].slopeTo(pointsTmp[k]);
                    if (res2 == Double.NEGATIVE_INFINITY) {
                        throw new IllegalArgumentException("repeated point");
                    }
//                    if (res1 != res2)
//                        continue;
                    for (m = k + 1; m < sizeOfPoints; m++) {
                        
                        res3 = pointsTmp[i].slopeTo(pointsTmp[m]);
                        if (res3 == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException("repeated point");
                        }
                        
                                           
//                            StdOut.println(points[i].toString());
//                            StdOut.println(points[j].toString());
//                            StdOut.println(points[k].toString());
//                            StdOut.println(points[m].toString());
                        if (res1 == res2 && res1 == res3) {
//                            StdOut.println(pointsTmp[i]+"->"+pointsTmp[j]+"->"
//                            +pointsTmp[k]+"->"+pointsTmp[m]);
//                            StdOut.println(numberOfSegments);
                            lineSegmentTmp[numberOfSegments] = new 
                                LineSegment(pointsTmp[i], pointsTmp[m]);
                            numberOfSegments++;
                        }
                    }
                }
            }
        }
        lineSegment = new LineSegment[numberOfSegments];
        for (int pos = 0; pos < numberOfSegments; pos++) {
            lineSegment[pos] = lineSegmentTmp[pos];
        }
        
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }
    
    // the line segments
    public LineSegment[] segments() {
        return lineSegment.clone();
    }
    
    public static void main(String[] args) {

    // read the N points from a file
    In in = new In(args[0]);
    int N = in.readInt();
    
    Point[] points = new Point[N];
    for (int i = 0; i < N; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
        
    }
    
    // draw the points
    StdDraw.show(0);
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);

    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    
    //StdOut.println("number of segments is " + collinear.numberOfSegments);
    
    for (int i = 0; i < collinear.numberOfSegments(); i++) {
//        StdOut.println(segment);
//        segment.draw();
        LineSegment[] newSeg = collinear.segments();
        newSeg[i] = new LineSegment(points[0], points[1]);
    }
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        segment.draw();
        }
    
}
}
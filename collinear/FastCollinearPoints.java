/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints [input-file]
 *  Dependencies: Point.java; LineSegment.java
 *  
 ******************************************************************************/
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    int numberOfSegments = 0;
    LineSegment[] segments;
    
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("the arguement of constructor is null");
        Arrays.sort(points);
        
        int lengthOfPoints = points.length;
        Point[] pointsTmp = new Point[lengthOfPoints];
        
        LineSegment[] segmentsTmp = new LineSegment[lengthOfPoints*(lengthOfPoints-1)/2];
        
        for (int i = 0; i < lengthOfPoints; i++) {
            pointsTmp[i] = points[i];
        }
        for(int i = 0; i < lengthOfPoints; i++) {
            Arrays.sort(pointsTmp, points[i].slopeOrder());
            for(int j = 0; j < lengthOfPoints-2; j++) {
                // pointsTmp[j], pointsTmp[j+1], pointsTmp[j+2] -- points[i]
                double res1 = points[i].slopeTo(pointsTmp[j]); 
                if (res1 == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("the constructor contains a repeated point");
                }
                int pos = 1;
                double res2 = points[i].slopeTo(pointsTmp[j+pos]); // pointsTmp[j+1]
                if (res2 == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("the constructor contains a repeated point");
                }
                if (res1 != res2) {
                    continue;
                }
                pos++; //pos = 2
                res2 = points[i].slopeTo(pointsTmp[j+pos]); //pointsTmp[j+2]
                if (res2 == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("the constructor contains a repeated point");
                }
                if (res1 != res2) {
                    continue;
                }
                
                pos++;
                while (j + pos < lengthOfPoints) {
                    res2 = points[i].slopeTo(pointsTmp[j+pos]); //pointsTmp[j+3]
                    if (res2 == Double.NEGATIVE_INFINITY) {
                        throw new IllegalArgumentException("the constructor contains a repeated point");
                    }
                    if (res1 != res2) {
                        break;
                    }
                    pos++;
                }
                
                segmentsTmp[numberOfSegments] = new LineSegment(points[i],points[j+pos]);
                numberOfSegments++;
                }
        }
        segments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = segmentsTmp[i];
        }
   
        
    
    }
    public int numberOfSegments() {
        return numberOfSegments;
    }
    
    public LineSegment[] segments() {
        return segments;
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    
    //StdOut.println("number of segments is " + collinear.numberOfSegments);
    
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
}
}
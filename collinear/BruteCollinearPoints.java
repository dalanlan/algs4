/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints [input-file]
 *  Dependencies: Point.java; LineSegment.java
 *  
 ******************************************************************************/
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    
    int numberOfSegments = 0;
    LineSegment[] lineSegment;
    
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("the arguement of constructor is null");
        
        // sort to detect duplicate elements
        Arrays.sort(points);
//        for (Point p : points) {
//            StdOut.println(p.toString());
//        }
        int sizeOfPoints = points.length;
       
        LineSegment[] lineSegmentTmp = new LineSegment[sizeOfPoints*(sizeOfPoints-1)/2];
        double res1, res2, res3; //res1: [i,j]; res2:[i,k]; res3:[i,m];
        int i,j,k,m;
        for (i = 0; i < sizeOfPoints - 3; i++) { // points[i]
            if (points[i] == null)
                throw new NullPointerException("the arguement of constructor is null");
            for (j = i + 1; j < sizeOfPoints - 2; j++ ) {
                if (points[j] == null)
                    throw new NullPointerException("the arguement of constructor is null");
                
                res1 = points[i].slopeTo(points[j]);
                if (res1 == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("the constructor contains a repeated point");
                }
                for (k = j + 1; k < sizeOfPoints - 1;k++) {
                    if (points[k] == null)
                        throw new NullPointerException("the arguement of constructor is null");
                    
                    res2 = points[i].slopeTo(points[k]);
                    if (res2 == Double.NEGATIVE_INFINITY) {
                        throw new IllegalArgumentException("the constructor contains a repeated point");
                    }
                    if (res1 != res2)
                        continue;
                    for (m = k + 1; m < sizeOfPoints; m++) {
                        if (points[m] == null)
                            throw new NullPointerException("the arguement of constructor is null");
                        
                        res3 = points[i].slopeTo(points[m]);
                        if (res3 == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException("the constructor contains a repeated point");
                        }
                        
                        if (res1 == res3) {
//                            StdOut.println("res1:"+res1+",res2:"+res2+",res3:"+res3);
//                            StdOut.println(points[i].toString());
//                            StdOut.println(points[j].toString());
//                            StdOut.println(points[k].toString());
//                            StdOut.println(points[m].toString());
//                            StdOut.println(numberOfSegments);
                            lineSegmentTmp[numberOfSegments] = new LineSegment(points[i], points[m]);
                            numberOfSegments++;
                        }
                    }
                }
            }
        }
        lineSegment = new LineSegment[numberOfSegments];
        for(int pos = 0; pos < numberOfSegments; pos++) {
            lineSegment[pos] = lineSegmentTmp[pos];
        }
        
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }
    
    // the line segments
    public LineSegment[] segments() {
        return lineSegment;
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
    
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
}
}
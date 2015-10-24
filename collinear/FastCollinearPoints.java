/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints [input-file]
 *  Dependencies: Point.java; LineSegment.java
 *  
 ******************************************************************************/
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] segments;
    
    
    private HashMap<Double, ArrayList<Point>> collection;
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("null arguement");
        
        int lengthOfPoints = points.length;  
        Point[] pointsClone = new Point[lengthOfPoints];
        for (int n = 0; n < lengthOfPoints; n++) {
            if (points[n] == null) 
                throw new NullPointerException("null arguement");
//            for (int m = n + 1; m < lengthOfPoints; m++) {
//                if (points[n].compareTo(points[m]) == 0) {
//                    throw new IllegalArgumentException("repeated arguement");
//                }      
//            }
            pointsClone[n] = points[n];
        }
        
        collection = new HashMap<Double, ArrayList<Point>>();
        Arrays.sort(pointsClone);
        for (int n = 0; n < lengthOfPoints - 1; n++) {
            if (pointsClone[n].compareTo(pointsClone[n+1]) == 0) {
                throw new IllegalArgumentException("repeated arguement");
            }
        }
             
        LineSegment[] segmentsTmp = new 
            LineSegment[lengthOfPoints*(lengthOfPoints-1)/2];
        
        
        for (int i = 0; i < lengthOfPoints; i++) {
            
            Point[] pointsTmp = new Point[lengthOfPoints-(i+1)];
            for (int pos = i+1; pos < lengthOfPoints; pos++) {
                pointsTmp[pos-(i+1)] = pointsClone[pos];
            }
            Arrays.sort(pointsTmp, pointsClone[i].slopeOrder());
            
            for (int j = 0; j < lengthOfPoints-i-1; j++) {
                // pointsTmp[j], pointsTmp[j+1], pointsTmp[j+2] -- points[i]
                double res1 = pointsClone[i].slopeTo(pointsTmp[j]); 
                //StdOut.println("res1:"+res1);
                
                int pos = 1;
                if (j+pos >= lengthOfPoints-i-1) {
                    continue;
                }
                double res2 = pointsClone[i].slopeTo(pointsTmp[j+pos]); 
                
                //StdOut.println("res2:"+res2);
                if (res1 != res2) {
                    continue;
                }
                pos++; //pos = 2
                if (j + pos >= lengthOfPoints-i-1) {
                    continue;
                }
                res2 = pointsClone[i].slopeTo(pointsTmp[j+pos]); //pointsTmp[j+2]
                
//StdOut.println("res2:"+res2);
                
                if (res1 != res2) {
                    continue;
                }
                
                pos++;
                while (j + pos < lengthOfPoints-i-1) {
                    res2 = pointsClone[i].slopeTo(pointsTmp[j+pos]); //pointsTmp[j+3]
                    
                    if (res1 != res2) {
                        break;
                    }
                    pos++;
                }
//                StdOut.println("***************");
//                StdOut.println(points[i].toString());
//                for (int k = 1; k < pos; k++) {
//                    StdOut.println(pointsTmp[j + k].toString());
//                }
//                StdOut.println("***************");
                if (!exist(pointsClone[i], res1)) {
                    segmentsTmp[numberOfSegments] = new 
                        LineSegment(pointsClone[i], pointsTmp[j+pos-1]);
                    numberOfSegments++;
                    addCollection(pointsClone[i], res1);
                    for (int m = 0; m < pos; m++) {
                       addCollection(pointsTmp[j+m], res1);
                    }
                }
            //}
        }
        }
        segments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = segmentsTmp[i];
        }
   
        
    
    }
    private void addCollection(Point p, double res) {
        if (!collection.containsKey(res)) {
            collection.put(res, new ArrayList<Point>());
        }
        collection.get(res).add(p);
    }
    private boolean exist(Point p, double res) {
        if (collection.containsKey(res)) {
            for (Point pEle : collection.get(res)) {
                if (pEle.compareTo(p) == 0) {
                    return true;
                }
        }
        
    }
        return false;
    }
    public int numberOfSegments() {
        return numberOfSegments;
    }
    
    public LineSegment[] segments() {
        return segments.clone();
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
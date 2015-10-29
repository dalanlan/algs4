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
    
    private class Node {
        private Point first;
        private Point last;
        
        public Node(Point first, Point last) {
            this.first = first;
            this.last = last;
        }
    }
    
    private HashMap<Double, ArrayList<Point>> collection;
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("null arguement");
        
        int lengthOfPoints = points.length;  
        Point[] pointsClone = new Point[lengthOfPoints];
        for (int n = 0; n < lengthOfPoints; n++) {
            if (points[n] == null) 
                throw new NullPointerException("null arguement");
            pointsClone[n] = points[n];
        }
        
        collection = new HashMap<Double, ArrayList<Point>>();
        Arrays.sort(pointsClone);
        for (int n = 0; n < lengthOfPoints - 1; n++) {
            if (pointsClone[n].compareTo(pointsClone[n+1]) == 0) {
                throw new IllegalArgumentException("repeated arguement");
            }
        }

        Node[] node = new Node[lengthOfPoints*(lengthOfPoints-1)/2];
        
        Point[] auxPoints = pointsClone.clone();
        for (int i = 0; i < lengthOfPoints; i++) {
            //Arrays.sort(pointsClone);
            Arrays.sort(pointsClone, auxPoints[i].slopeOrder());
            
            int jBegin = 1, jEnd;
            while (jBegin < lengthOfPoints - 2) {
            jEnd = jBegin;
            double res1 = auxPoints[i].slopeTo(pointsClone[jEnd]);
            while (jEnd + 1 < lengthOfPoints && 
                   auxPoints[i].slopeTo(pointsClone[jEnd + 1]) == res1) {
                jEnd++;
            }
            if (jEnd - jBegin > 1) {
                Point firstEnd = auxPoints[i];
                Point lastEnd = auxPoints[i];
                for (int m = jBegin; m <= jEnd; m++) {
                    if (firstEnd.compareTo(pointsClone[m]) > 0) {
                        firstEnd = pointsClone[m];
                    }
                    if (lastEnd.compareTo(pointsClone[m]) < 0) {
                        lastEnd = pointsClone[m];
                    }
                }
                node[numberOfSegments] = new Node(firstEnd, lastEnd);
                numberOfSegments++;
            }
            jBegin = jEnd+1;
            }
            
//            for (int j = 0; j < lengthOfPoints-2; j++) {
//                // pointsTmp[j], pointsTmp[j+1], pointsTmp[j+2] -- points[i]
//                double res1 = auxPoints[i].slopeTo(pointsClone[j]); 
//                //StdOut.println("res1:"+res1);
//                int pos = 1;
//                double res2 = auxPoints[i].slopeTo(pointsClone[j+1]); 
//                
//                //StdOut.println("res2:"+res2);
//                if (res1 != res2) {
//                    continue;
//                }
//                pos++;
//                res2 = auxPoints[i].slopeTo(pointsClone[j+2]); //pointsTmp[j+2]
//                
//                if (res1 != res2) {
//                    continue;
//                }
//                
//                pos++;
//                while (j + pos < lengthOfPoints) {
//                    res2 = auxPoints[i].slopeTo(pointsClone[j+pos]); 
//                    
//                    if (res1 != res2) {
//                        break;
//                    }
//                    pos++;
//                }
////                StdOut.println(auxPoints[i].toString());
////                for (int k = 0; k < pos; k++) {
////                    StdOut.println(pointsClone[j + k].toString());
////                }
////                if (!exist(auxPoints[i], res1)) {
////                    segmentsTmp[numberOfSegments] = new 
////                        LineSegment(auxPoints[i], pointsClone[j+pos-1]);
////                    numberOfSegments++;
////                    addCollection(auxPoints[i], res1);
////                    for (int m = 0; m < pos; m++) {
////                       addCollection(pointsClone[j+m], res1);
////                    }
////                }
//                Point firstEnd = auxPoints[i];
//                Point lastEnd = auxPoints[i];
//                for (int m = 0; m < pos; m++) {
//                    if (firstEnd.compareTo(pointsClone[j+m]) > 0) {
//                        firstEnd = pointsClone[j+m];
//                    }
//                    if (lastEnd.compareTo(pointsClone[j+m]) < 0) {
//                        lastEnd = pointsClone[j+m];
//                    }
//                }
////                segmentsTmp[numberOfSegments] = new LineSegment(auxPoints[i], 
////                                                                endPoint);
//                node[numberOfSegments] = new Node(firstEnd, lastEnd);
////                StdOut.println(auxPoints[i].toString()+"->"+endPoint.toString());
//                numberOfSegments++;
//        }
        }
        sort(node, numberOfSegments);
        
//        for(int w = 0; w < numberOfSegments; w++) {
//            StdOut.println(node[w].first.toString()+"->"+node[w].last.toString());
//        }
        if (numberOfSegments >= 1) {
        int total = 1;
        segments = new LineSegment[numberOfSegments];
        
        Node prev = node[0];
        segments[0] = new LineSegment(node[0].first, node[0].last);
        
        for (int w = 1; w < numberOfSegments; w++) {
            if(prev.first.compareTo(node[w].first) == 0 && 
               prev.last.compareTo(node[w].last) == 0) {
                continue;
            }
            else {
                segments[total++] = new LineSegment(node[w].first, node[w].last);
                prev = node[w];
            }
        }
        numberOfSegments = total;
        }
        else
        {
            numberOfSegments = 0;
        }
        //original solution
//        int total = 0;
//        int[] flag = new int[numberOfSegments];
//        
//        for (int i = 0; i < numberOfSegments; i++) {
//            double res = node[i].first.slopeTo(node[i].last);
//
//            if (!exist(node[i].first, res) && !exist(node[i].last, res)) {
//                total++;
//                addCollection(node[i].first, res);
//                addCollection(node[i].last, res);
//                flag[i] = 1;
//            }
//        }
//
//        segments = new LineSegment[total];
//        int pos = 0;
//        for (int i = 0; i < numberOfSegments; i++) {
//            if (flag[i] == 1)
//                segments[pos++] = new LineSegment(node[i].first, node[i].last);
//           
//        }
//   
//        numberOfSegments = total;
        
        
    
    }
    private void sort(Node[] a, int length) {
        Node[] aux = new Node[length];
        sort(a, aux, 0, length-1);                        
    }
    private void sort(Node[] a, Node[] aux, int lo, int hi) {
        if (hi <= lo)
            return;
        int mid = lo + (hi-lo)/2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }
    private void merge(Node[] a, Node[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i]) < 0) a[k] = aux[j++];
            else a[k] = aux[i++];
  
        }
    }
    private int less(Node p, Node q) {
        return p.first.compareTo(q.first);
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
        return Arrays.copyOf(segments, numberOfSegments);
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
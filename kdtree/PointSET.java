import java.util.TreeSet;
import edu.princeton.cs.algs4.Stack;
//import edu.princeton.cs.algs4.StdDraw; 
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private TreeSet<Point2D> treeSet;
    // construction function: construct an empty
    // set of points 
    public PointSET() {
        treeSet = new TreeSet<Point2D>();
    }
    
    // Is the set empty?
    public boolean isEmpty() {
        return treeSet.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return treeSet.size();
    }
    
    // add the point to the set 
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        treeSet.add(p);
    }
    
    // does the set contain poing p? 
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return treeSet.contains(p);
            
    }
    
    // draw all the points to standard draw
    public void draw() {
        for (Point2D p:treeSet) {
            p.draw();
        }
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        Stack stack = new Stack<Point2D>();
        for (Point2D p:treeSet) {
            if (rect.contains(p)) {
                stack.push(p);
            }
        }
        return stack;
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        Point2D nearest = null;
        double dist = Double.POSITIVE_INFINITY;
        for (Point2D q : treeSet) {
            double tmp = p.distanceSquaredTo(q);
            if (tmp < dist) {
                dist = tmp;
                nearest = q;
            }
        }
        return nearest;
    }
    
    public static void main(String[] args) {
    }
    
    
}
/***************************************************
  * 2d-tree implementation
  * 
 ***************************************************/

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw; 
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
    
public class KdTree {
    
    private class Node {
        private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;
        
        public Node(Point2D p, RectHV initRect) {
            point = p;
            rect = initRect;
            left = null;
            right = null;
            
        }
    }
    
    private Node root;
    private int size;
    private Point2D nearestPoint;
    private double dist;
    
    // empty 
    public KdTree() {
        root = null;
        size = 0;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        boolean isVertical = true;     
        double xMin, xMax, yMin, yMax;
        // kd-Tree is empty
        if (root == null) {
            xMin = 0.0;
            xMax = 1.0;
            yMin = 0.0;
            yMax = 1.0;
            root = new Node(p, new RectHV(xMin, yMin, xMax, yMax));
            size++;
        }
        else {
            Node cur = root;
            while (cur != null && !cur.point.equals(p)) {
                // vertical
                if (isVertical) {
                    yMin = cur.rect.ymin();
                    yMax = cur.rect.ymax();
                    // left 
                    if (p.x() < cur.point.x()) {
                        if (cur.left == null) {
                            cur.left = new Node(p, new RectHV(cur.rect.xmin(), 
                                       yMin, cur.point.x(), yMax));
                            size++;
                            break;
                        }
                        else {
                            cur = cur.left;
                        }
                    }
                    // right 
                    else {
                        if (cur.right == null) {
                         
                            cur.right = new Node(p, new RectHV(cur.point.x(), 
                                        yMin, cur.rect.xmax(), yMax));
                            size++;
                            break;
                        }
                        else {
                            cur = cur.right;
                        }
                    }
                }
                // horizontal
                else {
                    xMax = cur.rect.xmax();
                    xMin = cur.rect.xmin();
                    
                    // left 
                    if (p.y() < cur.point.y()) {
                        if (cur.left == null) {
                            cur.left = new Node(p, new RectHV(xMin, 
                                       cur.rect.ymin(), xMax, cur.point.y()));
                            size++;
                            break;
                        }
                        else {
                            cur = cur.left;
                        }
                    }
                    // right
                    else {
                        if (cur.right == null) {
                            cur.right = new Node(p, new RectHV(xMin, 
                                        cur.point.y(), xMax, cur.rect.ymax()));
                            size++;
                            break;
                        }
                        else {
                            cur = cur.right;
                        }
                    }
                    
                }
            isVertical = !isVertical;
            }
        }
        
       
    }
    
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        
        boolean isVertical = true;
        boolean res = false;
        Node cur = root;
        while (cur != null && !res) {
            if (cur.point.equals(p)) {
                res = true;
                break;
            }
            else {
                // vertical
                if (isVertical) {
                    // left 
                    if (p.x() < cur.point.x()) {
                        cur = cur.left;
                    }
                    else {
                        cur = cur.right;
                    }
                }
                // horizontal
                else {
                    if (p.y() < cur.point.y()) {
                        cur = cur.left;
                    }
                    else {
                        cur = cur.right;
                    }
                    
                }
            }
            isVertical = !isVertical;
        }
        return res;
    }
    
    public void draw() {
        StdOut.println(size);
        draw(root, true);
    }
    private void draw(Node cur, boolean red) {
        if (cur != null) {
            // left tree
            
//            if (cur.left != null) {
//                StdOut.println("left");
//                StdOut.println(cur.left.point.x());
//                StdOut.println(cur.left.point.y());
//                StdOut.println(cur.left.rect.xmin());
//                StdOut.println(cur.left.rect.xmax());
//                StdOut.println(cur.left.rect.ymin());
//                StdOut.println(cur.left.rect.ymax());
//            }
            draw(cur.left, !red);
            
            // point
            StdDraw.setPenColor(StdDraw.BLACK); 
            cur.point.draw();
            
            // horizontal : red
            if (red) {
                StdDraw.setPenColor(StdDraw.RED);
           
                StdDraw.line(cur.point.x(), 
                        cur.rect.ymin(), cur.point.x(), cur.rect.ymax());
            }
            // vertical : blue
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                
                StdDraw.line(cur.rect.xmin(), 
                        cur.point.y(), cur.rect.xmax(), cur.point.y());
            }
            // right tree
            draw(cur.right, !red);
            
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        return range(root, rect, new Stack<Point2D>());
    }
    
    private Iterable<Point2D> range(Node cur, RectHV rect, Stack<Point2D> stack) {
        if (cur != null) {
            if (cur.rect.intersects(rect)) {
                if (rect.contains(cur.point)) {
                    stack.push(cur.point);
                }
                range(cur.left, rect, stack);
                range(cur.right, rect, stack);
            }
        }
        return stack;
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            return null;
        }
        
        nearestPoint = null;
        dist = Double.POSITIVE_INFINITY;
        nearest(root, p, true);
        return nearestPoint;
        
    }
    private void nearest(Node cur, Point2D p, boolean isVertical) {
        if (cur != null) {
            // vertical
            if (isVertical) {
                // left
                if (p.x() < cur.point.x()) {
                    // left tree
                    nearest(cur.left, p, !isVertical);
                    // self
                    updateDist(cur, p);
                    // right tree
                    if (cur.right != null && 
                        cur.right.rect.distanceSquaredTo(p) < dist) {
                        nearest(cur.right, p, !isVertical);
                    }
                }
                //right
                else {
                    // right tree
                    nearest(cur.right, p, !isVertical);
                    // self
                    updateDist(cur, p);
                    // left tree
                    if (cur.left != null && 
                        cur.left.rect.distanceSquaredTo(p) < dist) {
                        nearest(cur.left, p, !isVertical);
                    }
                }
            }
            // horizontal
            else {
                // left
                if (p.y() < cur.point.y()) {
                    // left tree
                    nearest(cur.left, p, !isVertical);
                    // self
                    updateDist(cur, p);
                    // right tree
                    if (cur.right != null && 
                        cur.right.rect.distanceSquaredTo(p) < dist) {
                        nearest(cur.right, p, !isVertical);
                    }
                }
                //right
                else {
                    // right tree
                    nearest(cur.right, p, !isVertical);
                    // self
                    updateDist(cur, p);
                    // left tree
                    if (cur.left != null && 
                        cur.left.rect.distanceSquaredTo(p) < dist) {
                        nearest(cur.left, p, !isVertical);
                    }
                }
            }
        }
        
    }
    private void updateDist(Node cur, Point2D p) {
        double tmp = cur.point.distanceSquaredTo(p);
        if (tmp < dist) {
            nearestPoint = cur.point;
            dist = tmp;
        }
    }
    
    public static void main(String[] args) {
    }
    
}
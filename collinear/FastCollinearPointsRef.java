import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
public class FastCollinearPointsRef 
{
    private int segmentsCount = 0;
    private LineSegment[] lineSegments;
    
    private class Node
    {
        public Point first;
        public Point last;
        
        public Node(Point first, Point last)
        {
            this.first = first;
            this.last = last;
        }
    }
    
    public FastCollinearPointsRef(Point[] points) // finds all line segments containing 4 or more points
    {
        if (points == null)
            throw (new java.lang.NullPointerException());
        for (int index = 0; index < points.length; index++)
        {
            if (points[index] == null)
                   throw (new java.lang.NullPointerException());
            for (int index2 = index + 1; index2 < points.length; index2++)
            {
                if (points[index2] == null)
                   throw (new java.lang.NullPointerException());
                if (points[index].compareTo(points[index2]) == 0)
                    throw (new java.lang.IllegalArgumentException());
            }
        }
        Node[] lines = new Node[points.length]; 
        Point[] auxArray = java.util.Arrays.copyOf(points, points.length);
        for (int index = 0; index < points.length; index++) //index of base point to determine angle 
        {
            java.util.Arrays.sort(auxArray, points[index].slopeOrder());
            int currentStreak = 2;
            for (int auxIndex = 2; auxIndex < auxArray.length; auxIndex++) //loop through the array of points sorted by slope with base
            {//find series of equal slopes and add them to resulting array if it's unique
                double thisSlope = points[index].slopeTo(auxArray[auxIndex]);
                double prevSlope = points[index].slopeTo(auxArray[auxIndex - 1]);
                if (thisSlope == prevSlope)
                {//if slopes of two points are equal - they're lying on one line
                    currentStreak++;
                }
                else
                {
                    if (currentStreak > 3)
                    {
                        Point first = points[index];
                        Point last = points[index];
                        for (int subIndex = 1; subIndex < currentStreak; subIndex++)
                        {
                            if (first.compareTo(auxArray[auxIndex - subIndex]) < 0)
                                first = auxArray[auxIndex - subIndex];
                            if (last.compareTo(auxArray[auxIndex - subIndex]) > 0)
                                last = auxArray[auxIndex - subIndex];
                        }
                        if (segmentsCount == lines.length - 1)
                            lines = java.util.Arrays.copyOf(lines, lines.length*2);
                        lines[segmentsCount++] = new Node(first, last);
                    }
                    currentStreak = 2;
                }
            }
            if (currentStreak > 3)
            {
                Point first = points[index];
                Point last = points[index];
                for (int subIndex = 1; subIndex < currentStreak; subIndex++)
                {
                    if (first.compareTo(auxArray[auxArray.length - subIndex]) < 0)
                        first = auxArray[auxArray.length - subIndex];
                    if (last.compareTo(auxArray[auxArray.length - subIndex]) > 0)
                        last = auxArray[auxArray.length - subIndex];
                }
                if (segmentsCount == lines.length - 1)
                    lines = java.util.Arrays.copyOf(lines, lines.length*2);
                lines[segmentsCount++] = new Node(first, last);
            }
        }
      // remove duplicates
        lineSegments = new LineSegment[lines.length];
        int totalLines = 0;
        mergesort(lines, 0, segmentsCount - 1);        
        Node thisNode = null;
        for (int index = 0; index < segmentsCount; index++)
        {
           if (thisNode == null)
           {
               lineSegments[totalLines++] = new LineSegment(lines[index].first, lines[index].last);
               thisNode = lines[index];
               StdOut.print("added line: ");
               StdOut.println(lineSegments[totalLines - 1].toString());
           }
           else
           {
               if (thisNode.first.compareTo(lines[index].first) == 0 && thisNode.last.compareTo(lines[index].last) == 0)
               {
                   continue;
               }
               else
               {
                   lineSegments[totalLines++] = new LineSegment(lines[index].first, lines[index].last);
                   thisNode = lines[index];
                   StdOut.print("added line: ");
                   StdOut.println(lineSegments[totalLines - 1].toString());
               }
           }
        }
        segmentsCount = totalLines;
}
    
    private void mergesort(Node[] array, int firstIndex, int lastIndex)
    {
            mergesortfirst(array, firstIndex, lastIndex);
            mergesortlast(array, firstIndex, lastIndex);
    }
    
    private void mergesortfirst(Node[] array, int firstIndex, int lastIndex)
    {
        if (lastIndex - firstIndex < 1)
            return;
        else
        {
            int middle = (lastIndex - firstIndex) / 2 + firstIndex;
            mergesortfirst(array, firstIndex, middle);
            mergesortfirst(array, middle + 1, lastIndex);
            mergefirst(array, firstIndex, middle, lastIndex);
        }
    }
    
    private void mergefirst(Node[] array, int firstIndex, int middle, int lastIndex)
    {
        int left = firstIndex;
        int right = middle + 1;
        Node[] aux = new Node[(lastIndex + 1) - firstIndex];
        int auxIndex = 0;
        while (middle - left >= 0 && lastIndex - right >= 0)
        {
            if (array[left].first.compareTo(array[right].first) > 0)
                aux[auxIndex++] = array[right++];
            else
                aux[auxIndex++] = array[left++];
        }
        while(middle - left >= 0)
            aux[auxIndex++] = array[left++];
        while(lastIndex - right >= 0)
            aux[auxIndex++] = array[right++];
        auxIndex = 0;
        for(int index = firstIndex; index <= lastIndex; index++)
        {
            array[index] = aux[auxIndex++];
        }
    }
    
    private void mergesortlast(Node[] array, int firstIndex, int lastIndex)
    {
        if (lastIndex - firstIndex < 1)
            return;
        else
        {
            int middle = (lastIndex + firstIndex) / 2;
            mergesortlast(array, firstIndex, middle);
            mergesortlast(array, middle + 1, lastIndex);
            mergelast(array, firstIndex, middle, lastIndex);
        }
    }
    
    private void mergelast(Node[] array, int firstIndex, int middle, int lastIndex)
    {
        int left = firstIndex;
        int right = middle + 1;
        Node[] aux = new Node[lastIndex + 1 - firstIndex];
        int auxIndex = 0;
        while (middle - left >= 0 && lastIndex - right >= 0)
        {
            if (array[left].last.compareTo(array[right].last) > 0)
                aux[auxIndex++] = array[right++];
            else
                aux[auxIndex++] = array[left++];
        }
        while(middle - left >= 0)
            aux[auxIndex++] = array[left++];
        while(lastIndex - right >= 0)
            aux[auxIndex++] = array[right++];
        auxIndex = 0;
        for(int index = firstIndex; index <= lastIndex; index++)
        {
            array[index] = aux[auxIndex++];
        }
    }
    
//    public static void main(String args[])
//    {
//        //String arg = args[0];
//        int[] xpoints = new int[]{11750,2000,25100,23600,2100,32100,6500,16650,28850,8250,18750,2700,13950,14900,31150,26300,30700,550,30500,17600,14700,10850,28600,7300,30650,28350,18950,30200,30000,27800,700,2950,9100,11050,700,31800,11350,24500,32650,10600,13400,4850,5300,30600,7900,29050,26400,18650,11800,12600,17550,11750,1000,4300,19250,24600,8300,7500,20850,17200,8650,1200,21800,17000,9100,23700,27550,1300,16450,26850,15250,3950,21100,1950,14200,3200,14750,3250,25450,5600,200,29900,7300,29550,26450,30850,27950,27650,28250,10150,8750,24150,10700,22150,28600,31600,13750,17350,6000,6950,20450,200,7850,16900,14450,17550,18400,5000,5900,29250,7450,1650,18250,14900,32200,21850,2950,24450,16050,17250,18250,19000,3900,28150,550,12050,29150,1100,4650,13400,14700,23350,28800,7100,6850,31050,21900,7500,1600,20450,24900,14050,5600,32150,11300,23750,400,3550,17450,4550,11350,21450,13500,4150,30600,4950,23350,19200,12300,21950,26200,12800,550,24100,1200,16150,2000,23000,24300,30200,8500,600,8350,8150,26000,2950,28700,10650,8350,21850,21450,11850,27250,31150,10350,22500,2600,28400,26250,23000,30450,6350,6200,29800,25450,15600,100,1050,26550,30950,4950,18450,31000,27700,100,10400,32450,29300,12150,25500,30350,26250,12500,10800,14500,1150,16300,22200,2550,9450,6600,900,20400,10650,2750,27450,21400,5300,11500,7300,10550,24750,32600,9600,25700,21650,4450,1450,29300,10850,9450,18450,1600,600,18550,21700,9900,14800,1900,7300,4600,24500,29300,27200,17450,24900,16900,4800,2800,27850,22200,27950,5100,16850,15800,29650,2950,3900,10450,13400,25700,28150,4550,32000,29500,17250,23650,22300,23150,16500,6350,22900,21950,15100,12450,9000,16800,17600,25600,28050,20800,0,8600,18900,4500,20150,24700,13900,28200
//};
//        int[] ypoints = new int[]{7100,26150,24650,23500,30900,26550,11700,27100,1350,12650,18250,31850,10700,32250,15200,23900,14550,50,14400,10250,27550,3450,18200,10450,30050,15400,21650,1600,8500,11150,2050,200,3400,21800,4350,13600,14950,25850,11850,28550,17450,12750,20900,14700,12950,5250,26650,29100,32650,31350,26500,22600,32650,12050,26650,7450,1350,31250,11900,6900,12900,19450,10800,3900,3200,23150,3000,14300,1900,9000,15100,25700,23950,30300,3150,10250,9950,17450,16050,30600,17550,1600,25700,8500,22350,18400,31050,10700,4600,25500,4050,15250,24250,14150,5450,29750,27850,9900,21000,32200,21900,28050,28000,32700,13850,23250,22050,4900,10550,12600,5500,2050,15350,3350,12750,26700,4050,18000,25650,17450,31950,29150,1550,29550,11300,14500,15400,6950,15250,25750,400,24950,8700,8050,22550,23950,9600,32250,1050,28350,23450,6100,21750,18850,17850,1050,8600,9700,16000,14900,15650,6150,3250,29450,17900,18650,4500,17850,22000,20350,9400,23950,14600,8650,21500,17850,27300,8900,23100,26150,17450,31900,17350,28500,23300,5600,21000,4350,14250,2100,21900,30600,10150,11350,20800,11850,4000,24950,23600,8500,16650,24700,21950,7350,31400,1600,8600,17650,21850,8500,16000,9500,500,28650,12050,4000,24600,6900,17700,11800,3700,6350,2100,15250,14850,1900,2000,2150,4800,10000,4800,100,26650,17800,25250,5000,4650,650,29500,31650,27900,31800,23350,31550,18950,4500,5750,21400,23100,600,23500,19750,15050,24400,11650,20500,24900,20950,32300,10050,21150,27250,22950,11650,9650,10600,15900,30350,23350,18650,26550,4150,19400,15550,32000,11900,25400,700,15950,24900,4800,3650,32000,12250,1550,9700,12150,17750,31750,30700,5650,2400,2100,22150,11500,10250,2550,13850,18000,12300,18750,7150,18900,2400,16600,22000,13700,11100,17700
//};
//        Point[] newpoints = new Point[xpoints.length];
//        for (int index = 0; index < xpoints.length; index++)
//        {
//            newpoints[index] = new Point(xpoints[index], ypoints[index]);
//        }
//        FastCollinearPointsRef col = new FastCollinearPointsRef(newpoints);
//        StdOut.print("total count: ");
//        StdOut.println(col.numberOfSegments());
//    }
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
    FastCollinearPointsRef collinear = new FastCollinearPointsRef(points);
    
    //StdOut.println("number of segments is " + collinear.numberOfSegments);
    
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
}
    public int numberOfSegments() { return segmentsCount; }        // the number of line segments
    public LineSegment[] segments()               // the line segments
    {
        return java.util.Arrays.copyOf(lineSegments, segmentsCount);
    }
}
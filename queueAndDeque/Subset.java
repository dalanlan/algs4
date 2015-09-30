import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Subset {
    public static void main (String[] args)
    {
        int k;
        k = Integer.parseInt(args[0]);
//        if (args.length > 0) {
//            try {
//                k = Integer.parseInt(args[0]);
//            } catch (NumberFormatException e) {
//                StdOut.println("input mismatch");
//                System.exit(1);
//            }
//        }
//      
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            q.enqueue(item);
        }
        for(int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
        StdOut.println(q.size() + " left in queue");    
    }
}
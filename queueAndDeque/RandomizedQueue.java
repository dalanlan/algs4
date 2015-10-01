import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {
   
    private Item[] q;
    private int size = 0;
    private int first = 0;
    private int last = 0;
    
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
    }
    /**
     * is the queue empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    private void resize(int max) {
        assert max >= size;
        Item[] tmp = (Item[]) new Object[max];
        for (int i = 0; i < size; i++) {
            tmp[i] = q[(first + i) % q.length];
        }
        q = tmp;
        first = 0;
        last = size;
    }
    /**
     * return the number of items on the queue
     */
    public int size() {
        return size;
    }
    /**
     * add the item from the end 
     */
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("null pointer");
        
        if (size == q.length) 
            resize(2*q.length);
        q[last++] = item;
        if (last == q.length) 
            last = 0;   // wrap-around
        size++;
    }
   
    /**
     * remove and return a random item
     */
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
       
        int tmp_last;
        if (last < first)
        {
            tmp_last = last + q.length;
        }
        else if (last == first && size == q.length) {
            tmp_last = last + q.length;
        }
        else
            tmp_last = last;
        
        int index = (StdRandom.uniform(first, tmp_last) % q.length);
        
        
        Item tmp = q[index];
        q[index] = q[first];
        q[first] = null;
        size--;
        first++;
        if (first == q.length) 
            first = 0;
        if (size > 0 && size == q.length/4)
            resize(q.length/2);
        return tmp;

    }
    
    /**
     * return a random item
     */
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        
        int tmp_last;
        if (last < first)
        {
            tmp_last = last + q.length;
        }
        else if (last == first && size == q.length) {
            tmp_last = last + q.length;
        }
        else
            tmp_last = last;
        
        int index = (StdRandom.uniform(first, tmp_last) % q.length);
        return q[index];
    }
    
    /**
     * return an independent iterator over items
     * in random order
     */
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item> {
        private int index;
        private int tmpLast;
        
        public RandomIterator() {
       
         if (last < first)
        {
            tmpLast = last + q.length;
        }
        else if (last == first && size == q.length) {
            tmpLast = last + q.length;
        }
        else
            tmpLast = last;
        
        index = (StdRandom.uniform(first, tmpLast) % q.length);
        }
        
        public boolean hasNext() {
            return !isEmpty();
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            int index_next;
            do {
                index_next = (StdRandom.uniform(first, tmpLast) % q.length);
            } while(index == index_next);
            return q[index_next];
                
        }
    }
    
    /**
     * unit testing
     */
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                q.enqueue(item);
            else if (!q.isEmpty())
                StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left in queue)");
    }
   
}
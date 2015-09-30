import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private int size;         // size of deque
    private Node<Item> first; // beginning of deque
    private Node<Item> last;  // end of deque
    
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }
    
    /**m
      * 
     * constructor
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }
    
    /** 
     * is the deque empty
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * the number of items on the deque
     */
    public int size() {
        return size;
    }
    
    /**
     * add the item to the front 
     */
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("null pointer");
        
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        
        if( !isEmpty())
        {
            oldfirst.prev = first;
            first.next = oldfirst;
        }
        if (isEmpty())
            last = first;
        size++;
    }
    /**
     * add the item to the end
     */
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("null pointer");
        
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        
        if (!isEmpty())
        {
            oldlast.next = last;
            last.prev = oldlast;
        }
        else
            first = last;
       
        size++;
    }
    
    /**
     * remove and return the item from the front
     */
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("deque underflow");
        
        Node<Item> oldfirst = first;
        first = first.next;
        oldfirst.next = null;  
        size--;
        
        if(isEmpty())
            last = first;
        else {
            first.prev = null;
        }
        return oldfirst.item;
        
    }
    
    /**
     * remove and return item from the end
     */
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("deque underflow");
       
        Item item = last.item;
        if(first == last)
        {
            first = null;
            last = null;    
        }
        else {
            last = last.prev;
            last.next = null;
        }
        size--;
        return item;
        
    }
    
    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        public ListIterator(Node<Item> first) {
            current = first;
        }
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) 
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                d.addFirst(item);
            else if (!d.isEmpty())
                StdOut.print(d.removeLast() + " ");
        }
        StdOut.println("(" + d.size() + " left in deque)");
    }
    
}
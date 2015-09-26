/*----------------------------------------------------------------
 * Author:       Emma He
 * Written:      09/20/2015
 * Last updated: 09/21/2015
 * 
 * Model a percolation system.
 * Create an N-by-N grid of sites. Each site is either open or blocked.
 * Provide some methods to determine whether the site is open or full,
 * and the grid is percolated.
 *  
 * ----------------------------------------------------------------*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;                  // N-by-N grid
    private int n;                         // length of side of grid   
    private WeightedQuickUnionUF uf;       // union-find data structure
    private WeightedQuickUnionUF ufHelper; // union-find data structure
    
    /**
     * Creates N-by-N grid, with all sites blocked
     * Initializes an empty union-find data structure to help 
     * check whether the site is full.
     * 
     * Throws a java.lang.IllegalArgumentException if N <= 0.
     */
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("Illegal argument");
        n = N;
        grid = new int[n][n]; 
        uf = new WeightedQuickUnionUF(n*n + 1);       // range: 0~N*N
        ufHelper = new WeightedQuickUnionUF(n*n + 1); // range: 0~N*N
    }
    
    /**
     * Open site (row i, column j) if it is not open already
     * Merge the newly open site with its neighbor
     * 
     * Throw a java.lang.IndexOutOfBoundsException 
     * if any argument outside its prescribed range.
     */
    public void open(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n)
            throw new IndexOutOfBoundsException("Index out of bound");
        
        if (!isOpen(i, j))
               grid[i-1][j-1] = 1;
        
        if (i == 1)                                  // top row
        {
            uf.union(0, (i-1)*n+j);                  // virtual 
            ufHelper.union(0, (i-1)*n+j);            // real
        }
        
        if (i == n)
        {
            uf.union(n*n, (i-1)*n+j);               // bottom row
        }
        if (i-1 > 0)
        {
            if (isOpen(i-1, j))
            {
                uf.union((i-2)*n+j, (i-1)*n+j);      // union up
                ufHelper.union((i-2)*n+j, (i-1)*n+j);
            }
        }
        if (i+1 <= n)
        {
            if (isOpen(i+1, j))
            {
                uf.union(i*n+j, (i-1)*n+j);          // union down
                ufHelper.union(i*n+j, (i-1)*n+j);
            }
        } 
        if (j-1 > 0)
        {
            if (isOpen(i, j-1))
            {
                uf.union((i-1)*n+j-1, (i-1)*n+j);    // union left
                ufHelper.union((i-1)*n+j-1, (i-1)*n+j);
            }
        }
        if (j+1 <= n)
        {
            if (isOpen(i, j+1))
            {
                uf.union((i-1)*n+j+1, (i-1)*n+j);     // union right
                ufHelper.union((i-1)*n+j+1, (i-1)*n+j);
            }
        }
    
    }
    
    /**
     * Returns whether site (row i, column j) is open
     * 
     * Throw a java.lang.IndexOutOfBoundsException 
     * if any argument outside its prescribed range.
     */
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n)
            throw new IndexOutOfBoundsException("Index out of bound");
        return grid[i-1][j-1] == 1;
    }
    
     /**
     * Returns whether site (row i, column j) is full
     * 
     * Throw a java.lang.IndexOutOfBoundsException 
     * if any argument outside its prescribed range.
     */
    public boolean isFull(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n)
            throw new IndexOutOfBoundsException("Index out of bound");
        if (!isOpen(i, j))
            return false;
        if (i == 1)
            return true;
        return ufHelper.connected(0, (i-1)*n+j);        
    }
    
     /**
     * Returns whether the system percolates
     * 
     */
    public boolean percolates() {
        return uf.connected(0, n*n);
    }
    
}
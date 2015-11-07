import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Board {
    private int[][] board;
    private int dim;
    
    private int iBlank;
    private int jBlank;
    
    public Board(int[][] blocks) {
        // null arguments
        if (blocks == null) {
            throw new IllegalArgumentException("Illegal argument");
        }
        // deep copy
        dim = blocks.length;
        board = new int[dim][dim];
//        for (int i = 0; i < dim; i++) {
//            board[i] = Arrays.copyOf(blocks[i], dim);
//        }
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                board[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    iBlank = i;
                    jBlank = j;
                }
            }
        }
    }
    
    private void swap(int[][] blo, int i0, int j0, int i1, int j1) {
        int tmp = blo[i0][j0];
        blo[i0][j0] = blo[i1][j1];
        blo[i1][j1] = tmp;
    }
    // dimension
    public int dimension() {
        return dim;
    }
    // hamming distance
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != 0 && board[i][j] != i*dim + j + 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }
    //manhattan distance
    public int manhattan() {
        int iTmp, jTmp, diff, manhattan = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != 0 && board[i][j] != i*dim + j + 1) {
                    iTmp = (board[i][j]-1)/dim;
                    jTmp = (board[i][j]-1) % dim;
                    diff = Math.abs(iTmp - i) + Math.abs(jTmp -j);
                    //StdOut.println("i: "+i+", j: "+j+", diff: "+diff);
                    manhattan += diff;
                }
            }
        }
        return manhattan;
    }
 
    // Is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board twin = new Board(board);
        // exchange the pairs of the same row
        int i = (iBlank+1) % dim;
        swap(twin.board, i, 0, i, 1);
        return twin;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) 
            return  true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (this.dim != that.dim)
            return false;
        
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
        
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neigh = new Queue<Board>();
        int i = iBlank, j = jBlank;
        
        //left
        if (j > 0) {
            swap(board, i, j, i, j-1);
            Board newBoard = new Board(board);
            neigh.enqueue(newBoard);
            swap(board, i, j, i, j-1);
        }
        
        //right
        if (j < dim-1) {
            swap(board, i, j, i, j+1);
            Board newBoard = new Board(board);
            neigh.enqueue(newBoard);
            swap(board, i, j, i, j+1);
        }
        
        //up 
        if (i > 0) {
            swap(board, i, j, i-1, j);
            Board newBoard = new Board(board);
            neigh.enqueue(newBoard);
            swap(board, i, j, i-1, j);
        }
        
        //down
        if (i < dim-1) {
            swap(board, i, j, i+1, j);
            Board newBoard = new Board(board);
            neigh.enqueue(newBoard);
            swap(board, i, j, i+1, j);
        }
        return neigh;
           
        
    }
    
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) {
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);
//    StdOut.println(initial.toString());
//    StdOut.println(initial.manhattan());
    
//    Iterable<Board> newboards = initial.neighbors();
//      for (Board bo : newboards)
//          StdOut.println(bo.toString());

      Iterable<Board> newboards = initial.neighbors();
      for (Board bo : newboards)
          StdOut.println(bo.toString());
      StdOut.println(initial.dimension());
      StdOut.println(initial.hamming());
      StdOut.println(initial.manhattan());
    }
}
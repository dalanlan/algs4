import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    
    private boolean isSolvable;
    private Stack<Board> solution;
    
    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode father;
        private int move;
        private final Board board;
        
        
        SearchNode(Board board, SearchNode father) {
            this.board = board;
            this.move = (father == null) ? 0 : father.move + 1;
            this.father = father;
            
        }
        
        private int priority() {
            return board.manhattan() + move;
        }
        
        public int compareTo(SearchNode that) {
            if (this.priority() > that.priority()) return +1;
            if (this.priority() < that.priority()) return -1;
            if (this.board.manhattan() > that.board.manhattan()) return +1;
            if (this.board.manhattan() < that.board.manhattan()) return -1;
            return 0;
        }
    }
    public Solver(Board initial) {
        solution = new Stack<Board>();
        if (initial.isGoal()) {
            
            isSolvable = true;
            solution.push(initial);
            return;
        }
        if (initial.twin().isGoal()) {
            isSolvable = false;
            return; 
        }
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        MinPQ<SearchNode> tmp = pq;
        
        pq.insert(new SearchNode(initial, null));
        pqTwin.insert(new SearchNode(initial.twin(), null));
        
        SearchNode minNode;
        while (true) {
            
            minNode = tmp.delMin();
            //minNodeTwin = pqTwin.delMin();

            if (minNode.board.isGoal()) {
                isSolvable = (tmp == pq);
                if (isSolvable) {
                
                solution.push(minNode.board);
                while (minNode.father != null) {
                    minNode = minNode.father;
                    solution.push(minNode.board);
                }
                }
            return;
            }
            
//            if (minNodeTwin.board.isGoal()) {
//                isSolvable = false;
//                return;
//            }

            for (Board b:minNode.board.neighbors()) {
                if (minNode.father != null && b.equals(minNode.father.board))
                    continue;
                tmp.insert(new SearchNode(b, minNode));
                
            }

//            for (Board b:minNodeTwin.board.neighbors()) {
//                if (minNodeTwin.father != null &&
//                    b.equals(minNodeTwin.father.board)) 
//                    continue;
//                pqTwin.insert(new SearchNode(b, minNodeTwin));
//
//            }
            tmp = (tmp == pq) ? pqTwin : pq;
            
        }
        
        
        
    }
    
    public boolean isSolvable() {
        return isSolvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution.size()-1;
    }
    
    public Iterable<Board> solution() {
        if (isSolvable()) 
            return solution;
        return null;
    }
    
    public static void main(String[] args) {
       
    // create initial board from file
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
      
    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
    }
}
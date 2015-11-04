import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private int move = -1; // initialization
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
            return 0;
        }
    }
    public Solver(Board initial) {
        solution = new Stack<Board>();
        if (initial.isGoal()) {
            move = 0;
            isSolvable = true;
            solution.push(initial);
            return;
        }
        if (initial.twin().isGoal()) {
            isSolvable = false;
            return; // move = -1 now
        }
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        
        pq.insert(SearchNode(initial, null));
        pqTwin.insert(SearchNode(initial.twin(),null));
        
        move++; // move = 0, pq.min == initial
        while (!pq.min.board.isGoal() && !pqTwin.min.board.isGoal()) {
            SearchNode minNode = pq.min;
            SearchNode minNodeTwin = pqTwin.min.board;
            move++;
            for (Board b:pq.delMin().board.neighbors()) {
                if (b.equals(minNode.board))
                    continue;
                pq.insert(SearchNode(b, minNode));
            }
            for (Board b:pq.delMin().board.neighbors()) {
                if (b.equals(minNodeTwin.board)) 
                    continue;
                pqTwin.insert(SearchNode(b, minNodeTwin));
            }
        }
        if (pq.min.board.isGoal()) {
            isSolvable = true;
            
            SearchNode node = pq.min;
            solution.push(node.board);
            while(node.father != null) {
                node = node.father;
                solution.push(node.board);
            }    
        }
        else {
            isSolvable = false;
        }
        
    }
    
    public boolean isSolvable() {
        return isSolvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return move;
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
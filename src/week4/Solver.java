package week4;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {

    private boolean solvable;
    private Node endNode;

    private class Node {

        private Board board;
        private Node parent;
        private int moves;

        public Node(Board board, Node parent, int moves) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        solvable = false;
        boolean swapSolvable = false;
        Comparator<Node> boardComparator = Comparator.comparingInt(o -> o.board.manhattan() + o.moves);
        MinPQ<Node> queue = new MinPQ<>(boardComparator);
        MinPQ<Node> swapQueue = new MinPQ<>(boardComparator);
        Node initialNode = new Node(initial, null, 0);
        queue.insert(initialNode);
        Node initialSwap = new Node(initial.twin(), null, 0);
        swapQueue.insert(initialSwap);
        while (!solvable && !swapSolvable) {
            solvable = solveStep(queue);
            swapSolvable = solveStep(swapQueue);
        }
    }

    private boolean solveStep(MinPQ<Node> queue) {

        Node current = queue.delMin();

        if (current.board.isGoal()) {
            endNode = current;
            return true;
        }

        for (Board board : current.board.neighbors()) {
            if (current.parent == null) {
                Node neighbor = new Node(board, current, 1);
                queue.insert(neighbor);
            } else if (!board.equals(current.parent.board)) {
                Node neighbor = new Node(board, current, current.moves + 1);
                queue.insert(neighbor);
            }
        }

        return false;

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return endNode.moves;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> solutions = new Stack<>();
            Node current = endNode;
            solutions.push(endNode.board);

            while (current.parent != null) {
                solutions.push(current.parent.board);
                current = current.parent;
            }

            return solutions;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        int[][] input = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board testBoard = new Board(input);
        Solver solver = new Solver(testBoard);
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        }
    }
}

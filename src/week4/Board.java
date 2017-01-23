package week4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int[][] blocks;

    // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];
        for (int row = 0; row < this.blocks.length; row++) {
            System.arraycopy(blocks[row], 0, this.blocks[row], 0, this.blocks.length);
        }
    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int hammingScore = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                if (blocks[row][column] != 0 && blocks[row][column] != dimension() * row + column + 1) {
                    hammingScore++;
                }
            }
        }
        return hammingScore;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattanScore = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                if (blocks[row][column] != 0) {
                    int compareValue = blocks[row][column];
                    int expectedRow = (compareValue - 1) / dimension();
                    int expectedColumn = (compareValue - 1) % dimension();
                    manhattanScore += (Math.abs(expectedRow - row) + Math.abs(expectedColumn - column));
                }
            }
        }
        return manhattanScore;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    /// a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twinBlocks = new int[dimension()][dimension()];
        for (int row = 0; row < dimension(); row++) {
            System.arraycopy(blocks[row], 0, twinBlocks[row], 0, dimension());
        }
        int rowSwap = 0;
        if (twinBlocks[0][0] == 0 || twinBlocks[0][1] == 0) rowSwap = 1;
        swap(twinBlocks, rowSwap, 0, rowSwap, 1);
        return new Board(twinBlocks);
    }


    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board board = (Board) y;
        return Arrays.deepEquals(blocks, board.blocks);
    }

    /// all neighboring boards
    public Iterable<Board> neighbors() {

        List<Board> neighbors = new ArrayList<>();

        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                if (blocks[row][column] == 0) {

                    if (row != 0) {
                        int[][] neighbourBlocks = new int[dimension()][dimension()];
                        for (int counter = 0; counter < dimension(); counter++) {
                            System.arraycopy(blocks[counter], 0, neighbourBlocks[counter], 0, dimension());
                        }
                        swap(neighbourBlocks, row, column, row - 1, column);
                        neighbors.add(new Board(neighbourBlocks));
                    }

                    if (row != dimension() - 1) {
                        int[][] neighbourBlocks = new int[dimension()][dimension()];
                        for (int counter = 0; counter < dimension(); counter++) {
                            System.arraycopy(blocks[counter], 0, neighbourBlocks[counter], 0, dimension());
                        }
                        swap(neighbourBlocks, row, column, row + 1, column);
                        neighbors.add(new Board(neighbourBlocks));
                    }

                    if (column != 0) {
                        int[][] neighbourBlocks = new int[dimension()][dimension()];
                        for (int counter = 0; counter < dimension(); counter++) {
                            System.arraycopy(blocks[counter], 0, neighbourBlocks[counter], 0, dimension());
                        }
                        swap(neighbourBlocks, row, column, row, column - 1);
                        neighbors.add(new Board(neighbourBlocks));
                    }

                    if (column != dimension() - 1) {
                        int[][] neighbourBlocks = new int[dimension()][dimension()];
                        for (int counter = 0; counter < dimension(); counter++) {
                            System.arraycopy(blocks[counter], 0, neighbourBlocks[counter], 0, dimension());
                        }
                        swap(neighbourBlocks, row, column, row, column + 1);
                        neighbors.add(new Board(neighbourBlocks));
                    }
                }
            }
        }

        return neighbors;
    }

    private void swap(int[][] arr, int i1, int j1, int i2, int j2) {
        int temp = arr[i1][j1];
        arr[i1][j1] = arr[i2][j2];
        arr[i2][j2] = temp;
    }

    // string representation of this board (in the output format specified below)
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension()).append('\n');
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append('\n');
        }
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] input = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board testBoard = new Board(input);
        System.out.println(testBoard.manhattan());
        input[0][0] = 2;
        input[0][1] = 1;
        System.out.println(testBoard.manhattan());
        Iterable<Board> result = testBoard.neighbors();
        for (Board b : result) {
            System.out.println(b.toString());
        }
    }
}

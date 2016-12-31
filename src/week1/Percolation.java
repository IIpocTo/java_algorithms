package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int gridSize;
    private int[] openSites;
    private WeightedQuickUnionUF normalUF;
    private WeightedQuickUnionUF hackUF;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("parameter n must be positive");
        }
        gridSize = n;
        normalUF = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        hackUF = new WeightedQuickUnionUF(gridSize * gridSize + 1);
        openSites = new int[gridSize * gridSize + 2];
        for (int i = 1; i <= gridSize * gridSize; i++) {
            openSites[i] = 0;
        }
        openSites[0] = 1;
        openSites[gridSize * gridSize + 1] = 1;
    }

    private int xyTo1D(int x, int y) {
        return (x - 1) * gridSize + y;
    }

    private void validateIndex(int row, int col) {
        if (row <= 0 || row > gridSize || col <= 0 || col > gridSize) {
            throw new IndexOutOfBoundsException("wrong elem index");
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndex(row, col);
        if (isOpen(row, col)) return;
        final int thisCite = xyTo1D(row, col);
        openSites[thisCite] = 1;
        if (row == gridSize) normalUF.union(thisCite, gridSize * gridSize + 1);
        if (row == 1) {
            normalUF.union(thisCite, 0);
            hackUF.union(thisCite, 0);
        }
        if (row - 1 != 0 && isOpen(row - 1, col)) {
            normalUF.union(thisCite, xyTo1D(row - 1, col));
            hackUF.union(thisCite, xyTo1D(row - 1, col));
        }
        if (row + 1 != gridSize + 1 && isOpen(row + 1, col)) {
            normalUF.union(thisCite, xyTo1D(row + 1, col));
            hackUF.union(thisCite, xyTo1D(row + 1, col));
        }
        if (col - 1 != 0 && isOpen(row, col - 1)) {
            normalUF.union(thisCite, xyTo1D(row, col - 1));
            hackUF.union(thisCite, xyTo1D(row, col - 1));
        }
        if (col + 1 != gridSize + 1 && isOpen(row, col + 1)) {
            normalUF.union(thisCite, xyTo1D(row, col + 1));
            hackUF.union(thisCite, xyTo1D(row, col + 1));
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndex(row, col);
        return openSites[xyTo1D(row, col)] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndex(row, col);
        return isOpen(row, col) && hackUF.connected(xyTo1D(row, col), 0);
    }

    // number of open sites
    public int numberOfOpenSites() {
        int openSitesSum = 0;
        for (int site : openSites) {
            if (site == 1) openSitesSum++;
        }
        return openSitesSum - 2;
    }

    // does the system percolate?
    public boolean percolates() {
        return normalUF.connected(gridSize * gridSize + 1, 0);
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 3);
        percolation.open(2, 2);
        percolation.open(3, 3);
        percolation.open(1, 2);
        System.out.println(percolation.percolates());
    }

}

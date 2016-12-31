package week1.additionalTasks;

/*
Successor with delete. Given a set of N integers S={0,1,...,N−1} and a sequence of requests of the following form:
    Remove x from S
    Find the successor of x: the smallest y in S such that y≥x.
design a data type so that all operations (except construction) should take logarithmic time or better.
*/
public class SuccessorRemoveQuickUnionUF {

    private int[] id;

    public SuccessorRemoveQuickUnionUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }

    public void remove(int x) {
        // can't delete last element
        if (x == id.length - 1) return;
        union(x, x + 1);
    }

    public int successor(int x) {
        return root(x);
    }

}

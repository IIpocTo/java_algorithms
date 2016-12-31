package week1.additionalTasks;

/*
Social network connectivity.
Given a social network containing n members and a log file containing m timestamps at which times pairs of members formed friendships,
design an algorithm to determine the earliest time at which all members are connected
(i.e., every member is a friend of a friend of a friend ... of a friend).
Assume that the log file is sorted by timestamp and that friendship is an equivalence relation.
The running time of your algorithm should be mlogn or better and use extra space proportional to n.
*/
public class SocialNetworkQuickUnionUF {

    private int[] id;
    private int[] sz;
    private int treesCounter;

    public SocialNetworkQuickUnionUF(int N) {
        id = new int[N];
        sz = new int[N];
        treesCounter = N;
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
            treesCounter -= 1;
        } else {
            id[j] = i;
            sz[i] += sz[j];
            treesCounter -= 1;
        }
    }

    public boolean isAllConnected() {
        return treesCounter == 1;
    }

}

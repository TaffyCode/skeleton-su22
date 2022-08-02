public class UnionFind {

    private final int[] parentUnion;
    private final int[] total;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        if (N < 0) {
            throw new IllegalArgumentException();
        }
        parentUnion = new int[N];
        total = new int[N];
        for (int each = 0; each < N; each++) {
            parentUnion[each] = each;
            total[each] = 1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -parentUnion[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return parentUnion[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v >= parentUnion.length || v < 0) {
            throw new IllegalArgumentException();
        }
        while (v != parentUnion[v]) {
            parentUnion[v] = parentUnion[parentUnion[v]];
            v = parentUnion[v];
        }
        return v;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int i = find(v1);
        int j = find(v2);
        if (i == j) {
            return;
        }
        if (total[i] < total[j]) {
            parentUnion[i] = j;
            total[j] += total[i];
        } else {
            parentUnion[j] = i;
            total[i] += total[j];
        }
    }
}

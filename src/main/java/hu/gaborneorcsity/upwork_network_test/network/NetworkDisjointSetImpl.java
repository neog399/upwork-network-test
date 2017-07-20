package hu.gaborneorcsity.upwork_network_test.network;

/**
 * <p>
 * Represents a network of nodes where querying the reachability of one node from another
 * is decided using disjoint-sets
 * </p>
 */
public final class NetworkDisjointSetImpl extends NetworkBase {
    /**
     * The underlying disjoint-set
     * Space complexity: O(n)
     */
    private final DisjointSet disjointSet;

    /**
     * <p>
     * Constructs a new network instance with the given number of nodes.
     * The constructed network will use disjoint-set to discover whether a node is reachable from another one
     * </p>
     *
     * @param numberOfNodes The number of nodes this network will contain
     */
    @SuppressWarnings("unchecked")
    public NetworkDisjointSetImpl(int numberOfNodes) {
        super(numberOfNodes);

        this.disjointSet = new DisjointSet(numberOfNodes);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1) (amortized)
     * Space complexity: O(1)
     * </p>
     */
    @Override
    protected void doConnect(int src, int dest) {
        // Need to transform the indices because ours are 1-based, but Java array indices are 0-based
        disjointSet.union(src - 1, dest - 1);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1) (amortized)
     * Space complexity: O(1)
     * </p>
     */
    @Override
    protected boolean doQuery(int src, int dest) {
        // Need to transform the indices because ours are 1-based, but Java array indices are 0-based
        return disjointSet.find(src - 1) == disjointSet.find(dest - 1);
    }

    /**
     * A data structure representing a disjoint-set
     */
    private static final class DisjointSet {
        private final int numberOfElements;

        /**
         * An array containing the parent of each element
         */
        private final int[] parents;

        /**
         * An array containing the rank of each element
         */
        private final int[] ranks;

        /**
         * Constructs a new disjoint-set instance with the given number of elements
         *
         * @param numberOfElements The number of elements this disjoint-set will contain
         */
        public DisjointSet(int numberOfElements) {
            this.numberOfElements = numberOfElements;
            this.parents = new int[numberOfElements];
            this.ranks = new int[numberOfElements];

            for (int i = 0; i < numberOfElements; i++) {
                this.parents[i] = i;
                this.ranks[i] = 0;
            }
        }

        /**
         * Find and return the representative element of the set to which the provided element belongs
         * Compress the element tree if needed
         *
         * @param element The element the representative of which we're looking for
         *
         * @return The representative element of the set to which the provided element belongs
         *
         * @throws IllegalArgumentException If the element index is out of bounds [0, numberOfElements - 1]
         */
        public int find(int element) {
            if (element < 0 || element >= numberOfElements) {
                throw new IllegalArgumentException("Element " + element + " was not found");
            }

            // This means element has no parent
            if (parents[element] == element) {
                // Found the representative of the set
                return element;
            }

            // Compress the tree - attach all elements directly to the representative element of the set
            int root = find(parents[element]);
            parents[element] = root;

            return root;
        }

        /**
         * Merges the sets to which element1 and element2 belong, respectively.
         * If the two elements belong to the same set, it returns silently.
         * The merge itself will take into consideration the size of the element trees.
         *
         * @param element1 An element from the 1st set
         * @param element2 An element from the 2nd set
         *
         * @throws IllegalArgumentException If the element index is out of bounds [0, numberOfElements - 1]
         */
        public void union(int element1, int element2) {
            int root1 = find(element1);
            int root2 = find(element2);

            // Are they in the same set
            if (root1 == root2) {
                // Nothing to do here
                return;
            }

            if (ranks[root1] < ranks[root2]) {
                parents[root1] = root2;
            } else if (ranks[root2] < ranks[root1]) {
                parents[root2] = root1;
            } else {
                // This is an arbitrary choice
                parents[root2] = root1;
                ranks[root1]++;
            }
        }
    }
}

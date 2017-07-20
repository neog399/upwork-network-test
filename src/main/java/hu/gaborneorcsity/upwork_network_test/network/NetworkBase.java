package hu.gaborneorcsity.upwork_network_test.network;

/**
 * <p>
 * An abstract base for all network implementations.
 * It handles common stuff like the size of the network and node index checking.
 * </p>
 */
abstract class NetworkBase implements Network {
    /**
     * The number of nodes in this network.
     * Defines the range of node indices as [1, number of nodes] or [] if it is 0
     */
    protected final int numberOfNodes;

    /**
     * <p>
     * Creates a new network with the provided number of nodes
     * The node indices in the network will range from [1, `numberOfNodes`] or [] if `numberOfNodes` is 0
     * </p>
     *
     * @param numberOfNodes The number of nodes this network will contain
     *
     * @throws IllegalArgumentException If `numberOfNodes` is a negative number
     */
    protected NetworkBase(int numberOfNodes) {
        if (numberOfNodes < 0) {
            throw new IllegalArgumentException("The number of nodes in a network must be a non-negative number");
        }

        this.numberOfNodes = numberOfNodes;
    }

    @Override
    public void connect(int src, int dest) {
        verifyNodesArePresent(src, dest);

        doConnect(src, dest);
    }

    /**
     * <p>
     * Connects the provided nodes (`src` and `dest`).
     * If the nodes are already connected it returns silently.
     * </p>
     *
     * @param src  The source node
     * @param dest The destination node
     */
    protected abstract void doConnect(int src, int dest);

    @Override
    public boolean query(int src, int dest) {
        verifyNodesArePresent(src, dest);

        return doQuery(src, dest);
    }

    /**
     * <p>
     * Check whether the provided nodes (`src` and `dest`) are connected in the network
     * (i.e. There is a path in the network where the first node is `src` and the last node is `dest`).
     * </p>
     *
     * @param src  The source node
     * @param dest The destination node
     *
     * @return True if the two nodes are connected, false otherwise
     */
    protected abstract boolean doQuery(int src, int dest);

    /**
     * Utility method that takes two nodes and verifies that they are present in this network
     *
     * @param src  The source node
     * @param dest The destination node
     *
     * @throws IllegalArgumentException If `src` or `dest` is not present in this network
     */
    private void verifyNodesArePresent(int src, int dest) {
        if (src < 1 || src > numberOfNodes) {
            throw new IllegalArgumentException(
                    "Node " + src + " not found in network [1, " + numberOfNodes + "]"
            );
        }

        if (dest < 1 || dest > numberOfNodes) {
            throw new IllegalArgumentException(
                    "Node " + dest + " not found in network [1, " + numberOfNodes + "]"
            );
        }
    }
}

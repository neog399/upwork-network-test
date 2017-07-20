package hu.gaborneorcsity.upwork_network_test.network;

/**
 * Represents a network of nodes which offers the following operations:
 * - Nodes can be connected to each other
 * - The reachability between two nodes can be queried
 */
public interface Network {
    /**
     * <p>
     * Connects the provided nodes (`src` and `dest`).
     * If the nodes are already connected it returns silently.
     * </p>
     *
     * @param src  The source node
     * @param dest The destination node
     *
     * @throws IllegalArgumentException If `src` or `dest` is not present in this network
     */
    void connect(int src, int dest);

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
     *
     * @throws IllegalArgumentException If `src` or `dest` is not present in this network
     */
    boolean query(int src, int dest);
}

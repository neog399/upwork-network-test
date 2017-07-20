package hu.gaborneorcsity.upwork_network_test.network;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * <p>
 * Represents a network of nodes where querying the reachability of one node from another
 * is decided using Breadth-First-Search (BFS)
 * </p>
 */
public final class NetworkBFSImpl implements Network {
    /**
     * The number of nodes in this network.
     * Defines the range of node indices as [1, number of nodes] or [] if it is 0
     */
    private final int numberOfNodes;

    /**
     * Map of direct (symmetric) connections for each node
     * Space complexity: O(n^2)
     */
    private final Set<Integer>[] nodeConnections;

    /**
     * Creates a new network with the provided number of nodes
     * The node indices in the network will range from [1, `numberOfNodes`] or [] if `numberOfNodes` is 0
     *
     * @param numberOfNodes The number of nodes this network will contain
     *
     * @throws IllegalArgumentException If `numberOfNodes` is a negative number
     */
    @SuppressWarnings("unchecked")
    public NetworkBFSImpl(int numberOfNodes) {
        if (numberOfNodes < 0) {
            throw new IllegalArgumentException("The number of nodes in a network must be a non-negative number");
        }

        this.numberOfNodes = numberOfNodes;

        // We need to allocate an extra entry because our node indices are 1-based but Java's array indices are 0-based
        this.nodeConnections = new Set[numberOfNodes + 1];
        for (int i = 1; i <= numberOfNodes; i++) {
            this.nodeConnections[i] = new LinkedHashSet<>();
        }
    }

    /**
     * <p>
     * Connects the provided nodes (`src` and `dest`).
     * If the nodes are already connected it returns silently.
     * </p>
     * <p>
     * Time complexity: O(1)
     * Space complexity: O(1)
     * </p>
     *
     * @param src  The source node
     * @param dest The destination node
     */
    public void connect(int src, int dest) {
        verifyNodesArePresent(src, dest);

        nodeConnections[src].add(dest);
        nodeConnections[dest].add(src);
    }

    /**
     * <p>
     * Check whether the provided nodes (`src` and `dest`) are connected in the network
     * (i.e. There is a path in the network where the first node is `src` and the last node is `dest`).
     * </p>
     * <p>
     * Time complexity: O(n)
     * Space complexity: O(n)
     * </p>
     *
     * @param src  The source node
     * @param dest The destination node
     *
     * @return True if the two nodes are connected, false otherwise
     */
    public boolean query(int src, int dest) {
        verifyNodesArePresent(src, dest);

        // Each node is connected to itself
        if (src == dest) {
            return true;
        }

        boolean[] visitedNodes = new boolean[numberOfNodes + 1];
        Queue<Integer> processingQueue = new LinkedList<>();

        visitedNodes[src] = true;
        processingQueue.add(src);

        boolean connectionFound = false;

        while (!connectionFound && !processingQueue.isEmpty()) {
            // Take the next from the queue
            int current = processingQueue.poll();

            // Visit all of its direct neighbors
            for (int neighbor : nodeConnections[current]) {
                if (neighbor == dest) {
                    // If neighbor is the one we're looking for than stop looking
                    connectionFound = true;
                    break;
                } else if (!visitedNodes[neighbor]) {
                    // Otherwise if we haven't visited it yet
                    // we put it in the queue so that we can visit its neighbors later on
                    visitedNodes[neighbor] = true;
                    processingQueue.add(neighbor);
                }
            }
        }

        return connectionFound;
    }

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

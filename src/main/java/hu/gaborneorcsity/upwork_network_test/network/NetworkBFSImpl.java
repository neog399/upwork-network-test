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
public final class NetworkBFSImpl extends NetworkBase {
    /**
     * Map of direct (symmetric) connections for each node
     * Space complexity: O(n^2)
     */
    private final Set<Integer>[] nodeConnections;

    /**
     * <p>
     * Constructs a new network instance with the given number of nodes.
     * The constructed network will use BFS to discover whether a node is reachable from another one
     * </p>
     *
     * @param numberOfNodes The number of nodes this network will contain
     */
    @SuppressWarnings("unchecked")
    public NetworkBFSImpl(int numberOfNodes) {
        super(numberOfNodes);

        // We need to allocate an extra entry because our node indices are 1-based but Java's array indices are 0-based
        this.nodeConnections = new Set[numberOfNodes + 1];
        for (int i = 1; i <= numberOfNodes; i++) {
            this.nodeConnections[i] = new LinkedHashSet<>();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     * Space complexity: O(1)
     * </p>
     */
    protected void doConnect(int src, int dest) {
        nodeConnections[src].add(dest);
        nodeConnections[dest].add(src);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(n)
     * Space complexity: O(n)
     * </p>
     */
    protected boolean doQuery(int src, int dest) {
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
}

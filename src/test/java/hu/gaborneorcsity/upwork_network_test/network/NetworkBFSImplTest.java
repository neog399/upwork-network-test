package hu.gaborneorcsity.upwork_network_test.network;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NetworkBFSImplTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void canCreateANetworkContainingSomeNodes() {
        new NetworkBFSImpl(5);
    }

    @Test
    public void canCreateANetworkContainingNoNodes() {
        new NetworkBFSImpl(0);
    }

    @Test
    public void cannotCreateANetworkContainingANegativeNumberOfNodes() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The number of nodes in a network must be a non-negative number");

        new NetworkBFSImpl(-1);
    }

    @Test
    public void cannotConnectTwoNodesIfTheFirstOneIsNotPresentInTheNetwork() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Node 0 not found in network [1, 3]");

        Network network = new NetworkBFSImpl(3);
        network.connect(0, 1);
    }

    @Test
    public void cannotConnectTwoNodesIfTheSecondOneIsNotPresentInTheNetwork() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Node 4 not found in network [1, 3]");

        Network network = new NetworkBFSImpl(3);
        network.connect(1, 4);
    }

    @Test
    public void cannotQueryTwoNodesIfTheFirstOneIsNotPresentInTheNetwork() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Node 0 not found in network [1, 3]");

        Network network = new NetworkBFSImpl(3);
        network.query(0, 1);
    }

    @Test
    public void cannotQueryTwoNodesIfTheSecondOneIsNotPresentInTheNetwork() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Node 4 not found in network [1, 3]");

        Network network = new NetworkBFSImpl(3);
        network.query(1, 4);
    }

    @Test
    public void differentNodesAreNotConnectedToEachOtherAtFirst() {
        Network network = new NetworkBFSImpl(3);

        assertFalse(network.query(1, 2));
        assertFalse(network.query(1, 3));
        assertFalse(network.query(2, 3));
    }

    @Test
    public void allNodesAreConnectedToEachOtherAlways() {
        Network network = new NetworkBFSImpl(3);

        assertTrue(network.query(1, 1));
        assertTrue(network.query(2, 2));
        assertTrue(network.query(3, 3));
    }

    @Test
    public void directConnectionsAreProperlyDiscovered() {
        Network network = new NetworkBFSImpl(8);
        network.connect(1, 2);
        network.connect(1, 6);
        network.connect(2, 4);
        network.connect(2, 6);
        network.connect(5, 8);

        assertTrue(network.query(1, 2));
        assertTrue(network.query(1, 6));
        assertTrue(network.query(2, 4));
        assertTrue(network.query(2, 6));
        assertTrue(network.query(5, 8));
        assertFalse(network.query(1, 7));
        assertFalse(network.query(5, 2));
        assertFalse(network.query(5, 6));
    }

    @Test
    public void indirectConnectionsAreProperlyDiscovered() {
        Network network = new NetworkBFSImpl(8);
        network.connect(1, 2);
        network.connect(1, 6);
        network.connect(2, 4);
        network.connect(2, 6);
        network.connect(5, 8);

        assertTrue(network.query(1, 4));
        assertTrue(network.query(6, 4));
        assertFalse(network.query(1, 7));
        assertFalse(network.query(5, 2));
        assertFalse(network.query(5, 6));
    }

    @Test
    public void connectionsInTheNetworkAreSymmetric() {
        Network network = new NetworkBFSImpl(3);
        network.connect(1, 2);

        assertTrue(network.query(1, 2));
        assertTrue(network.query(2, 1));
    }
}
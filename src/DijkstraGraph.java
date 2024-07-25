// === CS400 File Header Information ===
// Name: <your full name>
// Email: <your @wisc.edu email address>
// Group and Team: <your group name: two letters, and team color>
// Group TA: <name of your group's ta>
// Lecturer: <name of your lecturer>
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.*;

import java.nio.file.NotDirectoryException;
import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new BasicMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {

        if (start == null || this.nodes.get(start) == null || end == null
                || this.nodes.get(end) == null) {
            throw new NoSuchElementException();
        }
        MapADT<NodeType, Integer> visited = new BasicMap<>();
        PriorityQueue<SearchNode> toVisit =
                new PriorityQueue<>();

        SearchNode node = new SearchNode(nodes.get(start), 0, null);
        toVisit.add(node);

        while (!toVisit.isEmpty()) {
            SearchNode curr = toVisit.poll();
            if (visited.containsKey(curr.node.data)) {
                continue;
            }
            visited.put(curr.node.data, 1);

            if (curr.node.data.equals(end)) {
                return curr;
            }
            for (Edge edges: curr.node.edgesLeaving){

                toVisit.add(new SearchNode(edges.successor,
                        curr.cost + edges.data.doubleValue(), curr));
            }
        }
        // implement in step 5.3
        throw new NoSuchElementException();
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {

        SearchNode node = computeShortestPath(start, end);
        List<NodeType> nodes = new LinkedList<>();
        while(node != null) {
            nodes.add(0, node.node.data);
            node = node.predecessor;
        }
        // implement in step 5.4
        return nodes;
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        SearchNode node = computeShortestPath(start, end);

        return node.cost;
    }

    // TODO: implement 3+ tests in step 4.1

    /**
     * This method set's up the graph which was used in class example
     */
    public static DijkstraGraph<Integer, Integer> setupGraph() {
        // Graph Setup
        DijkstraGraph<Integer, Integer> graph = new DijkstraGraph<>();
        int[][] adjacency = new int[8][8];

        // Store graphs
        adjacency[0][1] = 9;
        adjacency[0][5] = 14;
        adjacency[0][6] = 15;

        adjacency[1][2] = 23;

        adjacency[2][4] = 2;
        adjacency[2][7] = 19;

        adjacency[3][2] = 6;
        adjacency[3][7] = 6;

        adjacency[4][3] = 11;
        adjacency[4][7] = 16;

        adjacency[5][2] = 18;
        adjacency[5][4] = 30;
        adjacency[5][6] = 5;

        adjacency[6][4] = 20;
        adjacency[6][7] = 44;

        for (int i = 0; i < 8; i++) {
            graph.insertNode(i+1);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++){
                if (adjacency[i][j]!= 0) {
                    graph.insertEdge(i+1, j+1, adjacency[i][j]);
                }
            }
        }

        return graph;
    }

    /**
     * This method tests the class example of shortest path between node 1
     * and node 8
     */
    @Test
    public void testClassExample() {
        DijkstraGraph<Integer, Integer> graph = setupGraph();
        double cost = graph.shortestPathCost(1, 8);
        assertEquals(50,cost, "Expected cost is not same");

        List<Integer> path = graph.shortestPathData(1,8);
        int k = 0;
        for (int i : new int[] {1,2,3,5,8}) {
            assertEquals(i, path.get(k++), "Path nodes were not equal");
        }
        assertEquals(path.size(), 5, "Path contains more nodes");

    }

    /**
     * This test method is to find cost between the node 4 and node 5
     */
    @Test
    public void testCustomExample() {
        DijkstraGraph<Integer, Integer> graph = setupGraph();

        double cost = graph.shortestPathCost(4, 5);
        assertEquals(8,cost, "Expected cost is not same");

        List<Integer> path = graph.shortestPathData(4,5);
        int k = 0;
        for (int i : new int[] {4, 3, 5}) {
            assertEquals(i, path.get(k++), "Path nodes were not equal");
        }
        assertEquals(path.size(), 3, "Path contains more nodes");

    }

    /**
     * This tests the behaviour of Dijkstra on a non-existent path or
     * non-existent nodes
     */
    @Test
    public void testNonExistent() {

        DijkstraGraph<Integer, Integer> graph = setupGraph();

        // When there is no path
        assertThrows(NoSuchElementException.class,
                ()->{graph.shortestPathData(8, 1);});
        assertThrows(NoSuchElementException.class,
                ()->{graph.shortestPathCost(8, 1);});
    }

}

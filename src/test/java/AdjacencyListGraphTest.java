import GraphContents.Edge;
import GraphContents.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdjacencyListGraphTest {
    /* Washington State:

    bellingham
        |
     seattle---------------ellensberg---------spokane
        |                       \               /
     tacoma                      \             /
        |                         \           /
     olympia                       \         /
        |                         yakima    /
        |                            \     /
        |                            richland
        |                               \
    vancouver                       walla walla
     */

    private GraphContents.Graph<String> washington;
    private Node<String> bellingham;
    private Node<String> seattle;
    private Node<String> tacoma;
    private Node<String> olympia;
    private Node<String> vancouver;
    private Node<String> ellensberg;
    private Node<String> spokane;
    private Node<String> yakima;
    private Node<String> richland;
    private Node<String> wallaWalla;

    // node used to check for non-existent nodes that haven't been added to graph
    private Node<String> unadded;

    @Before
    public void setUp() throws Exception {
        washington = new D26_BreadthFirstTraversal.AdjacencyListGraph<>();
        bellingham = new Node<>("Bellingham");
        seattle = new Node<>("Seattle");
        tacoma = new Node<>("Tacoma");
        olympia = new Node<>("Olympia");
        vancouver = new Node<>("Vancouver");
        ellensberg = new Node<>("Ellensberg");
        spokane = new Node<>("Spokane");
        yakima = new Node<>("Yakima");
        richland = new Node<>("Richland");
        wallaWalla = new Node<>("Walla Walla");

        unadded = new Node<>("unadded");

        // I-5 north to south
        washington.addNode(bellingham);
        washington.addNode(seattle);
        washington.addNode(tacoma);
        washington.addNode(olympia);
        washington.addNode(vancouver);

        // I-90 west to east
        washington.addNode(ellensberg);

        // north east of ellensberg
        washington.addNode(spokane);

        // south east of ellensberg
        washington.addNode(yakima);
        washington.addNode(richland);
        washington.addNode(wallaWalla);

        washington.addTwoWayEdge(bellingham, seattle, 88);
        washington.addTwoWayEdge(seattle, tacoma, 33);
        washington.addTwoWayEdge(tacoma, olympia, 38);
        washington.addTwoWayEdge(olympia, vancouver, 109);

        // east from seattle to ellensberg
        washington.addTwoWayEdge(seattle, ellensberg, 107);

        // ellensberg splits east to spokane and south to yakima
        washington.addTwoWayEdge(ellensberg, spokane, 172);
        washington.addTwoWayEdge(ellensberg, yakima, 39);

        // yakima goes south east to richland
        // you can split and go to either spokane or walla walla from richland
        washington.addTwoWayEdge(yakima, richland, 77);
        washington.addTwoWayEdge(richland, wallaWalla, 57);
        washington.addTwoWayEdge(richland, spokane, 144);
    }


    @Test
    public void getNodes() {
        assertEquals(10, washington.getNodes().size());
    }

    @Test
    public void getEdges() {
        assertEquals(20, washington.getEdges().size());
    }

    @Test
    public void getNeighbors() {
        assertEquals(3, washington.getNeighbors(seattle).size());
    }

    @Test
    public void getEdge() {
        assertEquals(172, washington.getEdge(ellensberg, spokane).getCost());
    }

    @Test
    public void addEdgeThrowsExceptions1() {
        boolean isException = false;
        try {
            washington.addEdge(seattle, unadded, 0);
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(isException);
    }

    @Test
    public void addEdgeThrowsExceptions2() {
        boolean isException = false;
        try {
            washington.addEdge(unadded, seattle, 0);
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(isException);
    }

    @Test
    public void addTwoWayEdgeThrowsExceptions() {
        boolean isException = false;
        try {
            washington.addTwoWayEdge(seattle, unadded, 0);
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(isException);
    }

    @Test
    public void getEdgeThrowsExceptions1() {
        boolean isException = false;
        try {
            washington.getEdge(seattle, unadded);
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(isException);
    }

    @Test
    public void getEdgeThrowsExceptions2() {
        boolean isException = false;
        try {
            washington.getEdge(unadded, seattle);
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(isException);
    }

    @Test
    public void isConnectedThrowsExceptions1() {
        boolean isException = false;
        try {
            washington.isConnected(seattle, unadded);
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(isException);
    }

    @Test
    public void isConnectedThrowsExceptions2() {
        boolean isException = false;
        try {
            washington.isConnected(unadded, seattle);
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(isException);
    }

    @Test
    public void connectedness() {
        assertFalse(washington.isConnected(bellingham, tacoma));
        assertFalse(washington.isConnected(vancouver, spokane));
        assertFalse(washington.isConnected(wallaWalla, yakima));
        assertFalse(washington.isConnected(seattle, olympia));

        assertTrue(washington.isConnected(ellensberg, spokane));
        assertTrue(washington.isConnected(ellensberg, yakima));

        assertTrue(washington.isConnected(richland, spokane));
        assertTrue(washington.isConnected(richland, yakima));
    }

    @Test
    public void traverseTest() {
        List<GraphContents.Node<String>> traversal = breadthFirstTraversal(washington, ellensberg);

        Set<GraphContents.Node<String>> firstLevel = new HashSet<>();
        firstLevel.add(ellensberg);

        Set<GraphContents.Node<String>> secondLevel = new HashSet<>();
        secondLevel.add(seattle);
        secondLevel.add(spokane);
        secondLevel.add(yakima);

        Set<GraphContents.Node<String>> thirdLevel = new HashSet<>();
        thirdLevel.add(bellingham);
        thirdLevel.add(tacoma);
        thirdLevel.add(richland);

        Set<GraphContents.Node<String>> fourthLevel = new HashSet<>();
        fourthLevel.add(olympia);
        fourthLevel.add(wallaWalla);

        Set<GraphContents.Node<String>> fifthLevel = new HashSet<>();
        fifthLevel.add(vancouver);

        for (int i = 0; i < traversal.size(); i++) {
            GraphContents.Node<String> current = traversal.get(i);
            if (i < firstLevel.size()) {
                assertTrue(firstLevel.contains(current));
            } else if (i < firstLevel.size() + secondLevel.size()) {
                assertTrue(secondLevel.contains(current));
            } else if (i < firstLevel.size() + secondLevel.size() + thirdLevel.size()) {
                assertTrue(thirdLevel.contains(current));
            } else if (i < firstLevel.size() + secondLevel.size() + thirdLevel.size() + fourthLevel.size()) {
                assertTrue(fourthLevel.contains(current));
            } else if (i < firstLevel.size() + secondLevel.size() + thirdLevel.size() + fourthLevel.size() + fifthLevel.size()) {
                assertTrue(fifthLevel.contains(current));
            }
        }
    }

    //whiteboard 26 - breadth first traversal 
    public List<GraphContents.Node<String>> breadthFirstTraversal(GraphContents.Graph<String> graph, GraphContents.Node<String> start) {
        //thinking things through with pseudo code
        //get data from start node and use it's int value to begin
        //boolean evaluation of integers visited or not,
        // because boolean won't accept Nodes, only int
        //this is meant to help prevent repeat visits to nodes already visited

        //final trip list - itinerary
        List<Node<String>> itinerary = new ArrayList<>();

        //queue to hold visited nodes
        Queue<Node<String>> queue = new LinkedList<>();

        Set<Node<String>> addToItenerary = new HashSet<>();
        //mark first node as visited and add it to the queue
        queue.add(start);

        addToItenerary.add(start);

        while (!queue.isEmpty()) {
            Node<String> current = queue.poll();

            //get all adjacent nodes of the dequeued (polled) value
            //if node hasn't been visited before, mark it as visited
            //then enqueue (add) it
            itinerary.add(current);

            //from class
            for (Node<String> node : graph.getNeighbors(current)) {
                if (!addToItenerary.contains(node)) {
                    queue.add(node);
                    addToItenerary.add(node);
                }
            }
        }
        return itinerary;
    }

    //whiteboard 27 get edges - TEST
    @Test
    public void possibleDirectBusinessTrip () {
        List<GraphContents.Node<String>> itinerary = new ArrayList<>();
        itinerary.add(bellingham);
        itinerary.add(seattle);
        itinerary.add(ellensberg);
        itinerary.add(yakima);
        itinerary.add(richland);
        itinerary.add(wallaWalla);

        assertEquals(368, tripCost(washington, itinerary));
    }

    //whiteboard 27 get edges - TEST
    @Test
    public void impossibleDirectBusinessTrip () {
        List<GraphContents.Node<String>> itinerary = new ArrayList<>();
        itinerary.add(bellingham);
        itinerary.add(seattle);
        itinerary.add(ellensberg);
        itinerary.add(wallaWalla);

        assertEquals(0, tripCost(washington, itinerary));
    }

    //whiteboard 27 get edges
    public int tripCost (GraphContents.Graph graph, List < GraphContents.Node < String >> itinerary){
        int cost = 0;

        //to calculate the cost see if nodes are connected and find and add up costs
        //bit from lecture but also from looking at classmate, Brandon Fenty's repo
        for (int i = 0; i < itinerary.size() - 1; i ++) {
            Node current = itinerary.get(i);
            Node next = itinerary.get(i+1);

            if (graph.isConnected(current,next)) {
                Edge edge = graph.getEdge(current,next);
                cost += edge.getCost();
            } else {
                return 0;
            }
        }
        return cost;
    }

    //whiteboard 28 Islands - TEST
    @Test
    public void islands () {
        GraphContents.Graph<String> usa = new D26_BreadthFirstTraversal.AdjacencyListGraph<>();

        GraphContents.Node<String> alaska = new GraphContents.Node<>("Alaska");
        GraphContents.Node<String> hawaii = new GraphContents.Node<>("Hawaii");
        GraphContents.Node<String> washington = new GraphContents.Node<>("Washington");
        GraphContents.Node<String> oregon = new GraphContents.Node<>("Oregon");

        usa.addNode(alaska);
        usa.addNode(hawaii);
        usa.addNode(washington);
        usa.addNode(oregon);

        usa.addTwoWayEdge(washington, oregon);

        assertEquals(0, numIslands(this.washington));
        assertEquals(2, numIslands(usa));
    }

    //whiteboard 28 Islands
    public int numIslands (GraphContents.Graph<String> graph){
        int island =0;
        //working on for each use more
        for (Node<String> node: graph.getNodes()){
            if(graph.getNeighbors(node).isEmpty()){
                island++;
            }
        }
        return island;
    }

    //whiteboard 26 breadth first traversal TEST
    @Test
    public void visitFromBellingham () {
        visitAllCities(washington, bellingham);
    }

    //whiteboard 26 breadth first traversal
    public void visitAllCities(GraphContents.Graph < String > graph, Node < String > start){
        Queue<Node<String>> qq = new LinkedList<>();
        Set<Node<String>> isEnqueued = new HashSet<>();

        qq.add(start);
        isEnqueued.add(start);

        while (!qq.isEmpty()) {
            Node<String> current = qq.poll();

            for (Node<String> neighbor : graph.getNeighbors(current)) {
                if (!isEnqueued.contains(neighbor)) {
                    qq.add(neighbor);
                    isEnqueued.add(neighbor);

                }
            }
        }
    }
}

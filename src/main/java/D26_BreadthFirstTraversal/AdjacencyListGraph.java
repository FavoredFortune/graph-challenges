package D26_BreadthFirstTraversal;

import GraphContents.Edge;
import GraphContents.Graph;
import GraphContents.Node;

import java.util.*;

public class AdjacencyListGraph<E> implements Graph<E> {
    Map<Node<E>, HashSet<Node<E>>> adjacencyList;
    Map<Node<E>, Map<Node<E>, Edge>> costs;
    Set<Edge<E>> edges;


    public AdjacencyListGraph() {
        adjacencyList = new HashMap<>();
        costs = new HashMap<>();
        edges = new HashSet<>();
    }

    public Set<Node<E>> getNodes() {
        return adjacencyList.keySet();
    }

    public Set<Edge<E>> getEdges() {
        return edges;
    }

    @Override
    public void addNode(Node<E> node) {
        adjacencyList.put(node, new HashSet<>());
        costs.put(node, new HashMap<>());
    }

    @Override
    public void addEdge(Node<E> start, Node<E> end) {
        checkNodesExists(start,end);

        addEdge(start,end, 0);
        //creates an edge by connecting two nodes to each other
        adjacencyList.get(start).add(end);
    }

    @Override
    public void addEdge(Node<E> start, Node<E> end, int cost) {
        checkNodesExists(start,end);

        Map<Node<E>, Edge> options = costs.get(start);
        Edge<E> edge = new Edge(start, end, cost);
        adjacencyList.get(start).add(end);

        options.put(end, edge);
        edges.add(edge);
    }

    @Override
    public void addTwoWayEdge(Node<E> start, Node<E> end) {
        checkNodesExists(start,end);
        addEdge(start, end);
        addEdge(end,start);
    }

    @Override
    public void addTwoWayEdge(Node<E> start, Node<E> end, int cost) {
        checkNodesExists(start,end);
        addEdge(start, end, cost);
        addEdge(end,start, cost);
    }

    @Override
    public Set<Node<E>> getNeighbors(Node<E> node) {
        checkNodesExists(node);
        return adjacencyList.get(node);
    }

    @Override
    public boolean isConnected(Node<E> start, Node<E> end) {
        checkNodesExists(start,end);
        return adjacencyList.get(start).contains(end);
    }

    //like getting an GraphContents.Edge edge = graph.getEdge(seattle, bellingham)
    //sout("it costs + edge.cost to go from +edge.start to edge.end")
    @Override
    public Edge<E> getEdge(Node<E> start, Node<E> end) {
        checkNodesExists(start,end);
        return costs.get(start).get(end);
    }

    private void checkNodesExists(Node<E> node1, Node<E> node2) {
        checkNodesExists(node1);
        checkNodesExists(node2);
    }

    // leverage method overloading and use plural naming even for the single node check
    // because it's way easier to write the same method name everywhere than to remember to
    // write either "node" vs "nodes" in "checkNodeExists" or "checkNodesExists"
    private void checkNodesExists(Node<E> node) {
        if(!adjacencyList.containsKey(node)){
            String msg = "Attempt to access node that is not in the graph. GraphContents.Node: " + node;
            throw new IllegalArgumentException(msg);
        }
    }
}

package GraphContents;

import GraphContents.Edge;
import GraphContents.Node;

import java.util.Set;

public interface Graph<E> {
    Set<Node<E>> getNodes();
    Set<Edge<E>> getEdges();

    void addNode(Node<E> node);

    // handy no-cost method should default cost to zero
    void addEdge(Node<E> start, Node<E> end);
    void addEdge(Node<E> start, Node<E> end, int cost);

    // handy no-cost method should default cost to zero
    void addTwoWayEdge(Node<E> start, Node<E> end);
    void addTwoWayEdge(Node<E> start, Node<E> end, int cost);

    Set<Node<E>> getNeighbors(Node<E> node);
    boolean isConnected(Node<E> start, Node<E> end);
    Edge<E> getEdge(Node<E> start, Node<E> end);
}

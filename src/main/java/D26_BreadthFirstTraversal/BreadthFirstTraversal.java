//SEE TEST FOR BREADTHFIRST TRAVERSAL METHOD _ VISIT ALL CITIES


//package D26_BreadthFirstTraversal;
//
//import GraphContents.AdjacencyListGraph;
//import GraphContents.Node;
//
//import java.util.*;
//
//public class BreadthFirstTraversal extends AdjacencyListGraph {
//
//    //help from Geeks for Geeks https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
//    //help from Tutorial Horizon https://algorithms.tutorialhorizon.com/breadth-first-searchtraversal-in-a-graph/
//
//    public void breadthFirstTraversal(Node startNode) {
//
//        //get data from start node and use it's int value to begin
//        //boolean evaluation of integers visited or not,
//        // because boolean won't accept Nodes, only int
//        //this is meant to help prevent duplicative visits to nodes already visted
//        int start = (int) startNode.getData();
//        boolean[] visited = new boolean[start];
//
//        //queue to hold visted nodes
//        LinkedList<Node> queue = new LinkedList<>();
//
//        //mark first node as visted and add it to the queue
//        visited[start] = true;
//        queue.add(startNode);
//
//        while (!queue.isEmpty()) {
//            startNode = queue.poll();
//            System.out.println(startNode.toString() + " ");
//
//            //get all adjacent nodes of the dequeued (polled) value
//            //if node hasn't been visited before, mark it as visited
//            //then enqueue (add) it
//            Iterator iterator = startNode.getNodes();
//
//        }
//
//    }
//}
//
//
////boolean helper method to test if node has been visited before to prevent
////duplicative visits to nodes in traversal method
////didn't work, but keeping in so I can learn more about why IDE
////is insisting on returning a node and not a boolean when the helper method
////is defined as a boolean
////    public boolean visited(){
////        Set test = new HashSet();
////
////        if(!test.contains(startNode)){
////            test.add(startNode);
////            return false;
////        }
////        return true;
////    }
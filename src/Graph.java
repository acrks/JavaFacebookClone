/**
 * Graph.java
 * @author Alex Crooks
 * @author Daniel Winkler
 * CIS 22C, Final Project
 */

import javax.crypto.spec.PSource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private int vertices;
    private int edges;
    private ArrayList<List<Integer>> adj;
    private ArrayList<Character> color;
    private ArrayList<Integer> distance;
    private ArrayList<Integer> parent;

    /**Constructors*/

    /**
     * initializes an empty graph, with n vertices
     * and 0 edges
     * @param n the number of vertices in the graph
     */
    public Graph(int n) {
        this.vertices = n + 1;
        this.edges = 0;

        adj = new ArrayList<>(n);
        color = new ArrayList<>();
        distance = new ArrayList<>();
        parent = new ArrayList<>();

        for (int i = 1; i <= vertices; i++) {
            List<Integer> l = new List<>();
            adj.add(l);
            color.add('W');
            distance.add(-1);
            parent.add(null);
        }
    }

    public void resizeGraph(int n){
        this.vertices += n;
        for (int i = (vertices - n); i <= vertices; i++) {
            List<Integer> l = new List<>();
            adj.add(l);
            color.add('W');
            distance.add(-1);
            parent.add(null);
        }
    }

    /*** Accessors ***/

    /**
     * Returns the number of edges in the graph
     * @return the number of edges
     */
    public int getNumEdges() {
        return this.edges;
    }

    /**
     * Returns the number of vertices in the graph
     * @return the number of vertices
     */
    public int getNumVertices() {
        return this.vertices;
    }

    /**
     * returns whether the graph is empty (no edges)
     * @return whether the graph is empty
     */
    public boolean isEmpty() {
        return edges == 0;
    }

    /**
     * Returns the value of the distance[v]
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the distance of vertex v
     * @throws IndexOutOfBoundsException when
     * the precondition is violated
     */
    public Integer getDistance(Integer v) throws IndexOutOfBoundsException{
        if(v < 0 || v >= vertices) {
            throw new IndexOutOfBoundsException("getDistance(): Cannot execute, index out of bounds");
        }
        return distance.get(v);
    }

    /**
     * Returns the value of the parent[v]
     * @param v a vertex in the graph
     * @precondition v <= vertices
     * @return the parent of vertex v
     * @throws IndexOutOfBoundsException when
     * the precondition is violated
     */
    public Integer getParent(Integer v) throws IndexOutOfBoundsException {
        if(v < 0 || v >= vertices) {
            throw new IndexOutOfBoundsException("getParent(): Cannot execute, index out of bounds");
        }
        return parent.get(v);
    }

    /**
     * Returns the value of the color[v]
     * @param v a vertex in the graph
     * @precondition 0< v <= vertices
            * @return the color of vertex v
            * @throws IndexOutOfBoundsException when
            * the precondition is violated
     */
    public Character getColor(Integer v) throws IndexOutOfBoundsException {
        if(v < 0 || v >= vertices) {
            throw new IndexOutOfBoundsException("getColor(): Cannot execute, index out of bounds");
        }
        return color.get(v);
    }

    /*** Mutators ***/

    /**
     * Inserts vertex v into the adjacency list of vertex u
     * (i.e. inserts v into the list at index u)
     * @precondition 0 < u, v <= vertices
     * @throws IndexOutOfBoundsException when the precondition
     * is violated
     */
    public void addDirectedEdge(Integer u, Integer v) throws IndexOutOfBoundsException {
        if(u < 0 || v > vertices) {
            throw new IndexOutOfBoundsException("addDirectedEdge(): Cannot execute, index out of bounds");
        }
        adj.get(u).addLast(v);
        this.edges++;
    }

    /**
     * Inserts vertex v into the adjacency list of vertex u
     * (i.e. inserts v into the list at index u)
     * and inserts u into the adjacent vertex list of v
     * @precondition 0 < u, v <= vertices
     *
     */
    public void addUndirectedEdge(Integer u, Integer v) {
        if(u < 0 || v > vertices) {
            throw new IndexOutOfBoundsException("addDirectedEdge(): Cannot execute, index out of bounds");
        }
        adj.get(u).addLast(v);
        adj.get(v).addLast(u);
        this.edges++;
    }

    /**
     * Removes vertex v from the adjacency list of vertex u
     * @precondition 0 < u, v <= vertices
     *
     */
    public void removeDirectedEdge(Integer u, Integer v){
        if(u < 0 || v > vertices) {
            throw new IndexOutOfBoundsException("removeDirectedEdge(): Cannot execute, index out of bounds");
        }
        adj.get(u).placeIterator();
        int num = adj.get(u).linearSearch(v);
        adj.get(u).iteratorToIndex(num);
        adj.get(u).removeIterator();
        this.edges--;
    }


    /*** Additional Operations ***/

    /**
     * Creates a String representation of the Graph
     * Prints the adjacency list of each vertex in the graph,
     * vertex: <space separated list of adjacent vertices>
     */
    @Override public String toString() {
        String result = "";

        for(int i = 1; i < vertices; i++) {
            adj.get(i).placeIterator();
            for(int x = 0; x < adj.get(i).getLength(); x++) {
                result += (adj.get(i).getIterator() + " ");
                adj.get(i).advanceIterator();
            }
            result += "\n";
        }
        return result;
    }



    /**
     * Prints the current values in the parallel ArrayLists
     * after executing BFS. First prints the heading:
     * v <tab> c <tab> p <tab> d
     * Then, prints out this information for each vertex in the graph
     * Note that this method is intended purely to help you debug your code
     */

    public void printBFS() {
        System.out.println("v\tc\tp\td");
        for (int i = 1; i < vertices; i++) {
            System.out.println(i + "\t" + color.get(i) + "\t" + parent.get(i) + "\t" + distance.get(i));
        }
    }

    /**
     * Recursive function to print the connection between suggestion and user
     * @param user recursive element that represents ID of user connected to source
     * @param source User ID to be passed in
     * @param userArrayList ArrayList containing the users who's data we use
     * @precondition 0 < user, user <= vertices
     * @precondition 0 < source, source <= vertices
     * @throws IndexOutOfBoundsException when the precondition
     * is violated
     */
    public String getConnection(int user, int source, ArrayList<User> userArrayList){
        if(user < 0 || user > vertices) {
            throw new IndexOutOfBoundsException("getConnection(): Cannot execute, user out of bounds");
        }
        if(source < 0 || source > vertices) {
            throw new IndexOutOfBoundsException("getConnection(): Cannot execute, source out of bounds");
        }
        if(getParent(user) == source){
            return " friend of you.";
        } else {
            return " friend of " + userArrayList.get(getParent(user)).getFirstName() + getConnection(getParent(user), source, userArrayList);
        }
    }

    /**
     * Performs breath first search on this Graph give a source vertex
     * @param source
     * @precondition graph must not be empty
     * @precondition source is a vertex in the graph
     * @throws IllegalStateException if the graph is empty
     * @throws IndexOutOfBoundsException when the source vertex
     * is not a vertex in the graph
     */

    public void BFS(Integer source) throws IllegalStateException, IndexOutOfBoundsException {
        if(isEmpty()){
            throw new IllegalStateException("Graph is empty");
        }

        if (source > vertices){
            throw new IndexOutOfBoundsException("source vertex is not a vertex in the graph");
        }
//        for all x in V(G)
        for(int i = 1; i < vertices; i++){
//            color[x] = white
            color.set(i, 'W');
//            distance[x] = -1
            distance.set(i, -1);
//            parent[x] = Nil
            parent.set(i, null);
        }
//        color[s] = grey
        color.set(source, 'G');
//        distance[s] = 0
        distance.set(source, 0);

        List<Integer> q = new List<>();
//        distance[s] = 0
        q.addLast(source);
//        while(Q is not empty)
        while (!q.isEmpty()){
//            x = front of Q
            int x = q.getFirst();
//            Dequeue(Q,x)
            q.removeFirst();
//            for all y in adj[x]
            adj.get(x).placeIterator();
            for(int i = 1; i <= adj.get(x).getLength(); i++){
                int y = adj.get(x).getIterator();
//                    if color[y] == white
                if (color.get(y) == 87){
//                    color[y] = grey
                    color.set(y, 'G');
//                    distance[y] = distance[x] + 1
                    distance.set(y, distance.get(x)+1);
//                    parent[y] = x
                    parent.set(y, x);
//                    Enqueue(Q, y)
                    q.addLast(y);
                }
                adj.get(x).advanceIterator();
            }
//            color[x] = black
            color.set(x, 'B');
        }
    }
}

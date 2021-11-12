public class GraphTester {
    public static void main(String[] args) {
        Graph g = new Graph(9);
//        System.out.println(g.adj);
        g.addDirectedEdge(2,3);
//        System.out.println(g.adj);
        g.addDirectedEdge(2,1);
        g.addDirectedEdge(2,4);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);
        g.addDirectedEdge(3, 4);
        g.addDirectedEdge(4,9);
        g.addDirectedEdge(5,6);
        g.addDirectedEdge(8,7);
//        System.out.println(g.adj);
        g.BFS(1);
        System.out.println(g.toString());
        g.printBFS();

    }
}

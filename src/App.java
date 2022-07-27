public class App {
    public static void main(String[] args) throws Exception {
        Graph notDirectedGraph = new Graph("graphs//Graph_1.csv");
        Graph directedGraph = new Graph("graphs//Graph_2.csv");

        System.out.println(notDirectedGraph.getNotDirectedEdges());
        System.out.println(directedGraph.getNotDirectedEdges());
    }
}
public class App {
    public static void main(String[] args) throws Exception {
        Graph simpleGraph = new Graph("graphs//Graph_1.csv");
        Graph mixedGraph = new Graph("graphs//Graph_2.csv");

        mixedGraph.info();
    }
}
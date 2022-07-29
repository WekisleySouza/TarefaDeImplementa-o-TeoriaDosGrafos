public class App {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph("graphs//Graph_3.csv");
        // IncidencyMatrix teste = new IncidencyMatrix(graph);
        // System.out.println(teste.getMatrix());
        graph.info();
    }
}
public class AdjacencyMatrix extends Matrix{

    public AdjacencyMatrix(Graph graph){
        this.matrix = new int[graph.getOrder()][graph.getOrder()];
        this.preencher(graph);
    }

    private void preencher(Graph graph){
        for(Edge edge : graph.getEdges()){
            this.matrix[edge.getPreviousVertexLabel()][edge.getNextVertexLabel()] = edge.getLength();
        }
    }

    // Getters and Setters:
    public int[][] getMatrix(){ return this.matrix; }
}
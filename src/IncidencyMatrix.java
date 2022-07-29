public class IncidencyMatrix extends Matrix{

    public IncidencyMatrix(Graph graph){
        this.matrix = new int[graph.getOrder()][graph.getSize()];
        this.preencher(graph);
    }

    private void preencher(Graph graph){
        for(Edge edge : graph.getEdges()){
            this.matrix[edge.getPreviousVertexLabel()][edge.getLabel()] = edge.getLength();
            this.matrix[edge.getNextVertexLabel()][edge.getLabel()] = (-1 * edge.getLength());
        }
    } 

    // Getters and Setters:
    public int[][] getMatrix(){ return this.matrix; }
}

import java.util.ArrayList;
import java.util.List;

public class IncidencyMatrix extends Matrix{

    public IncidencyMatrix(Graph graph){
        this.matrix = new int[graph.getOrder()][graph.getOrder()];
        this.preencher(graph);
    }

    private void preencher(Graph graph){
        for(Edge edge : graph.getEdges()){
            this.matrix[edge.getPreviousVertexLabel()-1][edge.getNextVertexLabel()-1] = edge.getLength();
        }
    }

    public List<String> findSpanningTree(){
        List<String> edges = new ArrayList<String>();
        List<Integer> vertices = new ArrayList<Integer>();
        lookTreeEdges(edges, vertices);
        return edges; 
    }

    private void lookTreeEdges(List<String> edges, List<Integer> vertices){
        for(int i = 0; i < matrix.length; i++){
            for(int j = correctIndexAboveDiagonal(i); j < matrix[0].length; j++){
                if((!vertices.contains(j) || !vertices.contains(i)) && matrix[i][j] == 1){
                    edges.add(Integer.toString(i) + " - " + Integer.toString(j));
                    vertices.add(j);
                }
            }
        }
    }

    private int correctIndexAboveDiagonal(int index){
        return index + 1;
    }

    // Getters and Setters:
    public int[][] getMatrix(){ return this.matrix; }
}

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Edge> edges;
    private List<Vertex> vertices;
    private String path;

    public Graph(String path){
        this.path = path;
        this.edges = new ArrayList<Edge>();
        this.vertices = new ArrayList<Vertex>();
        this.addEdges(this.path);
    }

    private void addEdges(String path){
        String[] stringVerticesPairs = this.readStringEdges(path);
        for(String pair : stringVerticesPairs){
            this.edges.add(this.stringToEdge(pair));
        }
    }

    private String[] readStringEdges(String path){
        String[] text = File.readFile(path).split("\n");
        return text;
    }

    private Edge stringToEdge(String text){
        String[] vertices =  text.split(";");
        Vertex vertex1 = new Vertex(Integer.parseInt(vertices[0]));
        Vertex vertex2 = new Vertex(Integer.parseInt(vertices[1]));
        return (Edge) new Edge(this.generateEdgeLabel(), vertex1, vertex2);
    }

    private int generateEdgeLabel(){
        return (int) this.edges.size();
    }

    @Override
    public String toString(){
        return "G(" + this.vertices.size() + ", " + this.edges.size() + ")";
    }
}

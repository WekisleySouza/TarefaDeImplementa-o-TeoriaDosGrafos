import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Edge> edges;
    private List<Edge> notDirectedEdges;
    private List<Edge> directedEdges;
    private List<Vertex> vertices;
    private String path;
    
    public Graph(String path){
        this.path = path;
        this.edges = new ArrayList<Edge>();
        this.notDirectedEdges = new ArrayList<Edge>();
        this.directedEdges = new ArrayList<Edge>();
        this.vertices = new ArrayList<Vertex>();
        this.addEdges(this.path);
        this.separateEdgesByType();
        addVertices();
    }

    private void addVertices(){
        for(Edge edge : this.edges){
            addVerticesFromEdge(edge);
        }
    } 

    private void addVerticesFromEdge(Edge edge){
        addVertexFromEdgeIfNotExists(edge.getVertex1());
        addVertexFromEdgeIfNotExists(edge.getVertex2());
    }

    private void addVertexFromEdgeIfNotExists(Vertex vertex){
        if(!vertex.existsInList(this.vertices)){
            this.vertices.add(vertex);
        }
    }

    public void info(){
        printTypeOfGraph();
        System.out.println("É um grafo de ordem " + this.vertices.size() + ".");
        System.out.println("É um grafo de tamanho " + (this.notDirectedEdges.size() + this.directedEdges.size()) + ".");
    }

    private void printTypeOfGraph(){
        if(isDirectedGraph()){
            System.out.println("É um grafo direcionado!");
        }else if(isSimpleGraph()){
            System.out.println("É um grafo simples!");
        }else if(isMixedGraph()){
            System.out.println("É um grafo misto!");
        }
    }

    private boolean isMixedGraph(){
        return (!this.isDirectedGraph() && !this.isSimpleGraph())? true : false;
    }

    private boolean isDirectedGraph(){
        return (this.edges.size() == this.directedEdges.size())? true : false;
    }

    private boolean isSimpleGraph(){
        return (this.edges.size() == this.notDirectedEdges.size())? true : false;
    }

    private void separateEdgesByType(){
        for(Edge edge : this.edges){
            this.addEdgeByType(edge);
        }
    }

    private void addEdgeByType(Edge edge){
        if(edge.reverseIsInList(this.edges)){
            this.notDirectedEdges.add(edge);

        }else if(!edge.reverseIsInList(this.notDirectedEdges)){
            this.directedEdges.add(edge);
        }
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
    
    // Getters and Setters
    public List<Edge> getDirectedEdges() { return directedEdges; }
    
    public void setDirectedEdges(List<Edge> directedEdges) { this.directedEdges = directedEdges; }
    
    public List<Vertex> getVertices() { return vertices; }
    
    public void setVertices(List<Vertex> vertices) { this.vertices = vertices; }
    
    public String getPath() { return path; }
    
    public void setPath(String path) { this.path = path; }
    
    public List<Edge> getEdges() { return edges; }
    
    public void setEdges(List<Edge> edges) { this.edges = edges; }
    
    public List<Edge> getNotDirectedEdges() { return notDirectedEdges; }
    
    public void setNotDirectedEdges(List<Edge> notDirectedEdges) { this.notDirectedEdges = notDirectedEdges; }
}

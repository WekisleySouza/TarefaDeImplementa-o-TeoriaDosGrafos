import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Edge> edges;
    private List<Edge> notDirectedEdges;
    private List<Edge> directedEdges;
    private List<Vertex> vertices;
    private List<Vertex> leaves;
    private List<Way> ways;
    private String path;
    
    public Graph(String path){
        this.path = path;
        this.edges = new ArrayList<Edge>();
        this.notDirectedEdges = new ArrayList<Edge>();
        this.directedEdges = new ArrayList<Edge>();
        this.vertices = new ArrayList<Vertex>();
        this.ways = new ArrayList<Way>();
        this.leaves = new ArrayList<Vertex>();
        this.addEdges(this.path);
        this.separateEdgesByType();
        addVertices();
        updateArrowEdgesStatus();
        updateVertexConnections();
        updateLeafVertices();
    }

    
    public void info(){
        System.out.println("======================================================================");
        System.out.println("================================= Grafo ==============================");
        System.out.println("======================================================================");
        printTypeOfGraph();
        printIsRegular();
        printIsPair();
        System.out.println("É um grafo de ordem " + this.getOrder() + ".");
        System.out.println("É um grafo de tamanho " + (this.notDirectedEdges.size() + this.directedEdges.size()) + ".");
        printLeavesVertices();
        printVerticesDegree();
        System.out.println("======================================================================");
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

    private void printVerticesDegree(){
        System.out.println("-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/");
        for(Vertex vertex : this.vertices){
            System.out.println("Grau do vértice " + vertex.getLabel() + ": " + vertex.getDegree());
        }
        System.out.println("-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/");
    }

    private void printIsRegular(){
        if(isRegular()){
            System.out.println("É um grafo regular!");
        }else{
            System.out.println("É um grafo irregular!");
        }
    }

    private void printIsPair(){
        if(isPair()){
            System.out.println("É um grafo par!");
        }else{
            System.out.println("Não é um grafo par!");
        }
    }

    private void printLeavesVertices(){
        System.out.print("Folhas do grafo: ");
        for(int i = 0; i < this.leaves.size(); i++){
            System.out.print(this.leaves.get(i));
            if(i != this.leaves.size() - 1){
                System.out.print(", ");
            }else{
                System.out.println(".");
            }
        }
    }

    private void updatePreviousVertexConnection(int vertexIndex, int edgeLabel){
        if(vertexIndex != -1){
            this.vertices.get(vertexIndex).addPreviousEdgeLabel(edgeLabel);
        }
    }

    private void updateNextVertexConnection(int vertexIndex, int edgeLabel){
        if(vertexIndex != -1){
            this.vertices.get(vertexIndex).addPreviousEdgeLabel(edgeLabel);
        }
    }
    
    private void updateVertexConnections(){
        for(Edge edge : this.directedEdges){
            updatePreviousVertexConnection(edge.previousVertexIndexInList(this.vertices), edge.getLabel());
            updateNextVertexConnection(edge.nextVertexIndexInList(this.vertices), edge.getLabel());
        }
        for(Edge edge : this.notDirectedEdges){
            updatePreviousVertexConnection(edge.previousVertexIndexInList(this.vertices), edge.getLabel());
            updateNextVertexConnection(edge.nextVertexIndexInList(this.vertices), edge.getLabel());
        }
    }

    private void updateLeafVertices(){
        for(Vertex vertex : this.vertices){
            if(vertex.isLeaf()){
                this.leaves.add(vertex);
            }
        }
    }
    
    private void addVertices(){
        for(Edge edge : this.edges){
            addVerticesFromEdge(edge);
        }
    } 
    
    private void addVerticesFromEdge(Edge edge){
        addVertexFromEdgeIfNotExists(edge.getPreviousVertex());
        addVertexFromEdgeIfNotExists(edge.getNextVertex());
    }

    private void addVertexFromEdgeIfNotExists(Vertex vertex){
        if(!vertex.existsInList(this.vertices)){
            this.vertices.add(vertex);
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

    private boolean isPair(){
        for(Vertex vertex : this.vertices){
            if(vertex.getDegree() % 2 != 0){
                return false;
            }
        }
        return true;
    }

    private boolean isRegular(){
        int vertexConecttions = this.vertices.get(0).getDegree();
        for(Vertex vertex : this.vertices){
            if(vertex.getDegree() != vertexConecttions){
                return false;
            }
        }
        return true;
    }

    private void updateArrowEdgesStatus(){
        for(Edge edge : this.directedEdges){
            edge.setArrow(true);
        }
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
    public int getOrder(){ return this.vertices.size(); }

    public int getSize(){ return this.notDirectedEdges.size() + this.directedEdges.size(); }
    
    public List<Vertex> getVertices() { return vertices; }
    
    public void setVertices(List<Vertex> vertices) { this.vertices = vertices; }
    
    public String getPath() { return path; }
    
    public void setPath(String path) { this.path = path; }
    
    public List<Edge> getEdges() { return edges; }
    
    public void setEdges(List<Edge> edges) { this.edges = edges; }
    
    public List<Edge> getDirectedEdges() { return directedEdges; }
        
    public void setDirectedEdges(List<Edge> directedEdges) { this.directedEdges = directedEdges; }
    
    public List<Edge> getNotDirectedEdges() { return notDirectedEdges; }
    
    public void setNotDirectedEdges(List<Edge> notDirectedEdges) { this.notDirectedEdges = notDirectedEdges; }
}

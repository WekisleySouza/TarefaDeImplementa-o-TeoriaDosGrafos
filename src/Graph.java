import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Edge> edges;
    private List<Edge> notDirectedEdges;
    private List<Edge> directedEdges;
    private List<Vertex> vertices;
    private List<Vertex> leaves;
    private String path;
    IncidencyMatrix matrix;

    public Graph(){

    }
    
    public Graph(String path){
        this.path = path;
        this.edges = new ArrayList<Edge>();
        this.notDirectedEdges = new ArrayList<Edge>();
        this.directedEdges = new ArrayList<Edge>();
        this.vertices = new ArrayList<Vertex>();
        this.leaves = new ArrayList<Vertex>();
        this.addEdges(this.path);
        this.separateEdgesByTypeOf(this.edges);
        addVertices();
        updateArrowEdgesStatus();
        updateVertexConnections();
        updateLeafVertices();
        this.matrix  = new IncidencyMatrix(this);
    }
    
    public void info(){
        System.out.println("======================================================================");
        System.out.println("================================= Grafo ==============================");
        System.out.println("======================================================================");
        printTypeOfGraph();
        printIsRegular();
        printIsPair();
        printCompleteGraph();
        printExtremeVertices();
        printIsPlanar();
        System.out.println("É um grafo de ordem " + this.getOrder() + ".");
        System.out.println("É um grafo de tamanho " + (this.notDirectedEdges.size() + this.directedEdges.size()) + ".");
        printSpanningTree();
        printLeavesVertices();
        printVerticesDegree();
        System.out.println("======================================================================");
    }

    private void printSpanningTree(){
        String text = "Spanning tree edges: ";
        List<Edge> edges = stringSpanningTreeToEdges();
        for(int i = 1; i < edges.size(); i++){
            text += edges.get(i - 1);
            if(i % 6 == 0){
                text += "\n";
            }
        }
        System.out.println(text);
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
        if(this.leaves.size() != 0){
            printLeaves();
        }
    }

    private void printLeaves(){
        System.out.print("Folhas do grafo: ");
        for(int i = 0; i < this.leaves.size(); i++){
            System.out.print(this.leaves.get(i) + verifiedStringToSeparateLeaves(i));
        }
    }

    private void printIsPlanar(){
        if(this.isPlanar()){
            System.out.println("É um grafo planar!");
        }else{
            System.out.println("Não é um grafo planar!");
        }
    }
    
    private String verifiedStringToSeparateLeaves(int index){
        if(index != this.leaves.size() - 1){
            return ", ";
        }
        return ".\n";
    }

    private void printCompleteGraph(){

        if(isCompleteGraph()){
            System.out.println("É um grafo completo!");
        }else{
            System.out.println("Não é um grafo completo!");
        }
    }
    
    private void printExtremeVertices(){
        if(extremeVerticesExists()){
            System.out.println("Existem vértices extremos!");
        }else{
            System.out.println("Não existem vértices extremos!");
        }
    }

    private List<Edge> stringSpanningTreeToEdges(){
        List<Edge> spanningEdges = new ArrayList<Edge>();
        for(String edge : this.matrix.findSpanningTree()){
            String[] vertices = edge.split(" - ");
            spanningEdges.add(new Edge(new Vertex(correctVertex(vertices[0])), new Vertex(correctVertex(vertices[1]))));
        }
        return spanningEdges;
    }

    private int correctVertex(String label){
        return Integer.parseInt(label) + 1;
    }
    
    private void updatePreviousVertexConnection(int vertexIndex, int edgeLabel){
        if(vertexIndex != -1){
            this.vertices.get(vertexIndex).addPreviousEdgeLabel(edgeLabel);
        }
    }

    private void updateNextVertexConnection(int vertexIndex, int edgeLabel){
        if(vertexIndex != -1){
            this.vertices.get(vertexIndex).addNextEdgeLabel(edgeLabel);
        }
    }
    
    private void updateVertexConnections(){
        updateVertexConnectionFrom(this.notDirectedEdges);
        updateVertexConnectionFrom(this.directedEdges);
    }

    private void updateVertexConnectionFrom(List<Edge> edges){
        for(Edge edge : edges){
            updatePreviousVertexConnection(edge.nextVertexIndexInList(this.vertices), edge.getLabel());
            updateNextVertexConnection(edge.previousVertexIndexInList(this.vertices), edge.getLabel());
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
        addVertexIfNotExistsFrom(edge.getPreviousVertex());
        addVertexIfNotExistsFrom(edge.getNextVertex());
    }

    private void addVertexIfNotExistsFrom(Vertex vertex){
        if(!vertex.existsInList(this.vertices)){
            this.vertices.add(vertex);
        }
    }

    private boolean extremeVerticesExists(){
        return (this.leaves.size() == 2)? true : false;
    }

    private boolean isCompleteGraph(){
        int edgesNumberForComplete = (this.vertices.size() * (this.vertices.size() - 1)) / 2;
        return (edgesNumberForComplete == this.edgesNumber())? true : false;
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

    private boolean isPlanar(){
        int maxEdgesToPLanar = (3 * verticesNumber()) - 6;
        return (this.edgesNumber() <= maxEdgesToPLanar)? true : false;
    }

    private void updateArrowEdgesStatus(){
        for(Edge edge : this.directedEdges){
            edge.setArrow(true);
        }
    }

    private void separateEdgesByTypeOf(List<Edge> edgesList){
        for(Edge edge : edgesList){
            addInDirectedEdgesList(new Edge(edge));
        }
        for(Edge edge : edgesList){
            addInNotDirectedEdgesList(new Edge(edge));
        }
        removeAditionalDirectedEdges();
    }

    private void removeAditionalDirectedEdges(){
        int nonRepetitionNotDirectedEdges = (this.notDirectedEdges.size()) / 2;
        int size = this.notDirectedEdges.size();
        for(int i = size - 1; i >= nonRepetitionNotDirectedEdges; i--){
            this.notDirectedEdges.remove(i);
        }
    }
    
    private void addInDirectedEdgesList(Edge edge){
        if(edge.reverseIsInList(this.edges) && !edge.reverseIsInList(this.directedEdges) ){
            this.notDirectedEdges.add(edge);
        }
    }

    private void addInNotDirectedEdgesList(Edge edge){
        if(!edge.reverseIsInList(this.notDirectedEdges)){
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

    public int verticesNumber(){
        return (int) this.vertices.size();
    }

    public int edgesNumber(){
        return (int) this.directedEdges.size() + this.notDirectedEdges.size();
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

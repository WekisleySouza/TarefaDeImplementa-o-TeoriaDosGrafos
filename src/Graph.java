import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Edge> edges;
    private List<Edge> notDirectedEdges;
    private List<Edge> directedEdges;
    private List<Vertex> vertices;
    private List<Vertex> leaves;
    private String path;
    private List<Way> ways;
    
    public Graph(String path){
        this.path = path;
        this.edges = new ArrayList<Edge>();
        this.notDirectedEdges = new ArrayList<Edge>();
        this.directedEdges = new ArrayList<Edge>();
        this.vertices = new ArrayList<Vertex>();
        this.leaves = new ArrayList<Vertex>();
        this.ways = new ArrayList<Way>();
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
        printCompleteGraph();
        printExtremeVertices();
        printIsPlanar();
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

    public void test(){
        System.out.println(searchWays(this.vertices.get(0), this.vertices.get(4)));
    }

    private List<Way> searchWays(Vertex beginVertex, Vertex endVertex){
        List<Way> ways = new ArrayList<Way>(getStepsFrom(beginVertex));
        List<Way> possibleWays = new ArrayList<Way>();
        for(Way way : ways){
            List<Way> possibleSteps = getStepsFrom(way.getEndVertex());
            for(Way possibleStep : possibleSteps){
                Way actualWay = new Way(way);
                if(stepIsValid(way, possibleStep, endVertex)){
                    actualWay.addStep(possibleStep.getEndVertex(), possibleStep.getLength());
                    possibleWays.add(actualWay);
                }
            }
        }
        return possibleWays;
    }

    private boolean stepIsValid(Way actualWay, Way possibleStep, Vertex endVertex){
        return (!wayContains(actualWay, possibleStep) && stepIsSequence(actualWay, possibleStep))? true : false;
    }

    private boolean stepIsSequence(Way way, Way possibleStep){
        return (possibleStep.getBeginVertex().equals(way.endVertex))? true : false;
    }

    private boolean wayContains(Way way, Way possibleStep){
        return (way.getWay().contains(possibleStep.getEndVertex().getLabel()))? true : false;
    }

    private boolean isPreviousStep(Way possibleStep, Way way){
        return (possibleStep.getEndVertex().getLabel() != way.getWay().get(way.getWay().size()-2))? true : false;
    }

    private List<Way> getStepsFrom(Vertex actualVertex){
        List<Way> ways = new ArrayList<Way>();
        List<Integer> edgeLabel = actualVertex.getNextEdgeLabel();
        for(int label : edgeLabel){
            ways.add(newWay(actualVertex, label));
        }
        return ways;
    }

    private Way newWay(Vertex actualVertex, int label){
        Edge edge = findEdge(label);
        Vertex nextVertex = findVertex(edge.getNextVertexLabel());
        return new Way(actualVertex, nextVertex, edge.getLength());
    }
    
    private Edge findEdge(int edgeLabel){
        for(Edge edge : this.edges){
            if(edge.getLabel() == edgeLabel){
                return edge;
            }
        }
        return null;
    }
    
    private Vertex findVertex(int vertexLabel){
        for(Vertex vertex : this.vertices){
            if(vertex.getLabel() == vertexLabel){
                return vertex;
            }
        }
        return null;
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

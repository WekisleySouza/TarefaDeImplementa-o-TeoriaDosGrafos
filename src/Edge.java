import java.util.List;

public class Edge{
    private int label;
    private Vertex previousVertex;
    private Vertex nextVertex;
    private int length;
    private boolean arrow;
    
    public boolean isArrow() {
        return arrow;
    }

    public void setArrow(boolean arrow) {
        this.arrow = arrow;
    }

    public Edge(Edge edge){
        this.previousVertex = edge.getPreviousVertex();
        this.nextVertex = edge.getNextVertex();
        this.label = edge.getLabel();
        this.length = edge.getLength();
        this.arrow = edge.isArrow();
    }

    public Edge(Vertex a, Vertex b){
        this.previousVertex = a;
        this.nextVertex = b;
        this.length = 1;
    }
    
    public Edge(int label, Vertex a, Vertex b){
        this.label = label;
        this.previousVertex = a;
        this.nextVertex = b;
        this.length = 1;
    }
    
    public Edge(int label, Vertex a, Vertex b, int length){
        this.label = label;
        this.previousVertex = a;
        this.nextVertex = b;
        this.length = length;
    }

    public int previousVertexIndexInList(List<Vertex> vertices){
        return (int) getVertexIndexByLabel(this.getPreviousVertexLabel(), vertices);
    }

    public int nextVertexIndexInList(List<Vertex> vertices){
        return (int) getVertexIndexByLabel(this.getNextVertexLabel(), vertices);
    }

    private int getVertexIndexByLabel(int label, List<Vertex> vertices){
        for(int i = 0; i < vertices.size(); i++){
            if(vertices.get(i).getLabel() == label){
                return i;
            }
        }
        return -1;
    }
    
    public boolean isLoop() {
        return (previousVertex == nextVertex)? true : false;
    }

    public boolean isInList(List<Edge> edgesList){
        for(Edge edge : edgesList){
            if(edge.getLabel() == this.label){
                return true;
            }
        }
        return false;
    }
    
    public boolean equals(Edge otherEdge){
        return (this.equalsVerifyOrderCase(otherEdge) || this.equalsVerifyInvertCase(otherEdge))? true : false;
    }
    
    private boolean equalsVerifyOrderCase(Edge otherEdge){
        boolean vertex1EqualsOtherVertex1 = this.previousVertex.equals(otherEdge.getPreviousVertex());
        boolean vertex2EqualsOtherVertex2 = this.nextVertex.equals(otherEdge.getNextVertex());
        
        return (vertex1EqualsOtherVertex1 && vertex2EqualsOtherVertex2)? true : false;
    }

    private boolean equalsVerifyInvertCase(Edge otherEdge){
        boolean vertex1EqualsOtherVertex2 = this.previousVertex.equals(otherEdge.getNextVertex());
        boolean vertex2EqualsOtherVertex1 = this.nextVertex.equals(otherEdge.getPreviousVertex());
        
        return (vertex1EqualsOtherVertex2 && vertex2EqualsOtherVertex1)? true : false;
    }

    public boolean reverseIsInList(List<Edge> edgesList){
        for(Edge nowEdge : edgesList){
            if(this.isReverseWith(nowEdge)){
                return true;
            }
        }
        return false;
    }
    
    public Edge Reverse(){
        return (Edge) new Edge(this.label, this.nextVertex, this.previousVertex);
    }
    
    public boolean isReverseWith(Edge otherEdge){
        return (this.equalsVerifyInvertCase(otherEdge))? true : false;
    }

    @Override
    public String toString(){
        return "(" + this.previousVertex.getLabel() + " -> " + this.nextVertex.getLabel() + ")"; 
    }
    
    //GETTERS AND SETTERS:
    public Vertex getPreviousVertex() { return previousVertex; }

    public void setPreviousVertex(Vertex vertex) { this.previousVertex = vertex; }

    public Vertex getNextVertex() { return nextVertex; }

    public void setNextVertex(Vertex vertex) { this.nextVertex = vertex; }

    public int getNextVertexLabel(){ return this.nextVertex.getLabel(); }

    public int getPreviousVertexLabel(){ return this.previousVertex.getLabel(); }
    
    public int getLength() { return length; }
    
    public void setLength(int length) { this.length = length; }

    public int getLabel() { return label; }

    public void setLabel(int label) { this.label = label; }
}

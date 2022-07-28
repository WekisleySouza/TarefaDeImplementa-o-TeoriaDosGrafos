import java.util.List;

public class Edge{
    private int label;
    private Vertex vertex1;
    private Vertex vertex2;
    private int length;
    
    public Edge(Vertex a, Vertex b){
        this.vertex1 = a;
        this.vertex2 = b;
        this.length = 1;
    }
    
    public Edge(int label, Vertex a, Vertex b){
        this.label = label;
        this.vertex1 = a;
        this.vertex2 = b;
        this.length = 1;
    }
    
    public Edge(int label, Vertex a, Vertex b, int length){
        this.label = label;
        this.vertex1 = a;
        this.vertex2 = b;
        this.length = length;
    }
    
    public boolean isLoop() {
        return (vertex1 == vertex2)? true : false;
    }

    public Edge Reverse(){
        return (Edge) new Edge(this.label, this.vertex2, this.vertex1);
    }
    
    public boolean isReverseWith(Edge otherEdge){
        return (this.equalsVerifyInvertCase(otherEdge))? true : false;
    } 

    public boolean equals(Edge otherEdge){
        return (this.equalsVerifyOrderCase(otherEdge))? true : false;
    }

    private boolean equalsVerifyOrderCase(Edge otherEdge){
        boolean vertex1EqualsOtherVertex1 = this.vertex1.equals(otherEdge.getVertex1());
        boolean vertex2EqualsOtherVertex2 = this.vertex2.equals(otherEdge.getVertex2());
        
        return (vertex1EqualsOtherVertex1 && vertex2EqualsOtherVertex2)? true : false;
    }

    private boolean equalsVerifyInvertCase(Edge otherEdge){
        boolean vertex1EqualsOtherVertex2 = this.vertex1.equals(otherEdge.getVertex2());
        boolean vertex2EqualsOtherVertex1 = this.vertex2.equals(otherEdge.getVertex1());
        
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

    @Override
    public String toString(){
        return Integer.toString(this.label); 
    }
    
    //GETTERS AND SETTERS:
    public Vertex getVertex1() { return vertex1; }

    public void setVertex1(Vertex vertex1) { this.vertex1 = vertex1; }

    public Vertex getVertex2() { return vertex2; }

    public void setVertex2(Vertex vertex2) { this.vertex2 = vertex2; }
    
    public int getLength() { return length; }
    
    public void setLength(int length) { this.length = length; }

    public int getLabel() { return label; }

    public void setLabel(int label) { this.label = label; }
}
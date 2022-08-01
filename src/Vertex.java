import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private int label;
    private List<Integer> nextEdgeLabel;
    private List<Integer> previousEdgeLabel;
    
    public Vertex(int label){
        this.label = label;
        this.previousEdgeLabel = new ArrayList<Integer>();
        this.nextEdgeLabel = new ArrayList<Integer>();
    }
    
    public boolean equals(Vertex otherVertex){
        if(this.label == otherVertex.getLabel()){
            return true;
        }
        return false;
    }

    public boolean existsInList(List<Vertex> vertices){
        for(Vertex currentVertex : vertices){
            if(this.equals(currentVertex)){
                return true;
            }
        }
        return false;
    }

    private int previousEdgeQuantity(){
        return (int) this.previousEdgeLabel.size();
    }

    private int nextEdgeQuantity(){
        return (int) this.nextEdgeLabel.size();
    }

    public boolean isLeaf(){
        return (nextEdgeQuantity() + previousEdgeQuantity() == 1)? true : false;    
    }
    
    @Override
    public String toString(){
        return Integer.toString(this.label);
    }
    
    // Getters and Setters:
    public int getLabel(){ return this.label; }
    
    public void setLabel(int label) { this.label = label; }

    public List<Integer> getNextEdgeLabel() { return nextEdgeLabel; }
    
    public void setNextEdgeLabel(List<Integer> nextEdgeLabel) { this.nextEdgeLabel = nextEdgeLabel; }
    
    public List<Integer> getPreviousEdgeLabel() { return previousEdgeLabel; }
    
    public void PreviousEdgeLabel(List<Integer> previousEdgeLabel) { this.previousEdgeLabel = previousEdgeLabel; }

    public void addPreviousEdgeLabel(int label){ this.previousEdgeLabel.add(label); }

    public void addNextEdgeLabel(int label){ this.nextEdgeLabel.add(label); }

    public int getDegree(){ return (int) (nextEdgeLabel.size() + previousEdgeLabel.size()); }
}

import java.util.List;

public class Vertex {
    private int label;

    
    public Vertex(int label){
        this.label = label;
    }

    public boolean equals(Vertex otherVertex){
        if(this.label == otherVertex.getLabel()){
            return true;
        }
        return false;
    }

    public boolean existsInList(List<Vertex> vertices){
        for(Vertex nowVertex : vertices){
            if(this.equals(nowVertex)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return Integer.toString(this.label);
    }
    
    public int getLabel(){ return this.label; }
    
    public void setLabel(int label) { this.label = label; }
}

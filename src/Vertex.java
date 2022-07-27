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

    @Override
    public String toString(){
        return Integer.toString(this.label);
    }

    public int getLabel(){ return this.label; }
}

import java.util.ArrayList;
import java.util.List;

public class Way {
    List<Integer> way;
    Vertex beginVertex;
    Vertex endVertex;
    int length;
    
    public Way(Way otherWay){
        this.way = new ArrayList<Integer>(otherWay.getWay());
        this.beginVertex = otherWay.getBeginVertex();
        this.endVertex = otherWay.getEndVertex();
        this.length = otherWay.getLength();
    }

    public Way(Vertex begin, Vertex end, int lenght){
        this.way = new ArrayList<Integer>();
        this.beginVertex = begin;
        if(stepIsValid(end)){ this.endVertex = end; }
        this.way.add(begin.getLabel());
        this.way.add(end.getLabel());
        this.length = 0;
    }
    
    @Override
    public String toString(){
        String way = "( ";
        for(int i = 0; i < this.way.size(); i++){
            way += Integer.toString(this.way.get(i)) + verifyToAddString(i);
        }
        return way;
    }

    public boolean contains(int vertexLabel){
        return (way.contains(vertexLabel))? true : false;
    }

    private String verifyToAddString(int index){
        return (index != this.way.size() - 1)? " --> " : " )";
    }

    public void joinWay(Way otherWay, int otherLength){
        for(int vertex : otherWay.getWay()){
            verifyToJoin(vertex);
        }
        this.length += otherLength;
    }

    private void verifyToJoin(int vertex){
        if(!this.way.contains(vertex)){
            this.way.add(vertex);
        }
    }
    
    public void addStep(Vertex destin, int length){
        updateWay(destin, length);
    }

    private void updateWay(Vertex destin, int length){
        this.way.add(destin.getLabel());
        this.endVertex = destin;
        this.length += length;
    }

    private boolean stepIsValid(Vertex destin){
        return (!this.beginVertex.equals(destin))? true : false;
    }

    public boolean equals(Way otherWay){
        return this.way.equals(otherWay.getWay());
    }

    // Setters and Getters
    public List<Integer> getWay(){
        return this.way;
    }

    public int getWayLength(){
        return (int) this.way.size();
    }
    
    public int getLength() {
        return this.length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public Vertex getEndVertex() {
        return endVertex;
    }
    
    public void setEndVertex(Vertex endVertex) {
        this.endVertex = endVertex;
    }
    
    public Vertex getBeginVertex() {
        return beginVertex;
    }

    public void setBeginVertex(Vertex beginVertex) {
        this.beginVertex = beginVertex;
    }
}

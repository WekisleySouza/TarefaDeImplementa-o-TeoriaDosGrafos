import java.util.ArrayList;
import java.util.List;

public class Way {
    List<Integer> way;
    
    public Way(){
        this.way = new ArrayList<Integer>();
    }

    @Override
    public String toString(){
        String way = "";
        for(int vertex : this.way){
            way += Integer.toString(vertex);
        }
        return way;
    }

    // Setters and Getters
    public List<Integer> getWay(){
        return this.way;
    }

    public void addStep(int step){
        this.way.add(step);
    }
}

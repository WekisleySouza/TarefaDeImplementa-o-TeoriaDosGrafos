import java.util.List;

public class AdjacencyMatrix extends Matrix{

    public AdjacencyMatrix(String path){
        this.matrix = readMatrixFromCsv(path);
    }

    private int[][] readMatrixFromCsv(String path){
        String[] file = File.readFile(path).split("\n");
        return graphFromStrings(file);
    }

    private int[][] graphFromStrings(String[] strings){
        for(String str : strings){

        }
        return matrix;
    }

    // Getters and Setters:
    public int[][] getMatrix(){ return this.matrix; }
}
public class Matrix {
    protected int[][] matrix;


    @Override
    public String toString(){
        String stringMatrix = "";
        for(int i = 0; i < this.matrix.length; i++){
            stringMatrix += "\n|  ";
            for(int j = 0; j < this.matrix[0].length; j++){
                stringMatrix += (this.matrix[i][j]) + "  ";
            }
            stringMatrix += " |";
        }
        return stringMatrix;
    }
}

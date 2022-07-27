import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class File {
    public static String readFile(String path){
        String text = "";
        try(BufferedReader file = new BufferedReader(new FileReader(path))){
            String line = file.readLine();
            while(line != null){
                text += line + "\n";
                line = file.readLine();
            }
        }catch(IOException error){
            System.out.println("Error: " + error);
        }
        return text;
    }

    public static void writeFile(String path, String text, boolean add){
        try(BufferedWriter file = new BufferedWriter(new FileWriter(path, add))){
            file.write(text);
        }catch(IOException error){
            System.out.println("Error: " + error);
        }
    }
}

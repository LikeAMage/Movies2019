import java.io.File;

public class Main {
    public static void main (String[] args) {
        String file = new File("movieproject2019.txt").getAbsolutePath();
        FileImport.importFile(file);
        String input = args[0];
        FileImport.inputCheck(input);

    }
}

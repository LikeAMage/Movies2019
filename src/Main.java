import java.io.File;

/*
    Textfile is getting imported and input is check
 */
public class Main {
    public static void main (String[] args) {
        //Using File Class from Java so getting the path is dynamic
        String file = new File("movieproject2019.txt").getAbsolutePath();
        FileImport.importFile(file);
        String input = args[0];
        FileImport.inputCheck(input);
    }
}

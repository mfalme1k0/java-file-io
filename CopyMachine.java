import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyMachine {
    static void main (String[] args) {
        String source = "media/bright.jpg";
        File target = new File("photocopy.jpg");
        try(
                FileInputStream input  = new FileInputStream(source);
                FileOutputStream output = new FileOutputStream(target);
                ){
            int value;
            long total = 0;

            while ((value = input.read()) != -1){
                output.write(value);
                total++;
            }

            System.out.println("Copied:" + total + "bytes");

        }catch (IOException e){
            System.out.println("Could not copy file:" + e.getMessage());
        }

    }
}

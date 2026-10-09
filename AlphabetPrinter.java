import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AlphabetPrinter {
    static void main(String[] args ) {
    try{
        for(int i = 65; i <=90; i++){
            System.out.write(i);



        }
        System.out.write(10);
        System.out.flush();

        byte[] data = "JAVA".getBytes(StandardCharsets.UTF_8);
        System.out.write(data);
    } catch (IOException e){
        System.out.println("Could not copy file:" + e.getMessage());
    }

    }
}

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ChunkReader {
    static void main(String[] args){
   try{
       byte[] data = "Hello, Java streams!".getBytes(StandardCharsets.UTF_8);
       ByteArrayInputStream input = new ByteArrayInputStream(data);
       byte[] buffer = new byte[5];

       int total = 0;
       int count;

       while((count = input.read(buffer))!= -1){
           System.out.write(buffer,0, count);
           System.out.println();
           total += count;
       }
   } catch (IOException e){
       System.out.println("Error:" + e.getMessage());
   }


    }
}

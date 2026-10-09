import java.io.IOException;

public class ByteEcho {
    public static void main(String[] args)  {
      try {
          int value;
          while((value = System.in.read())!= 10){
              System.out.println(value + "->" + (char) value);
          } catch(IOExceprion e){
              System.out.println("Error:" + e.getMessage());
          }
      }
    }
}

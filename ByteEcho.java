import java.io.IOException;

public class ByteEcho {
    public static void main(String[] args) throws IOException {
        int value;
        while((value = System.in.read())!= 10){
            System.out.println(value + "->" + (char) value);
        }
    }
}

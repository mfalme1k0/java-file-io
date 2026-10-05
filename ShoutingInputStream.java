import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ShoutingInputStream extends FilterInputStream {

    protected ShoutingInputStream(InputStream input) {
        super(input);
    }
    @Override
    public int read() throws IOException {
        int value = super.read();

        if(value == -1){
            return -1;
        }
        if(value >= 'a' && value <= 'z'){
            value = value - 'a' + 'A' ;
        }
        return value;
    }

    static void main() throws IOException {
        try (InputStream in = new ShoutingInputStream(
                new ByteArrayInputStream("quiet text, 123!".getBytes()))) {
            int b;
            while ((b = in.read()) != -1) System.out.print((char) b);
        }
        System.out.println();


    }

}

import java.nio.file.*;
import java.util.Random;

public class MakeBigFile {
    public static void main(String[] args) throws Exception {
        byte[] data = new byte[10 * 1024 * 1024];   // 10 MB
        new Random().nextBytes(data);
        Files.write(Path.of("big.bin"), data);
    }
}
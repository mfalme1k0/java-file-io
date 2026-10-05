import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String source = "big1.bin";

        long start = System.nanoTime();

        SpeedRace.copyPlain(source, "plain-copy.bin");

        long end = System.nanoTime();

        long plainTime = (end - start) / 1_000_000;

        start = System.nanoTime();

        SpeedRace.copyBuffered(source, "buffered-copy.bin");

        end = System.nanoTime();

        long bufferedTime = (end - start) / 1_000_000;

        System.out.println("Plain: " + plainTime + " ms");
        System.out.println("Buffered: " + bufferedTime + " ms");

        double speedup =
                (double) plainTime / bufferedTime;

        System.out.printf(
                "Buffered was %.2fx faster%n",
                speedup
        );
    }
}

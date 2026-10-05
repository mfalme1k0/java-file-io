import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


//writing and reading from a file
public class Demo {
    public static void main(String[] args) throws IOException {
        File file = new File("text.txt");

        // Write to file
        PrintWriter output = new PrintWriter(file);

        output.println("Kood is cool");
        output.println(2026);

        output.close();

        //read file
        Scanner input = new Scanner(file);

        String name = input.nextLine();
        int year = input.nextInt();

        System.out.printf("Name: %s year: %d\n", name, year);

        input.close();


    }
}


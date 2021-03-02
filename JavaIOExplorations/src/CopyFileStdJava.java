import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Program to copy a text file into another file.
 *
 * @author Nathan Matteson
 *
 */
public final class CopyFileStdJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJava() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main(String[] args) {

        // TODO - fill in body
        BufferedReader in;
        PrintWriter out;
        try {
            in = new BufferedReader(new FileReader(args[0]));
            out = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
        } catch (IOException e) {
            System.err.println("Couldn't open file.");
            return;
        }

        try {
            String s = in.readLine();
            while (s != null) {
                out.println(s);
                s = in.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading/writing to file.");
        }

        try {
            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println("Error closing file.");
        }
    }

}

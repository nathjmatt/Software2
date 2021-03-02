import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class ProgramWithIOAndStaticMethod {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ProgramWithIOAndStaticMethod() {
    }

    /**
     * Put a short phrase describing the static method myMethod here.
     */
    private static int findPeak(int[] arr, int start, int end) {
        /*
         * Put your code for myMethod here
         */
        SimpleWriter out = new SimpleWriter1L();
        int mid = (start + end) / 2;
        if (arr.length == 1) {
            return mid;
        }
        if (arr[start] > arr[start + 1]) {
            return start;
        }
        if (arr[mid] > arr[mid + 1] && arr[mid] > arr[mid - 1]) {
            return mid;
        }
        if (arr[mid] > arr[start]) {
            return findPeak(arr, mid, end);
        } else if (arr[mid] < arr[start]) {
            return findPeak(arr, start, mid - 1);
        } else {
            return end;
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Put your main program code here; it may call myMethod as shown
         */
        int[] g = { 1, 2, 5, 8, 4, 2, -1 };
        int max = -11;
        out.println("Length: " + (g.length));
        out.println(g[findPeak(g, 0, g.length - 1)]);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}

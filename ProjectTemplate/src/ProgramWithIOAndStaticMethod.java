import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.queue.Queue;
import components.queue.Queue1L;
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
    private static void myMethod(Queue<NaturalNumber> q) {
        /*
         * Put your code for myMethod here
         */
        Queue<NaturalNumber> temp = q.newInstance();
        temp.transferFrom(q);
        while (temp.length() > 0) {
            NaturalNumber n = temp.dequeue();
            n.add(new NaturalNumber2(n));
            q.enqueue(n);
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
        Queue<NaturalNumber> q = new Queue1L<>();
        NaturalNumber one = new NaturalNumber2(1);
        NaturalNumber two = new NaturalNumber2(2);
        NaturalNumber three = new NaturalNumber2(3);
        q.enqueue(one);
        q.enqueue(two);
        q.enqueue(three);
        out.println(q);
        myMethod(q);
        /*
         * Close input and output streams
         */
        out.println(q);
        in.close();
        out.close();
    }

}

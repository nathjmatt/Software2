import java.io.File;
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Counts the amount of times a word appears in a text file, then puts it into a
 * html document
 *
 * DOING ADDITIONAL ACTIVITY 2 WHERE IT IS CASE INSENSITIVE
 *
 * @author Nathan Matteson
 */
public final class WordCounter {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private WordCounter() {
        // no code needed here
    }

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param strSet
     *            the {@code Set} to be replaced
     * @replaces strSet
     * @ensures strSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> strSet) {
        assert str != null : "Violation of: str is not null";
        assert strSet != null : "Violation of: strSet is not null";

        strSet.clear();
        char[] ch = str.toCharArray();
        char empty = ' ';

        for (int i = 0; i < ch.length; i++) {
            if (ch[i] != ' ' && !strSet.contains(ch[i])) {
                strSet.add(ch[i]);
            } else if (ch[i] == ' ' && !strSet.contains(ch[i])) {
                strSet.add(empty);
            }
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        Boolean separator = separators.contains(text.charAt(position));
        Boolean exit = true;
        int stop = position;

        if (separator) {
            for (int i = position; i < text.length() && exit; i++) {
                if (!separators.contains(text.charAt(i))) {
                    exit = false;
                    stop = i;
                } else if (i == text.length() - 1) {
                    stop = text.length();
                }
            }
        } else {
            for (int i = position; i < text.length() && exit; i++) {
                if (separators.contains(text.charAt(i))) {
                    exit = false;
                    stop = i;
                } else if (i == text.length() - 1) {
                    stop = text.length();
                }
            }
        }

        return text.substring(position, stop);
    }

    /**
     * Inputs a map which has terms and the amount of times they appear, then
     * puts the words into a queue and then alphabetizes it.
     *
     * @param terms
     *            a queue of the words sorted alphabetically
     * @param words
     *            a map of the words and their occurrences
     * @param order
     *            ordering by which to compare the terms
     * @replaces terms
     * @requires [words has at least one entry, order is a comparator that
     *           adheres to lexicographical order]
     * @ensures [terms is the alphabetization of gloss's keys]
     */
    public static void sortWords(Queue<String> terms,
            Map<String, Integer> words, Comparator<String> order) {

        terms.clear();
        int wordsSize = words.size();

        Map<String, Integer> temp = words.newInstance();
        temp.transferFrom(words);

        for (int i = 0; i < wordsSize; i++) {
            Map.Pair<String, Integer> p = temp.removeAny();
            terms.enqueue(p.key());
            words.add(p.key(), p.value());
        }
        terms.sort(order);
    }

    /**
     * Grabs terms and the amount of times they appear in the given file and
     * stores them in the given {@code Map}.
     *
     * @param fileName
     *            the name of the input file
     * @param words
     *            the words and their occurrences
     * @replaces words
     * @requires [file named fileName exists but is not open
     */
    private static void getWords(String fileName, Map<String, Integer> words) {

        //Opens a file reader
        SimpleReader inFile = new SimpleReader1L(fileName);

        //Line to read from
        String line = "";
        //Clears the map
        words.clear();
        //String of characters to not read
        final String separatorStr = " \t, -!@#$%^&*()+=.?<>;:{}[]_~/|\\";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separatorStr, separatorSet);

        //Reads through the file
        while (!inFile.atEOS()) {
            line = inFile.nextLine();
            int position = 0;
            while (position < line.length()) {
                String word = nextWordOrSeparator(line, position, separatorSet)
                        .toLowerCase();
                if (!separatorSet.contains(word.charAt(0))) {
                    if (words.hasKey(word)) {
                        int num = words.value(word);
                        words.replaceValue(word, num + 1);
                    } else {
                        words.add(word, 1);
                    }
                }
                position += word.length();
            }
        }

        inFile.close();
    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     *
     * </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</body>");
        out.println("</html");
    }

    /**
     * Generates an index page with the supplied file name.
     *
     * @param fileName
     *            the given {@code String} to make the name of the HTML page
     * @param orderedWords
     *            the {@code Queue} to help alphabetize the table
     * @param words
     *            the {@code Map} to get the words and their occurrences
     * @param textName
     *            the name of the file that the words were read from
     * @ensures the HTML page has the title of the Words Counted in textName,
     *          header as Words Counted in textName, and a table of the words
     *          and their occurrences.
     */
    private static void outputPage(String fileName, Queue<String> orderedWords,
            Map<String, Integer> words, String textName) {

        /**
         * Creates a new file and and output stream to write to the file
         */
        File html = new File(fileName);
        SimpleWriter out = new SimpleWriter1L(fileName);

        /**
         * Sets up the start of the html file with the header, line break, and
         * the start of the table
         */
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println("Words counted in");
        out.println(textName);
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>");
        out.println("Words counted in ");
        out.println(textName);
        out.println("</h2>");
        out.println("<hr>");
        out.println("<table border = \"1\">");
        out.println("<tbody>");
        out.println("<tr>");
        out.println("<th> Words </th>");
        out.println("<th> Counts </th>");

        /**
         * Prints out all of the words and their occurrences into a table
         */
        int length = orderedWords.length();
        Queue<String> temp = orderedWords.newInstance();
        temp.transferFrom(orderedWords);
        for (int i = 0; i < length; i++) {
            String first = temp.dequeue();
            orderedWords.enqueue(first);
            out.println("<tr>");
            out.println("<td>");
            out.print(first);
            out.println("</td>");
            out.print("<td>");
            out.print(words.value(first));
            out.println("</td>");
            out.println("</tr>");
        }
        /**
         * Closes the table body and table
         */
        out.println("</tbody>");
        out.println("</table>");

        //Ensures the queue stays the same as it was before the method call
        orderedWords.flip();
        //Method call to close html document
        outputFooter(out);
        //Closes file output stream
        out.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Gets file to read data from and creates a map of the words and the
         * amount of times they appear.
         */
        out.print("Please enter the name of the file to read words from: ");
        String textName = in.nextLine();
        //Map of the words and the amount of times they appear
        Map<String, Integer> words = new Map1L<>();
        //Order in which to sort the words alphabetically
        Comparator<String> order = new StringLT();
        //Calls a method to get all of the words from the text file
        getWords(textName, words);
        //A queue of the words in alphabetical order
        Queue<String> orderedWords = new Queue1L<>();
        //Sorts the words in alphabetical order
        sortWords(orderedWords, words, order);
        //out.println(orderedWords.toString());
        //out.println(words.toString());

        /*
         * Gets the name of the html file and place
         */
        out.print("Where do you want to store the html file: ");
        String fileName = in.nextLine();
        outputPage(fileName, orderedWords, words, textName);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}

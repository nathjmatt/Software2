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
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Glossary {

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
     * Private constructor so this utility class cannot be instantiated.
     */
    private Glossary() {
    }

    /**
     * Inputs a "list" of terms and their definitions from the given file and
     * stores them in the given {@code Map}.
     *
     * @param fileName
     *            the name of the input file
     * @param gloss
     *            the term -> gloss
     * @replaces gloss
     * @requires [file named fileName exists but is not open, and has the format
     *           of one "term" (unique in the file) on its own line and one
     *           definition on another line that can span multiple lines but
     *           ends with a new line]
     * @ensures [gloss contains term -> term mapping from file fileName]
     */
    private static void getWordsDefs(String fileName,
            Map<String, String> gloss) {

        SimpleReader inFile = new SimpleReader1L(fileName);

        String word = "", definition = "", line = "";
        gloss.clear();

        while (!inFile.atEOS()) {
            definition = "";
            word = inFile.nextLine();
            while (!(line = inFile.nextLine()).isEmpty()) {
                definition = definition + " " + line;
            }
            gloss.add(word, definition);
        }

        inFile.close();
    }

    /**
     * Inputs a map which has terms and their definitions, then puts the terms
     * into a queue and then alphabetizes it.
     *
     * @param terms
     *            queue of the terms that will be in alphabetical order
     * @param gloss
     *            a map of the terms and their definitions
     * @param order
     *            ordering by which to compare the terms
     * @replaces terms
     * @requires [gloss has at least one entry, order is a comparator that
     *           adheres to lexicographical order]
     * @ensures [terms is the alphabetization of gloss's keys]
     */
    public static void sortTerms(Queue<String> terms, Map<String, String> gloss,
            Comparator<String> order) {

        terms.clear();
        int glossSize = gloss.size();

        Map<String, String> temp = gloss.newInstance();
        temp.transferFrom(gloss);

        for (int i = 0; i < glossSize; i++) {
            Map.Pair<String, String> p = temp.removeAny();
            terms.enqueue(p.key());
            gloss.add(p.key(), p.value());
        }
        terms.sort(order);

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
     * Generates an HTML page with the given {@code String}.html as the name.
     *
     * @param term
     *            the given {@code String} to make the html page on
     * @param terms
     *            the {@code Queue} to check to see if the definition needs to
     *            be linked
     * @ensures the HTML page has the title of the term, header of the term in
     *          red, bold, and italicized font, the definition in a paragraph,
     *          and if any words that appear in the definition are in the
     *          glossary, link it to that page
     */
    private static void outputTermPage(String term, String definition,
            Queue<String> terms, String folder) {

        File html = new File(folder + "\\" + term + ".html");
        SimpleWriter out = new SimpleWriter1L(folder + "\\" + term + ".html");

        final String separatorStr = " \t, ";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separatorStr, separatorSet);

        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println(term);
        out.println("</title>");
        out.println("</head>");
        out.println("<h2>");
        out.println("<b><i><font color=\"red\">");
        out.println(term);
        out.println("</font></i></b>");
        out.println("</h2>");
        out.println("<blockquote>");

        int termsLength = terms.length();
        out.println(terms.toString());
        Queue<String> temp = terms.newInstance();

        int position = 0;
        Boolean wrote = false;
        while (position < definition.length()) {
            wrote = false;
            temp.transferFrom(terms);
            String token = nextWordOrSeparator(definition, position,
                    separatorSet);
            if (separatorSet.contains(token.charAt(0))) {
                out.print("");
            } else {
                out.print("");
            }
            for (int i = 0; i < termsLength; i++) {
                String tempTerm = temp.dequeue();
                terms.enqueue(tempTerm);
                if (token.equals(tempTerm)) {
                    out.print(
                            "<a href=\"" + token + ".html\">" + token + "</a>");
                    wrote = true;
                }
            }
            if (!wrote) {
                out.print(token);
            }
            position += token.length();

        }
        out.println("</blockquote>");
        out.println("<hr>");
        out.println("<p>");
        out.println("Return to ");
        out.println("<a href=\"index.html\">index</a>");
        out.println(".");
        out.println("</p>");

        outputFooter(out);
    }

    /**
     * Generates an index page with the name index.html.
     *
     * @param fileName
     *            the given {@code String} to make the name of the HTML page
     * @param terms
     *            the {@code Queue} to put the terms into a list
     * @ensures the HTML page has the title of the Glossary, header as Glossary,
     *          and an unordered bulleted list of the terms that link to their
     *          separate pages
     */
    private static void outputIndexPage(String fileName, Queue<String> terms) {

        File html = new File(fileName + ".html");
        SimpleWriter out = new SimpleWriter1L(fileName + ".html");

        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println("Glossary");
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>");
        out.println("Glossary");
        out.println("</h2>");
        out.println("<hr>");
        out.println("<h3>");
        out.println("Index");
        out.println("</h3>");
        out.println("<ul>");

        Queue<String> temp = terms.newInstance();
        temp.transferFrom(terms);
        int length = temp.length();
        for (int i = 0; i < length; i++) {
            String term = temp.dequeue();
            out.println("<li>");
            out.println("<a href=\"" + term + ".html\">" + term + "</a>");
            out.println("</li>");
            terms.enqueue(term);
        }

        out.println("</ul>");
        outputFooter(out);
        out.close();
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
     * Creates an HTML page for each term.
     *
     * @param terms
     *            the {@code Queue} of terms to generate the pages for
     * @param pairs
     *            a {@code Map} of the terms and their definitions to put in the
     *            html page
     * @ensures an html page with the name of the file being "term.html" where
     *          term is one of the terms that was imported from the file
     */
    public static void generateTermPages(Queue<String> terms,
            Map<String, String> pairs, String folder) {

        Queue<String> temp = terms.newInstance();
        Queue<String> temp1 = terms.newInstance();

        int termsLength = terms.length();
        for (int i = 0; i < termsLength; i++) {
            String term = terms.dequeue();
            temp.enqueue(term);
            temp1.enqueue(term);
        }

        int length = temp.length();

        for (int i = 0; i < length; i++) {
            String term = temp.dequeue();
            String definition = pairs.value(term);
            outputTermPage(term, definition, temp1, folder);
            terms.enqueue(term);
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
         * Gets file to read data from and creates a map of the terms and their
         * definitions.
         */
        out.print("Please enter the name of the file to read words from: ");
        String fileName = in.nextLine();
        Map<String, String> glossary = new Map1L<>();
        //Method call to put the terms and definitions into a map
        getWordsDefs(fileName, glossary);
        /*
         * Sorts the map's key values into alphabetical order
         */
        Queue<String> terms = new Queue1L<>();
        Comparator<String> order = new StringLT();
        sortTerms(terms, glossary, order);

        /*
         * Gets the name of the folder to save the output files
         */
        out.print("What is the path of the folder to save the output files: ");
        String folder = in.nextLine();

        /*
         * Creates all the html pages
         */
        outputIndexPage(folder + "\\index", terms);
        generateTermPages(terms, glossary, folder);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}

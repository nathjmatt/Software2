import java.io.File;
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;
import components.utilities.FormatChecker;

/**
 * {@code TagCloud} utility class with methods to tokenize an input stream and
 * then sort the tokenized input via the number of times a word appears and then
 * alphabetically.
 *
 * @author Josh Wang
 * @author Rithvik Potluri
 * @author Nathan Matteson
 *
 *
 *
 *         Note: Did Additional Activity 2 and the second part of Additional
 *         Activity 1
 */
public final class TagCloud {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Definition of whitespace separators.
     */
    private static final String SEPARATORS = "  \t \n \r ,-.!?[]\";:/()&^%$#@*(){}[]|/1234567890_+=";

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloud() {
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code SEPARATORS}) or "separator string" (maximal length string of
     * characters in {@code SEPARATORS}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection entries(SEPARATORS) = {}
     * then
     *   entries(nextWordOrSeparator) intersection entries(SEPARATORS) = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection entries(SEPARATORS) /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of entries(SEPARATORS)  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of entries(SEPARATORS))
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position) {
        assert text != null : "Violation of: text is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        int substringLength = position;
        Boolean separator = SEPARATORS.indexOf(text.charAt(position)) != -1;
        while (text.length() > substringLength && (SEPARATORS
                .indexOf(text.charAt(substringLength)) != -1) == separator) {
            substringLength++;
        }
        return text.substring(position, substringLength);
    }

    /*
     * Public members ---------------------------------------------------------
     */

    /**
     * Tokenizes the entire input getting rid of all whitespace separators and
     * returning the non-separator tokens in a {@code Map<String, Integer>} that
     * displays the number of times they occur.
     *
     * @param in
     *            the input stream
     * @return the map of tokens
     * @requires in.is_open
     * @ensures <pre>
     * tokens =
     *   [the non-whitespace tokens in #in.content]
     * in.content = <>
     * </pre>
     */
    public static Map<String, Integer> countWords(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";

        Map<String, Integer> tokens = new Map1L<String, Integer>();
        while (!in.atEOS()) {
            int position = 0;
            String line = in.nextLine();
            while (line.length() > position) {
                String tokenizer = nextWordOrSeparator(line, position)
                        .toLowerCase();
                if (SEPARATORS.indexOf(line.charAt(position)) == -1) {
                    if (tokens.hasKey(tokenizer)) {
                        tokens.replaceValue(tokenizer,
                                tokens.value(tokenizer) + 1);
                    } else {
                        tokens.add(tokenizer, 1);
                    }

                }
                position += tokenizer.length();
            }
        }
        return tokens;
    }

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static class StringLT
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o1.key().compareTo(o2.key());
        }
    }

    /**
     * Compare {@code int}s descending order.
     */
    private static class IntegerLT
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o2.value().compareTo(o1.value());
        }
    }

    /**
     * Inputs a map which has terms and the amount of times they appear, then
     * sorts it based on the occurences of each word and then alphabetically.
     *
     * @param numWords
     *            number of words to be printed out
     * @param words
     *            a map of the words and their occurrences
     * @param out
     *            file output stream
     * @requires [words has at least one entry, order is a comparator that
     *           adheres to lexicographical order]
     * @ensures map is alphabetized and in descending order value-wise.
     */
    public static void sortWords(Map<String, Integer> words, int numWords,
            SimpleWriter out) {

        int wordsSize = words.size();
        Map<String, Integer> temp = words.newInstance();
        temp.transferFrom(words);

        /*
         * The numeric and alphabetical sorting machines and their comparators.
         */
        Comparator<Map.Pair<String, Integer>> num = new IntegerLT();
        Comparator<Map.Pair<String, Integer>> alpha = new StringLT();
        SortingMachine<Map.Pair<String, Integer>> numberSorter = new SortingMachine1L<>(
                num);
        SortingMachine<Map.Pair<String, Integer>> alphabetSorter = new SortingMachine1L<>(
                alpha);

        int min = 0, max = 0;
        for (int i = 0; i < wordsSize; i++) {
            Map.Pair<String, Integer> p = temp.removeAny();
            numberSorter.add(p);
        }

        numberSorter.changeToExtractionMode();
        int i = 0;
        while (numberSorter.size() > 0 && i < numWords) {
            Map.Pair<String, Integer> p = numberSorter.removeFirst();
            /*
             * For the font size function
             */
            if (i == 0) {
                max = p.value();
            } else if (i == numWords - 1 || i == wordsSize - 1) {
                min = p.value();
            }
            /*
             * Puts in another sorting machine to sort alphabetically
             */
            alphabetSorter.add(p);
            i++;
        }
        alphabetSorter.changeToExtractionMode();
        while (alphabetSorter.size() > 0) {
            Map.Pair<String, Integer> p = alphabetSorter.removeFirst();
            words.add(p.key(), p.value());
            /*
             * The scaled font size
             */
            int fontSize = 22;
            if (max != min) {
                fontSize = ((48 - 11) * (p.value() - min)) / (max - min) + 11;
            }
            /*
             * HTML line to put the word into the tag cloud
             */
            out.println("<span style=\"cursor:default\" class=\"f" + fontSize
                    + "\" title =\"count: " + p.value() + "\">" + p.key()
                    + "</span>");
        }
    }

    /**
     * Generates an html page with the supplied file name.
     *
     * @param fileName
     *            the given {@code String} to make the name of the HTML page
     * @param numWords
     *            the number of words to print out
     * @param words
     *            the {@code Map} to get the words and their occurrences
     * @param textName
     *            the name of the file that the words were read from
     * @ensures the HTML page has the title and header as the number of the
     *          words in the tag cloud in textName, and a tag cloud of the
     *          words.
     */
    private static void outputPage(String fileName, Map<String, Integer> words,
            String textName, int numWords) {

        /**
         * Creates a new file and and output stream to write to the file
         */
        File html = new File(fileName);
        SimpleWriter out = new SimpleWriter1L(fileName);

        /**
         * Sets up the start of the html file with the header and title.
         */
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        if (numWords < words.size()) {
            out.println("Top " + numWords + " words in " + textName);
        }
        out.println("</title>");
        out.println(
                "<link href = \"http://web.cse.ohio-state.edu/software/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\"");
        out.println("rel = \"stylesheet\" type = \"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>");
        out.println("Top " + numWords + " words in " + textName);
        out.println("</h2>");
        out.println("<hr>");
        out.println("<div class = \"cdiv\">");
        out.println("<p class = \"cbox\">");
        /*
         * Prints out the tag cloud
         */
        sortWords(words, numWords, out);

        /*
         * Closing tags for the html
         */
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

        //Closes file output stream
        out.close();
    }

    /*
     * Main test method -------------------------------------------------------
     */

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
         * Get input file name, closing file name, and number of words in tag
         * cloud
         */
        out.print("Enter input file name: ");
        String fileName = in.nextLine();
        SimpleReader file = new SimpleReader1L(fileName);
        out.print("Where do you want to store the html file: ");
        String finalFileName = in.nextLine();

        /*
         * Gets a valid amount of numbers to print
         */
        String numWord;
        do {
            out.print("How many words do you want to be included: ");
            numWord = in.nextLine();
        } while (!FormatChecker.canParseInt(numWord)
                || Integer.parseInt(numWord) < 0);

        int numWords = Integer.parseInt(numWord);

        /*
         * Tokenize input with Tokenizer implementation under test.
         */
        Map<String, Integer> words = TagCloud.countWords(file);
        //Added to ensure the number of words was displayed correctly
        if (words.size() < numWords) {
            numWords = words.size();
        }
        outputPage(finalFileName, words, fileName, numWords);
        /*
         * Closes all streams
         */
        file.close();
        in.close();
        out.close();
    }

}

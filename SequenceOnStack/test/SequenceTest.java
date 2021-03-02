import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new sequence
     * @ensures constructorTest = <>
     */
    protected abstract Sequence<String> constructorTest();

    /**
     * Invokes the appropriate {@code Sequence} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new sequence
     * @ensures constructorRef = <>
     */
    protected abstract Sequence<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsTest = [entries in args]
     */
    private Sequence<String> createFromArgsTest(String... args) {
        Sequence<String> sequence = this.constructorTest();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsRef = [entries in args]
     */
    private Sequence<String> createFromArgsRef(String... args) {
        Sequence<String> sequence = this.constructorRef();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    // TODO - add test cases for constructor, add, remove, and length

    /**
     * Test cases for constructors.
     */
    @Test
    public void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest();
        Sequence<String> expectedSeq = this.createFromArgsRef();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
    }

    /**
     * Test cases for constructors.
     */
    @Test
    public void testOneArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest("1");
        Sequence<String> expectedSeq = this.createFromArgsRef("1");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
    }

    /**
     * Test cases for constructors.
     */
    @Test
    public void testMultipleArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest("1", "2", "3");
        Sequence<String> expectedSeq = this.createFromArgsRef("1", "2", "3");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
    }

    /**
     * Test cases for kernel method.
     */
    @Test
    public void testAddOneToEmptySequence() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest();
        Sequence<String> expectedSeq = this.createFromArgsTest("1");
        /*
         * Call method under test
         */
        seq.add(0, "1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
    }

    @Test
    public void testAddToSequenceWithAFewEntriesToEnd() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest("1", "2", "3");
        Sequence<String> expectedSeq = this.createFromArgsTest("1", "2", "3",
                "4");
        /*
         * Call method under test
         */
        seq.add(3, "4");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
    }

    @Test
    public void testAddToSequenceWithAFewEntriesToBeginning() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest("1", "2", "3");
        Sequence<String> expectedSeq = this.createFromArgsTest("4", "1", "2",
                "3");
        /*
         * Call method under test
         */
        seq.add(0, "4");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
    }

    @Test
    public void testAddToSequenceWithAFewEntriesToMiddle() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest("1", "2", "3", "4");
        Sequence<String> expectedSeq = this.createFromArgsTest("1", "2", "5",
                "3", "4");
        /*
         * Call method under test
         */
        seq.add(2, "5");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
    }

    @Test
    public void testRemoveFromSequenceWithOneEntry() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest("1");
        Sequence<String> expectedSeq = this.createFromArgsTest();
        /*
         * Call method under test
         */
        seq.remove(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
    }

    @Test
    public void testRemoveFromSequenceWithTwoEntriesFromEnd() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest("1", "2");
        Sequence<String> expectedSeq = this.createFromArgsTest("1");
        /*
         * Call method under test
         */
        seq.remove(1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
    }

    @Test
    public void testRemoveFromSequenceWithMultipleEntriesFromMiddle() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest("1", "2", "3", "4", "5");
        Sequence<String> expectedSeq = this.createFromArgsTest("1", "2", "4",
                "5");
        /*
         * Call method under test
         */
        seq.remove(2);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
    }

    @Test
    public void testEmptyLength() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest();
        Sequence<String> expectedSeq = this.createFromArgsTest();
        /*
         * Call method under test
         */
        int length = seq.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
        assertEquals(0, length);
    }

    @Test
    public void testNonEmptyOneLength() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest("1");
        Sequence<String> expectedSeq = this.createFromArgsTest("1");
        /*
         * Call method under test
         */
        int length = seq.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
        assertEquals(1, length);
    }

    @Test
    public void testNonEmptyMoreThanOneLength() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> seq = this.createFromArgsTest("1", "2", "3");
        Sequence<String> expectedSeq = this.createFromArgsTest("1", "2", "3");
        /*
         * Call method under test
         */
        int length = seq.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq, seq);
        assertEquals(3, length);
    }
}

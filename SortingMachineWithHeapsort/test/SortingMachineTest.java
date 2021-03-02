import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    @Test
    public final void testAddNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "3");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "3", "2");
        m.add("2");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testisInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        boolean isIn = m.isInInsertionMode();
        assertEquals(true, isIn);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testisInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        boolean isIn = m.isInInsertionMode();
        assertEquals(false, isIn);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrder() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        Comparator<String> ord = m.order();
        assertEquals(ORDER, ord);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        int size = m.size();
        assertEquals(0, size);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeNonEmptyOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1");
        int size = m.size();
        assertEquals(1, size);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeNonEmptySome() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1",
                "2", "3", "4");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1", "2", "3", "4");
        int size = m.size();
        assertEquals(4, size);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeNonEmptyExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1");
        int size = m.size();
        assertEquals(1, size);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeNonEmptySomeExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1",
                "2", "3", "4");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "1", "2", "3", "4");
        int size = m.size();
        assertEquals(4, size);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstSome() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1",
                "2", "3", "4");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "2", "3", "4");
        String removed = m.removeFirst();
        assertEquals("1", removed);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstSomeOrdered() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "2",
                "3", "5", "4");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "3", "5", "4");
        String removed = m.removeFirst();
        assertEquals("2", removed);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "4");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String removed = m.removeFirst();
        assertEquals("4", removed);
        assertEquals(mExpected, m);
    }

}

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size
    /**
     * Test cases for constructors.
     */
    @Test
    public void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest();
        Set<String> expectedSet = this.createFromArgsRef();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
    }

    @Test
    public void testAddEmptySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest();
        Set<String> expectedSet = this.createFromArgsTest("1");
        set.add("1");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
    }

    @Test
    public void testAddOneEntryNotInSet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1");
        Set<String> expectedSet = this.createFromArgsTest("1", "2");
        set.add("2");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
    }

    @Test
    public void testRemoveOneEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1");
        Set<String> expectedSet = this.createFromArgsTest();
        String removed = set.remove("1");
        String expectedRemoved = "1";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedRemoved, removed);
    }

    @Test
    public void testRemoveMiddleMultipleEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1", "2", "3");
        Set<String> expectedSet = this.createFromArgsTest("1", "3");
        String removed = set.remove("2");
        String expectedRemoved = "2";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedRemoved, removed);
    }

    @Test
    public void testRemoveEndMultipleEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1", "2", "3");
        Set<String> expectedSet = this.createFromArgsTest("1", "2");
        String removed = set.remove("3");
        String expectedRemoved = "3";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedRemoved, removed);
    }

    @Test
    public void testTrueContainsOneEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1");
        Set<String> expectedSet = this.createFromArgsTest("1");
        Boolean contains = set.contains("1");
        Boolean expectedContains = true;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testTrueContainsMultipleEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1", "2", "3");
        Set<String> expectedSet = this.createFromArgsTest("1", "2", "3");
        Boolean contains = set.contains("2");
        Boolean expectedContains = true;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testFalseContainsMultipleEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1", "2", "3");
        Set<String> expectedSet = this.createFromArgsTest("1", "2", "3");
        Boolean contains = set.contains("4");
        Boolean expectedContains = false;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testFalseContainsOneEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1");
        Set<String> expectedSet = this.createFromArgsTest("1");
        Boolean contains = set.contains("4");
        Boolean expectedContains = false;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testContainsFalseEmpty() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest();
        Set<String> expectedSet = this.createFromArgsTest();
        Boolean contains = set.contains("4");
        Boolean expectedContains = false;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testSizeOneEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1");
        Set<String> expectedSet = this.createFromArgsTest("1");
        int size = set.size();
        int expectedSize = 1;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedSize, size);
    }

    @Test
    public void testSizeEmptyEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest();
        Set<String> expectedSet = this.createFromArgsTest();
        int size = set.size();
        int expectedSize = 0;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedSize, size);
    }

    @Test
    public void testSizeMultipleEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1", "2", "3", "4");
        Set<String> expectedSet = this.createFromArgsTest("1", "2", "3", "4");
        int size = set.size();
        int expectedSize = 4;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedSize, size);
    }

    @Test
    public void testRemoveAnyOneEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1");
        Set<String> expectedSet = this.createFromArgsRef("1");
        String removed = set.removeAny();
        Boolean contains = expectedSet.contains(removed);
        Boolean expectedContains = true;
        String expectedRemoved = expectedSet.remove(removed);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedRemoved, removed);
        assertEquals(expectedContains, contains);

    }

    @Test
    public void testRemoveAnyMultipleEntrySet() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsTest("1", "2", "3", "4");
        Set<String> expectedSet = this.createFromArgsRef("1", "2", "3", "4");
        String removed = set.removeAny();
        Boolean contains = expectedSet.contains(removed);
        Boolean expectedContains = true;
        String removedTest = expectedSet.remove(removed);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(removed, removedTest);
        assertEquals(expectedContains, contains);

    }
}

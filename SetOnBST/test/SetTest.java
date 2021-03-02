import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Nathan Matteson
 * @author Josh Wang
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
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
     * @ensures createFromArgsRef = [entries in args]
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
    @Test
    public void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.constructorTest();
        Set<String> expectedSet = this.constructorRef();

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
        Set<String> set = this.createFromArgsRef();
        Set<String> expectedSet = this.createFromArgsTest("1");
        set.add("1");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
    }

    @Test
    public void testAddEntryLeftTree() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("2");
        Set<String> expectedSet = this.createFromArgsTest("2", "1");
        set.add("1");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
    }

    @Test
    public void testAddEntryLeftLeftTree() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("5", "3", "4");
        Set<String> expectedSet = this.createFromArgsTest("5", "3", "2", "4");
        set.add("2");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
    }

    @Test
    public void testAddEntryLeftRightTree() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("5", "2", "3");
        Set<String> expectedSet = this.createFromArgsTest("5", "2", "3", "4");
        set.add("4");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
    }

    @Test
    public void testAddOneEntryRightTree() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("1");
        Set<String> expectedSet = this.createFromArgsTest("1", "2");
        set.add("2");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
    }

    @Test
    public void testAddEntryRightRightTree() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("1", "4", "2");
        Set<String> expectedSet = this.createFromArgsTest("1", "4", "2", "3");
        set.add("3");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
    }

    @Test
    public void testAddEntryRightLeftTree() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("1", "3", "4");
        Set<String> expectedSet = this.createFromArgsTest("1", "3", "2", "4");
        set.add("2");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
    }

    @Test
    public void testRemoveOneEntry() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("1");
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
    public void testRemoveLeftLeftTree() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("4", "3", "2");
        Set<String> expectedSet = this.createFromArgsTest("4", "3");
        String removed = set.remove("2");
        String expectedRemoved = "2";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedRemoved, removed);
    }

    @Test
    public void testRemoveLeftRightTree() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("4", "2", "3");
        Set<String> expectedSet = this.createFromArgsTest("4", "2");
        String removed = set.remove("3");
        String expectedRemoved = "3";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedRemoved, removed);
    }

    @Test
    public void testRemoveRightRightTree() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("4", "7", "5", "8");
        Set<String> expectedSet = this.createFromArgsTest("4", "7", "5");
        String removed = set.remove("8");
        String expectedRemoved = "8";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedRemoved, removed);
    }

    @Test
    public void testRemoveRightWithLeafsTree() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("4", "7", "5", "9", "8", "10");
        Set<String> expectedSet = this.createFromArgsTest("4", "7", "5", "10",
                "8");
        String removed = set.remove("9");
        String expectedRemoved = "9";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedRemoved, removed);
    }

    @Test
    public void testContainsTrueOneEntry() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("1");
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
    public void testContainsTrueLeftLeft() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("6", "4", "2", "5", "8", "7",
                "9");
        Set<String> expectedSet = this.createFromArgsTest("6", "4", "2", "5",
                "8", "7", "9");
        Boolean contains = set.contains("2");
        Boolean expectedContains = true;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testContainsTrueLeftRight() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("6", "4", "2", "5", "8", "7",
                "9");
        Set<String> expectedSet = this.createFromArgsTest("6", "4", "2", "5",
                "8", "7", "9");
        Boolean contains = set.contains("5");
        Boolean expectedContains = true;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testContainsFalseLeftLeft() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("6", "4", "2", "5", "8", "7",
                "9");
        Set<String> expectedSet = this.createFromArgsTest("6", "4", "2", "5",
                "8", "7", "9");
        Boolean contains = set.contains("3");
        Boolean expectedContains = false;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testContainsFalseLeftRight() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("6", "3", "2", "4", "8", "7",
                "9");
        Set<String> expectedSet = this.createFromArgsTest("6", "3", "2", "4",
                "8", "7", "9");
        Boolean contains = set.contains("5");
        Boolean expectedContains = false;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testContainsTrueRightRight() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("20", "10", "3", "16", "50",
                "32", "90");
        Set<String> expectedSet = this.createFromArgsTest("20", "10", "3", "16",
                "50", "32", "90");
        Boolean contains = set.contains("90");
        Boolean expectedContains = true;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testContainsTrueRightLeft() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("20", "10", "3", "16", "50",
                "32", "90");
        Set<String> expectedSet = this.createFromArgsTest("20", "10", "3", "16",
                "50", "32", "90");
        Boolean contains = set.contains("32");
        Boolean expectedContains = true;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testContainsFalseRightLeft() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("20", "10", "3", "16", "50",
                "32", "90");
        Set<String> expectedSet = this.createFromArgsTest("20", "10", "3", "16",
                "50", "32", "90");
        Boolean contains = set.contains("31");
        Boolean expectedContains = false;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testContainsFalseRightRight() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("20", "10", "3", "16", "50",
                "32", "90");
        Set<String> expectedSet = this.createFromArgsTest("20", "10", "3", "16",
                "50", "32", "90");
        Boolean contains = set.contains("55");
        Boolean expectedContains = false;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSet, set);
        assertEquals(expectedContains, contains);
    }

    @Test
    public void testContainsFalseOneEntry() {
        /*
         * Set up variables and call method under test
         */
        Set<String> set = this.createFromArgsRef("1");
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
        Set<String> set = this.createFromArgsRef();
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
        Set<String> set = this.createFromArgsRef("1");
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
        Set<String> set = this.createFromArgsRef();
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
        Set<String> set = this.createFromArgsRef("1", "2", "3", "4");
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
        Set<String> set = this.createFromArgsRef("1");
        Set<String> expectedSet = this.createFromArgsTest("1");
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

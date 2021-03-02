import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value, hasKey, and size
    @Test
    public void noArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest();
        Map<String, String> expectedMap = this.createFromArgsRef();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedMap, map);
    }

    @Test
    public void addEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest();
        Map<String, String> expectedMap = this.createFromArgsRef("red",
                "green");

        /*
         * Assert that values of variables match expectations
         */
        map.add("red", "green");
        assertEquals(expectedMap, map);
    }

    @Test
    public void addNonEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green");
        Map<String, String> expectedMap = this.createFromArgsRef("red", "green",
                "blue", "yellow");

        /*
         * Assert that values of variables match expectations
         */
        map.add("blue", "yellow");
        assertEquals(expectedMap, map);
    }

    @Test
    public void removeToMakeEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green");
        Map<String, String> expectedMap = this.createFromArgsRef();

        /*
         * Assert that values of variables match expectations
         */
        map.remove("red");
        assertEquals(expectedMap, map);
    }

    @Test
    public void removeToMakeNonEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green",
                "blue", "yellow");
        Map<String, String> expectedMap = this.createFromArgsRef("red",
                "green");
        /*
         * Assert that values of variables match expectations
         */
        map.remove("blue");
        assertEquals(expectedMap, map);
    }

    @Test
    public void removeAnyToMakeNonEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green",
                "blue", "yellow");

        Map<String, String> expectedMap = this.createFromArgsRef("red", "green",
                "blue", "yellow");

        /*
         * Assert that values of variables match expectations
         */
        Pair<String, String> removed = map.removeAny();
        assertTrue(expectedMap.hasKey(removed.key()));
        expectedMap.remove(removed.key());
        assertEquals(expectedMap, map);
    }

    @Test
    public void removeAnyToMakeEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green");

        Map<String, String> expectedMap = this.createFromArgsRef("red",
                "green");

        /*
         * Assert that values of variables match expectations
         */
        Pair<String, String> removed = map.removeAny();
        assertTrue(expectedMap.hasKey(removed.key()));
        expectedMap.remove(removed.key());
        assertEquals(expectedMap, map);
    }

    @Test
    public void valueMultipleEntry() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green",
                "blue", "yellow");

        Map<String, String> expectedMap = this.createFromArgsRef("red", "green",
                "blue", "yellow");

        /*
         * Assert that values of variables match expectations
         */
        String value = map.value("blue");
        String expectedValue = "yellow";
        assertEquals(expectedMap, map);
        assertEquals(value, expectedValue);
    }

    @Test
    public void valueOneEntry() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green");

        Map<String, String> expectedMap = this.createFromArgsRef("red",
                "green");

        /*
         * Assert that values of variables match expectations
         */
        String value = map.value("red");
        String expectedValue = "green";
        assertEquals(expectedMap, map);
        assertEquals(value, expectedValue);
    }

    @Test
    public void hasKeyOneEntryTrue() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green");

        Map<String, String> expectedMap = this.createFromArgsRef("red",
                "green");

        /*
         * Assert that values of variables match expectations
         */
        Boolean truth = map.hasKey("red");
        Boolean expectedTruth = true;
        assertEquals(expectedMap, map);
        assertEquals(truth, expectedTruth);
    }

    @Test
    public void hasKeyMultipleEntryTrue() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green",
                "blue", "yellow");

        Map<String, String> expectedMap = this.createFromArgsRef("red", "green",
                "blue", "yellow");

        /*
         * Assert that values of variables match expectations
         */
        Boolean truth = map.hasKey("blue");
        Boolean expectedTruth = true;
        assertEquals(expectedMap, map);
        assertEquals(truth, expectedTruth);
    }

    @Test
    public void hasKeyOneEntryFalse() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green");

        Map<String, String> expectedMap = this.createFromArgsRef("red",
                "green");

        /*
         * Assert that values of variables match expectations
         */
        Boolean truth = map.hasKey("blue");
        Boolean expectedTruth = false;
        assertEquals(expectedMap, map);
        assertEquals(truth, expectedTruth);
    }

    @Test
    public void hasKeyEmptyFalse() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest();

        Map<String, String> expectedMap = this.createFromArgsRef();

        /*
         * Assert that values of variables match expectations
         */
        Boolean truth = map.hasKey("blue");
        Boolean expectedTruth = false;
        assertEquals(expectedMap, map);
        assertEquals(truth, expectedTruth);
    }

    @Test
    public void hasKeyMultipleEntryFalse() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green",
                "blue", "yellow");

        Map<String, String> expectedMap = this.createFromArgsRef("red", "green",
                "blue", "yellow");

        /*
         * Assert that values of variables match expectations
         */
        Boolean truth = map.hasKey("orange");
        Boolean expectedTruth = false;
        assertEquals(expectedMap, map);
        assertEquals(truth, expectedTruth);
    }

    @Test
    public void sizeOneEntry() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green");

        Map<String, String> expectedMap = this.createFromArgsRef("red",
                "green");

        /*
         * Assert that values of variables match expectations
         */
        int size = map.size();
        int expectedSize = 1;
        assertEquals(expectedMap, map);
        assertEquals(size, expectedSize);
    }

    @Test
    public void sizeMultipleEntry() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest("red", "green",
                "blue", "yellow");

        Map<String, String> expectedMap = this.createFromArgsRef("red", "green",
                "blue", "yellow");

        /*
         * Assert that values of variables match expectations
         */
        int size = map.size();
        int expectedSize = 2;
        assertEquals(expectedMap, map);
        assertEquals(size, expectedSize);
    }

    @Test
    public void sizeEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> map = this.createFromArgsTest();

        Map<String, String> expectedMap = this.createFromArgsRef();

        /*
         * Assert that values of variables match expectations
         */
        int size = map.size();
        int expectedSize = 0;
        assertEquals(expectedMap, map);
        assertEquals(size, expectedSize);
    }

    @Test
    public final void testAddOneToNone() {
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("key1",
                "value1");
        m.add("key1", "value1");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddOneToSome() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1",
                "key2", "value2");
        Map<String, String> mExpected = this.createFromArgsRef("key1", "value1",
                "key2", "value2", "key3", "value3");
        m.add("key3", "value3");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveOneFromOne() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1");
        Map<String, String> mExpected = this.createFromArgsRef();
        m.remove("key1");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveOneFromTwo() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1",
                "key2", "value2");
        Map<String, String> mExpected = this.createFromArgsRef("key2",
                "value2");
        m.remove("key1");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveTwoFromSome() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1",
                "key2", "value2", "key3", "value3", "key4", "value4", "key5",
                "value5");
        Map<String, String> mExpected = this.createFromArgsRef("key3", "value3",
                "key4", "value4", "key5", "value5");
        m.remove("key1");
        m.remove("key2");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveAnyOneFromOne() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1");
        Map<String, String> mExpected = this.createFromArgsRef("key1",
                "value1");
        Map.Pair<String, String> p = m.removeAny();
        assertEquals(mExpected.hasKey(p.key()), true);
        mExpected.remove(p.key());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveAnyOneFromTwo() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1",
                "key2", "value2");
        Map<String, String> mExpected = this.createFromArgsRef("key1", "value1",
                "key2", "value2");
        Map.Pair<String, String> p = m.removeAny();
        assertEquals(mExpected.hasKey(p.key()), true);
        mExpected.remove(p.key());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveAnyTwoFromSome() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1",
                "key2", "value2", "key3", "value3", "key4", "value4", "key5",
                "value5");
        Map<String, String> mExpected = this.createFromArgsRef("key1", "value1",
                "key2", "value2", "key3", "value3", "key4", "value4", "key5",
                "value5");
        Map.Pair<String, String> p1 = m.removeAny();
        Map.Pair<String, String> p2 = m.removeAny();
        assertEquals(mExpected.hasKey(p1.key()), true);
        assertEquals(mExpected.hasKey(p2.key()), true);
        mExpected.remove(p1.key());
        mExpected.remove(p2.key());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testValueOne() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1");
        Map<String, String> mExpected = this.createFromArgsRef("key1",
                "value1");
        assertEquals(m.value("key1"), "value1");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testValueSome() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1",
                "key2", "value2", "key3", "value3");
        Map<String, String> mExpected = this.createFromArgsRef("key1", "value1",
                "key2", "value2", "key3", "value3");
        assertEquals(m.value("key2"), "value2");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeyNone() {
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        assertEquals(m.hasKey("key1"), false);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeyOne() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1");
        Map<String, String> mExpected = this.createFromArgsRef("key1",
                "value1");
        assertEquals(m.hasKey("key1"), true);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeySomeTrue() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1",
                "key2", "value2", "key3", "value3");
        Map<String, String> mExpected = this.createFromArgsRef("key1", "value1",
                "key2", "value2", "key3", "value3");
        assertEquals(m.hasKey("key3"), true);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeySomeFalse() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1",
                "key2", "value2", "key3", "value3");
        Map<String, String> mExpected = this.createFromArgsRef("key1", "value1",
                "key2", "value2", "key3", "value3");
        assertEquals(m.hasKey("key6"), false);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeNone() {
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        assertEquals(m.size(), 0);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeOne() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1");
        Map<String, String> mExpected = this.createFromArgsRef("key1",
                "value1");
        assertEquals(m.size(), 1);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeSome() {
        Map<String, String> m = this.createFromArgsTest("key1", "value1",
                "key2", "value2", "key3", "value3");
        Map<String, String> mExpected = this.createFromArgsRef("key1", "value1",
                "key2", "value2", "key3", "value3");
        assertEquals(m.size(), 3);
        assertEquals(mExpected, m);
    }

}

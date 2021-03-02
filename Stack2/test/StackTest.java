import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    // TODO - add test cases for constructor, push, pop, and length
    @Test
    public void noArgumentConstructor() {
        Stack<String> s = this.constructorRef();
        Stack<String> sExpected = this.constructorTest();

        assertEquals(sExpected, s);
    }

    @Test
    public void pushEmptyStack() {
        Stack<String> s = this.createFromArgsRef();
        Stack<String> sExpected = this.createFromArgsTest("1");

        s.push("1");
        assertEquals(sExpected, s);
    }

    @Test
    public void pushNonEmptyStack() {
        Stack<String> s = this.createFromArgsRef("2");
        Stack<String> sExpected = this.createFromArgsTest("1", "2");

        s.push("1");
        assertEquals(sExpected, s);
    }

    @Test
    public void popOneEntryStack() {
        Stack<String> s = this.createFromArgsRef("1");
        Stack<String> sExpected = this.createFromArgsTest();

        String popped = s.pop();
        assertEquals("1", popped);
        assertEquals(sExpected, s);
    }

    @Test
    public void popSomeEntryStack() {
        Stack<String> s = this.createFromArgsRef("1", "2", "3");
        Stack<String> sExpected = this.createFromArgsTest("2", "3");

        String popped = s.pop();
        assertEquals("1", popped);
        assertEquals(sExpected, s);
    }

    @Test
    public void lengthEmptyStack() {
        Stack<String> s = this.createFromArgsRef();
        Stack<String> sExpected = this.createFromArgsTest();

        int length = s.length();
        assertEquals(0, length);
        assertEquals(sExpected, s);
    }

    @Test
    public void lengthNonEmptyStack() {
        Stack<String> s = this.createFromArgsRef("1", "2", "3");
        Stack<String> sExpected = this.createFromArgsTest("1", "2", "3");

        int length = s.length();
        assertEquals(3, length);
        assertEquals(sExpected, s);
    }
}
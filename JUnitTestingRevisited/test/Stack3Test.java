import components.stack.Stack;
import components.stack.Stack3;

/**
 * Customized JUnit test fixture for {@code Stack1L}.
 */
public class Stack3Test extends StackTest {

    @Override
    protected final Stack<String> constructorTest() {

        // TODO - fill in body
        return new Stack3<String>();
        // This line added just to make the program compilable.

    }

    @Override
    protected final Stack<String> constructorRef() {

        // TODO - fill in body
        return new Stack3<String>();
        // This line added just to make the program compilable.
    }

}
import components.stack.Stack;
import components.stack.Stack1L;

/**
 * Customized JUnit test fixture for {@code Stack1L}.
 */
public class Stack1LTest extends StackTest {

    @Override
    protected final Stack<String> constructorTest() {

        // TODO - fill in body
        return new Stack1L<String>();
        // This line added just to make the program compilable.

    }

    @Override
    protected final Stack<String> constructorRef() {

        // TODO - fill in body
        return new Stack1L<String>();
        // This line added just to make the program compilable.
    }

}
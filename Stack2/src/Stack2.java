import java.util.Iterator;

import components.stack.Stack;
import components.stack.StackSecondary;

/**
 * {@code Stack} represented as a singly linked list, done "bare-handed", with
 * implementations of primary methods.
 *
 * <p>
 * Execution-time performance of all methods implemented in this class is O(1).
 *
 * @param <T>
 *            type of Stack entries
 * @convention <pre>
 * $this.length >= 0  and
 * if $this.length == 0 then
 *   [$this.top is null]
 * else
 *   [$this.top is not null]  and
 *   [$this.top points to the first node of a singly linked list
 *    containing $this.length nodes]  and
 *   [next in the last node of that list is null]
 * </pre>
 * @correspondence this = [data in $this.length nodes starting at $this.top]
 */
public class Stack2<T> extends StackSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Node class for singly linked list nodes.
     */
    private final class Node {

        /**
         * Data in node.
         */
        private T data;

        /**
         * Next node in singly linked list, or null.
         */
        private Node next;

    }

    /**
     * Top node of singly linked list.
     */
    private Node top;

    /**
     * Number of nodes in singly linked list, i.e., length = |this|.
     */
    private int length;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        // TODO - fill in body
        this.top = new Node();
        this.top.next = null;
        this.length = 0;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Stack2() {
        this.createNewRep();
    }

    /*
     * Standard methods removed to reduce clutter...
     */

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void push(T x) {
        assert x != null : "Violation of: x is not null";

        // TODO - fill in body
        Node p = new Node();
        Node q = this.top;
        p.data = x;
        p.next = q;
        this.length++;

    }

    @Override
    public final T pop() {
        assert this.length() > 0 : "Violation of: this /= <>";

        // TODO - fill in body
        T popped = this.top.data;
        Node q = this.top.next;
        this.top = q;
        this.length--;
        return popped;

    }

    @Override
    public final int length() {

        // TODO - fill in body
        return this.length;

    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public Stack<T> newInstance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void transferFrom(Stack<T> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * Iterator code removed to reduce clutter...
     */

}

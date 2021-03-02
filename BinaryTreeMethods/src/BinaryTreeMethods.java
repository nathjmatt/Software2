import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utility class with implementation of {@code BinaryTree} static, generic
 * methods height and isInTree.
 *
 * @author Put your name here
 *
 */
public final class BinaryTreeMethods {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private BinaryTreeMethods() {
    }

    /**
     * Returns the height of the given {@code BinaryTree<T>}.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} whose height to return
     * @return the height of the given {@code BinaryTree}
     * @ensures height = ht(t)
     */
    public static <T> int height(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";

        // TODO - fill in body
        int h = 0;
        if (t.size() > 0) {
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            h++;
            int leftH = height(left);
            int rightH = height(right);
            if (leftH >= rightH) {
                h += leftH;
            } else {
                h += rightH;
            }
            t.assemble(root, left, right);
        }

        // This line added just to make the component compilable.
        return h;
    }

    /**
     * Returns true if the given {@code T} is in the given {@code BinaryTree<T>}
     * or false otherwise.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} to search
     * @param x
     *            the {@code T} to search for
     * @return true if the given {@code T} is in the given {@code BinaryTree},
     *         false otherwise
     * @ensures isInTree = [true if x is in t, false otherwise]
     */
    public static <T> boolean isInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        // TODO - fill in body
//        boolean in = false;
//        if (t.size() > 0) {
//            BinaryTree<T> left = t.newInstance();
//            BinaryTree<T> right = t.newInstance();
//            T root = t.disassemble(left, right);
//            in = root.equals(x) || isInTree(left, x) || isInTree(right, x);
//            t.assemble(root, left, right);
//        }

        boolean in = false;
        Iterator<T> it = t.iterator();
        while (it.hasNext() && !in) {
            in = it.next().equals(x);
        }
        // This line added just to make the component compilable.
        return in;
    }

    /**
     * Checks if the given {@code BinaryTree<Integer>} satisfies the heap
     * ordering property according to the <= relation.
     *
     * @param t
     *            the binary tree
     * @return true if the given tree satisfies the heap ordering property;
     *         false otherwise
     * @ensures <pre>
     * satisfiesHeapOrdering = [t satisfies the heap ordering property]
     * </pre>
     */
    private static boolean satisfiesHeapOrdering(BinaryTree<Integer> t) {

        boolean sat = true;
        if (t.size() > 1) {
            boolean satL = true;
            boolean satR = true;
            BinaryTree<Integer> left = t.newInstance();
            BinaryTree<Integer> right = t.newInstance();
            Integer root = t.disassemble(left, right);
            if (left.size() > 0) {
                satR = root <= left.root();
            }
            if (right.size() > 0) {
                satL = root <= right.root();
            }
            sat = satR && satL && satisfiesHeapOrdering(right)
                    && satisfiesHeapOrdering(left);
            t.assemble(root, left, right);

        }
        return sat;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Input a tree (or just press Enter to terminate): ");
        String str = in.nextLine();
        while (str.length() > 0) {
            BinaryTree<String> t = BinaryTreeUtility.treeFromString(str);
            out.println("Tree = " + BinaryTreeUtility.treeToString(t));
            out.println("Height = " + height(t));
            out.print("  Input a label to search "
                    + "(or just press Enter to input a new tree): ");
            String label = in.nextLine();
            while (label.length() > 0) {
                if (isInTree(t, label)) {
                    out.println("    \"" + label + "\" is in the tree");
                } else {
                    out.println("    \"" + label + "\" is not in the tree");
                }
                out.print("  Input a label to search "
                        + "(or just press Enter to input a new tree): ");
                label = in.nextLine();
            }
            out.println();
            out.print("Input a tree (or just press Enter to terminate): ");
            str = in.nextLine();
        }

        in.close();
        out.close();
    }

}

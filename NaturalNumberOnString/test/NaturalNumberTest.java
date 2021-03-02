import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Nathan Matteson
 * @author Josh Wang
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    /*
     * Constructor Tests
     */
    @Test
    public final void noArgumentConstructor() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();

        assertEquals(nExpected, n);
    }

    /*
     * Int Constructor Tests
     */
    @Test
    public final void int0Constructor() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);

        assertEquals(nExpected, n);
    }

    @Test
    public final void intConstructor() {
        NaturalNumber n = this.constructorTest(12);
        NaturalNumber nExpected = this.constructorRef(12);

        assertEquals(nExpected, n);
    }

    @Test
    public final void intMaxConstructor() {
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);

        assertEquals(nExpected, n);
    }

    /*
     * String Constructor Tests
     */
    @Test
    public final void stringRegularIntConstructor() {
        NaturalNumber n = this.constructorTest("142");
        NaturalNumber nExpected = this.constructorRef("142");

        assertEquals(nExpected, n);
    }

    @Test
    public final void stringMoreThanIntMaxConstructor() {
        NaturalNumber n = this.constructorTest("2147483649");
        NaturalNumber nExpected = this.constructorRef("2147483649");

        assertEquals(nExpected, n);
    }

    /*
     * NaturalNumber Constructor Tests
     */
    @Test
    public final void nnConstructorZero() {
        NaturalNumber nnPass = this.constructorRef(0);
        NaturalNumber nnPassExpected = this.constructorRef(0);
        NaturalNumber n = this.constructorTest(nnPass);
        NaturalNumber nExpected = this.constructorRef();

        assertEquals(nnPassExpected, nnPass);
        assertEquals(nExpected, n);
    }

    @Test
    public final void nnConstructorIntMax() {
        NaturalNumber nnPass = this.constructorRef(Integer.MAX_VALUE);
        NaturalNumber nnPassExpected = this.constructorRef(Integer.MAX_VALUE);
        NaturalNumber n = this.constructorTest(nnPass);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);

        assertEquals(nnPassExpected, nnPass);
        assertEquals(nExpected, n);
    }

    @Test
    public final void nnConstructorStringGreaterThanIntMax() {
        NaturalNumber nnPass = this.constructorRef("2147483649");
        NaturalNumber nnPassExpected = this.constructorRef("2147483649");
        NaturalNumber n = this.constructorTest(nnPass);
        NaturalNumber nExpected = this.constructorRef("2147483649");

        assertEquals(nnPassExpected, nnPass);
        assertEquals(nExpected, n);
    }

    @Test
    public final void nnConstructorSomeInt() {
        NaturalNumber nnPass = this.constructorRef(51234);
        NaturalNumber nnPassExpected = this.constructorRef(51234);
        NaturalNumber n = this.constructorTest(nnPass);
        NaturalNumber nExpected = this.constructorRef(51234);

        assertEquals(nnPassExpected, nnPass);
        assertEquals(nExpected, n);
    }

    /*
     * Multiply By 10 Tests
     */

    @Test
    public final void multiplyBy10SomeIntBySomeInt() {
        NaturalNumber n = this.constructorTest(4);
        NaturalNumber nExpected = this.constructorRef(42);

        n.multiplyBy10(2);

        assertEquals(nExpected, n);
    }

    @Test
    public final void multiplyBy10FromEmpty() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef(8);

        n.multiplyBy10(8);

        assertEquals(nExpected, n);
    }

    @Test
    public final void multiplyBy10FromEmptyToEmpty() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();

        n.multiplyBy10(0);

        assertEquals(nExpected, n);
    }

    @Test
    public final void multiplyBy10IntMax() {
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef("21474836472");

        n.multiplyBy10(2);

        assertEquals(nExpected, n);
    }

    /*
     * Divide By 10 Tests
     */

    @Test
    public final void divide42By10() {
        NaturalNumber n = this.constructorTest(42);
        NaturalNumber nExpected = this.constructorRef(4);

        int remainder = n.divideBy10();
        int remainderExpected = 2;

        assertEquals(nExpected, n);
        assertEquals(remainderExpected, remainder);
    }

    @Test
    public final void divide3By10() {
        NaturalNumber n = this.constructorTest(3);
        NaturalNumber nExpected = this.constructorRef();

        int remainder = n.divideBy10();
        int remainderExpected = 3;

        assertEquals(nExpected, n);
        assertEquals(remainderExpected, remainder);
    }

    @Test
    public final void divideBy10ToIntMax() {
        NaturalNumber n = this.constructorTest("21474836474");
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);

        int remainder = n.divideBy10();
        int remainderExpected = 4;

        assertEquals(nExpected, n);
        assertEquals(remainderExpected, remainder);
    }

    @Test
    public final void divide0By10() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();

        int remainder = n.divideBy10();
        int remainderExpected = 0;

        assertEquals(nExpected, n);
        assertEquals(remainderExpected, remainder);
    }

    /*
     * Is Zero Tests
     */

    @Test
    public final void isZero4() {
        NaturalNumber n = this.constructorTest(4);
        NaturalNumber nExpected = this.constructorRef(4);

        boolean zero = n.isZero();

        assertEquals(nExpected, n);
        assertEquals(false, zero);
    }

    @Test
    public final void isZero0() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();

        boolean zero = n.isZero();

        assertEquals(nExpected, n);
        assertEquals(true, zero);
    }

    @Test
    public final void isZeroAboveIntMax() {
        NaturalNumber n = this.constructorTest("214748364743");
        NaturalNumber nExpected = this.constructorRef("214748364743");

        boolean zero = n.isZero();

        assertEquals(nExpected, n);
        assertEquals(false, zero);
    }

}

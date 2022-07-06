package deque;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. Please do not import java.util.Deque here!*/

    public static Deque<Integer> lld = new LinkedListDeque<>();

    @Before
    public void setUp() {
        lld = new LinkedListDeque<>();
    }

    @Test
    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
		assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
		lld.addFirst(0);
        assertFalse("lld1 should now contain 1 item", lld.isEmpty());
    }

    /** Adds an item, removes an item, and ensures that dll is empty afterwards. */
    @Test
    public void addRemoveTest() {
        lld.addFirst(1);
        lld.removeFirst();
        assertTrue("lld1 should be empty after adding then removing an item", lld.isEmpty());
    }
    /** Make sure that removing from an empty LinkedListDeque does nothing */
    @Test
    public void removeEmptyTest() {
        lld.removeFirst();
        assertTrue("removing something from an empty list should do nothing", lld.isEmpty());
    }
    /** Make sure your LinkedListDeque also works on non-Integer types */
    @Test
    public void multipleParamsTest() {
        Deque<String> lld2 = new LinkedListDeque<String>();
        lld2.addFirst("First");
        assertEquals(lld2.get(0), "First");
        Deque<Boolean> lld3 = new LinkedListDeque<Boolean>();
        lld3.addFirst(false);
        assertEquals(lld3.get(0), false);
    }
    /** Make sure that removing from an empty LinkedListDeque returns null */
    @Test
    public void emptyNullReturn() {
        assertEquals(null, lld.removeFirst());
    }

    @Test
    public void multipleThings() {
        lld.addFirst(1);
        lld.removeFirst();
        assertTrue("lld1 should be empty after adding then removing an item", lld.isEmpty());
        lld.addLast(2);
        lld.addLast(2);
        lld.addLast(2);
        lld.addLast(4);
        assertEquals(4, (int) lld.get(3));
    }
}

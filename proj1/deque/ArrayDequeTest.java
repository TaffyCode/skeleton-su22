package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

    public static Deque<Integer> ad = new ArrayDeque<Integer>();
    @Test
    public void testAddLast() {
        for (int count = 0; count <= 100; count++) {
            ad.addLast(count);
        }
        for (int count = 0; count <= 95; count++) {
            ad.removeFirst();
        }
    }
    @Test
    public void testAddFirstAndStuff() {
        ad.addFirst(2);
        ad.addFirst(3);
        assertEquals(3, (int) ad.get(0));
        assertEquals(2, (int) ad.get(1));
        ad.removeFirst();
        assertEquals(2, (int) ad.get(0));
        ad.addLast(4);
        assertEquals(4, (int) ad.get(1));
        assertEquals(2, (int) ad.get(0));
        ad.addFirst(3);
        ad.addLast(18);
        assertEquals(18, (int) ad.get(3));
        ad.removeLast();
        assertEquals(2, (int) ad.get(1));
    }
}

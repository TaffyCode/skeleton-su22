/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */

public class IntList {

    /** The integer stored by this node. */
    public int item;
    /** The next node in this IntList. */
    public IntList next;

    /** Constructs an IntList storing ITEM and next node NEXT. */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /** Constructs an IntList storing ITEM and no next node. */
    public IntList(int item) {
        this(item, null);
    }

    /** Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints 1 2 3 */
    public static IntList of(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        IntList current = this;
        if (position < 0) {
            throw new IllegalArgumentException("no lol pt. 2");
        }
        for (int x = 0; x <= position; x++) {
            if (current == null) {
                throw new IllegalArgumentException("no lol");
            } else if (x != position) {
                current = current.next;
            } else {
                return current.item;
            }
        }
        return -1;
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        IntList current = this;
        StringBuilder builder = new StringBuilder();
        while (current != null) {
            if (current.next == null) {
                builder.append(current.item);
            } else {
                builder.append(current.item).append(" ");
            }
            current = current.next;
        }
        return builder.toString();
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * NOTE: A full implementation of equals requires checking if the
     * object passed in is of the correct type, as the parameter is of
     * type Object. This also requires we convert the Object to an
     * IntList, if that is legal. The operation we use to do this is called
     * casting, and it is done by specifying the desired type in
     * parenthesis. An example of this is on line 84.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IntList)) {
            return false;
        }
        IntList otherLst = (IntList) obj;
        IntList current = this;
        while (current.next != null || otherLst.next != null) {
            if ((current.next == null && otherLst.next != null) || (current.next != null && otherLst.next == null)) {
                return false;
            } else {
                if (current.item == otherLst.item) {
                    current = current.next;
                    otherLst = otherLst.next;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        IntList current = this;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new IntList(value);
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        IntList current = this;
        int x = -1;
        while (current != null) {
            if (x == -1) {
                x = current.item;
            }
            else if (current.item < x) {
                x = current.item;
            }
            current = current.next;
        }
        return x;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        int x = 0;
        IntList current = this;
        while (current != null) {
            x += (current.item * current.item);
            current = current.next;
        }
        return x;
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        while (L != null) {
            L.item = L.item * L.item;
            L = L.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /** Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList dcatenate(IntList A, IntList B) {
        if (A == null || B == null) {
            return A;
        }
        while (B != null) {
            A.add(B.item);
            B = B.next;
        }
        return A;
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
     public static IntList catenate(IntList A, IntList B) {
        if (A == null) {
            IntList newLst = new IntList(B.item);
            IntList BMimic = B.next;
            while (BMimic != null) {
                newLst.add(BMimic.item);
                BMimic = BMimic.next;
            }
            return newLst;
        }
        IntList newLst = new IntList(A.item);
        IntList AMimic = A.next;
        IntList BMimic = B;
        while (AMimic != null) {
            newLst.add(AMimic.item);
            AMimic = AMimic.next;
        }
        while (BMimic != null) {
            newLst.add(BMimic.item);
            BMimic = BMimic.next;
        }
        return newLst;
     }
}
import java.util.ArrayList;
import java.util.List;

/**
 * Represent a set of ints.
 */
public class ListSet implements SimpleSet {

    List<Integer> elems;

    public ListSet() {
        elems = new ArrayList<Integer>();
    }

    /** Adds k to the set. */
    public void add(int k) {
        if (!this.contains(k)) {
            elems.add(k);
        }
    }

    /** Removes k from the set. */
    public void remove(int k) {
        Integer toRemove = k;
        elems.remove(toRemove);
    }

    /** Return true if k is in this set, false otherwise. */
    public boolean contains(int k) { return elems.contains(k); }

    /** Return true if this set is empty, false otherwise. */
    public boolean isEmpty() {
      return this.size() == 0;
    }

    /** Returns the number of items in the set. */
    public int size() { return elems.size(); }

    /** Returns an array containing all of the elements in this collection. */
    public int[] toIntArray() {
        int[] array = new int[elems.size()];
        for (int x = 0; x < elems.size(); x++) {
            array[x] = elems.get(x);
        }
        return array;
    }
}

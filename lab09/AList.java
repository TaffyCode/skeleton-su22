import java.util.Arrays;
import java.util.Iterator;

/**
 * A AList is a list of integers. Like SLList, it also hides the terrible
 * truth of the nakedness within, but uses an array as it's base.
 */
public class AList<Item> implements Iterable<Item>{


    private Item[] items;
    private int size;

    /** Creates an empty AList. */
    public AList() {
	items = (Item[]) new Object[8];
    size = 0;
    }

    public Iterator<Item> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<Item> {
        private int wizPos;
        public ArraySetIterator() {
            wizPos = 0;
        }
        public boolean hasNext() {
            return wizPos < size;
        }

        public Item next() {
            Item returnItem = items[wizPos];
            wizPos++;
            return returnItem;
        }
    }

    /** Returns a AList consisting of the given values. */
    public static <Item> AList<Item> of(Item... values) {
        AList<Item> list = new AList<>();
        for (Item value : values) {
            list.addLast(value);
        }
        return list;
    }

    /** Returns the size of the list. */
    public int size() {
        return size;
    }

    /** Adds item to the back of the list. */
    public void addLast(Item item) {
        if (items.length == size) {
            resize();
        }
        items[size] = item;
        size += 1;
    }

    /** Resize the array to accommodate more items. */
    private void resize() {
        Item[] newItems = (Item[]) new Object[items.length * 2];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }


    @Override
    public String toString() {
        String result = "";
        int p = 0;
        boolean first = true;
        while (p != size) {
            if (first) {
                result += items[p].toString();
                first = false;
            } else {
                result += " " + items[p].toString();
            }
            p += 1;
        }
        return result;
    }


    /** Returns whether this and the given list or object are equal. */
    public boolean equals(Object o) {
        AList other = (AList) o;
        return Arrays.deepEquals(items, other.getItems());
    }

    private Item[] getItems() {
        return items;
    }

}

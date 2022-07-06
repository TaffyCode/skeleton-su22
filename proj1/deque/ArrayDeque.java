package deque;

public class ArrayDeque<T> implements Deque<T> {
    //Helper class for each point in array
    private int first;
    private int last;
    private int size;
    private T[] array;
    private static final int startsize = 8;

    public ArrayDeque() {
        first = 0;
        last = 0;
        size = 0;
        array = (T[]) new Object[startsize];
    }
    @Override
    public void addFirst(T item) {
        if (isEmpty()) {
            first = 0;
            last = 0;
            array[0] = item;
            size = 0;
        } else {
            first = downMove(first);
            array[first] = item;
        }
        size += 1;
    }

    @Override
    public void addLast(T item) {
        sizeChange();
        if (isEmpty()) {
            first = 0;
            last = 0;
            array[0] = item;
            size = 0;
        } else {
            last = upMove(last);
            array[last] = item;
        }
        size += 1;
    }

    @Override
    public int size() { return size; }

    @Override
    public void printDeque() {
        int current = first;
        while (current != last) {
            System.out.print(array[current] + " ");
            current = upMove(current);
        }
        System.out.print(array[current] + " ");
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T remove = array[first];
        array[first] = null;
        first = upMove(first);
        size -= 1;
        sizeChange();
        return remove;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T remove = array[last];
        array[last] = null;
        last = downMove(last);
        size -= 1;
        sizeChange();
        return remove;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        index += first;
        index %= array.length;
        return array[index];
    }

    @Override
    public boolean equals(Object o) {
        for (int x = 0; x < this.size(); x++) {
            if (o.equals(get(x))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    //helper methods

    private void sizeChange() {
        if (size < (array.length/ 4)) {
            int current = first;
            int number = 0;
            T[] change = (T[]) new Object[array.length / 2];
            while (current != last) {
                number += 1;
                change[number] = array[current];
                current = upMove(current);
            }
            change[number] = array[current];
            first = 0;
            last = size - 1;
            array = change;
        } else if (size == array.length) {
            int current = first;
            int number = 0;
            T[] change = (T[]) new Object[array.length * 2];
            while (current != last) {
                number += 1;
                change[number] = array[number];
                current = upMove(current);
            }
            change[number] = array[number];
            first = 0;
            last = size - 1;
            array = change;
        }
    }
    private int upMove(int change) {
        change += 1;
        change %= array.length;
        return change;
    }
    private int downMove(int change) {
        change -= 1;
        change %= array.length;
        if (change < 0) {
            change += array.length;
        }
        return change;
    }
}

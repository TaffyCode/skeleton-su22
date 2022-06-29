package deque;

import java.util.LinkedList;

public class LinkedListDeque<T> implements Deque<T> {

    public LinkedListDeque() {
        T current = null;
        LinkedListDeque<T> next = null;
        LinkedListDeque<T> prev = null;
    }

    @Override
    public void addFirst(T item) {

    }

    @Override
    public void addLast(T item) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void printDeque() {

    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public boolean equals(Object o) { return false; }
}

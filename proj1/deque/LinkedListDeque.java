package deque;

import java.util.LinkedList;

public class LinkedListDeque<T> implements Deque<T> {

    private class Node {
        private T item;
        private Node next;
        private Node prev;
        public Node(T item_cons, Node prev_cons, Node next_cons) {
            this.item = item_cons;
            this.prev = prev_cons;
            this.next = next_cons;
        }
    }

    private Node sentinel;
    //sentinels <3
    private Node prevsentinel;
    private int size;
    public LinkedListDeque() {
        //constructor for linkedlistdeque
        size = 0;
        sentinel = new Node(null, null, null);
        prevsentinel = new Node(null, null, sentinel);
        sentinel.next = prevsentinel;
    }

    @Override
    public void addFirst(T item) {
        Node first = new Node(item, sentinel, sentinel.next);
        sentinel.next = first;
        first.next.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node last = new Node(item, prevsentinel.prev, prevsentinel);
        prevsentinel.prev = last;
        last.prev.next = last;
        size += 1;
    }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node current = sentinel.next;
        if (current != null) {
            return;
        }
        while (current != prevsentinel) {
            System.out.print(current.item + " ");
            current = current.next;
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            Node remove = sentinel.next;
            remove.next.prev = sentinel;
            sentinel.next = remove.next;
            size -= 1;
            return remove.item;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            Node remove = prevsentinel.next;
            remove.prev.next = prevsentinel;
            prevsentinel.prev = remove.prev;
            size -= 1;
            return remove.item;
        }
    }

    @Override
    public T get(int index) {
        if (index == this.size || index > this.size) {
            return null;
        }
        Node current = sentinel.next;
        while (index > 0) {
            current = current.next;
            index -= 1;
        }
        return current.item;
    }

    @Override
    public boolean equals(Object o) { return false; }
}

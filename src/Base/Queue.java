package Base;

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        Node oldlast = this.last;
        this.last = new Node();
        this.last.item = item;
        this.last.next = null;
        if (isEmpty()) {
            this.first = this.last;
        } else {
            oldlast.next = this.last;
        }
        N++;
    }

    public Item dequeue() {
        Item item = this.first.item;
        this.first = this.first.next;
        if (isEmpty()) {
            this.last = null;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}

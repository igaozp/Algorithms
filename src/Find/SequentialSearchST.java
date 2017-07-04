package Find;

import Base.Queue;

public class SequentialSearchST<Key, Value> {
    private Node first;

    private class Node {
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.val;
            }
        }
        return null;
    }

    public void put(Key key, Value val) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        first = new Node(key, val, first);
    }

    public void delete(Key key) {
        Node prev = first;
        for (Node x = first; x != null; x = x.next) {
            if (x != first) {
                prev = prev.next;
            }
            if (key.equals(x.key)) {
                x.val = null;
                prev.next = x.next;
            }
        }
    }

    public boolean contains(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        int size = 0;
        for (Node x = first; x != null; x = x.next) {
            size++;
        }
        return size;
    }

    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<>();
        for (Node x = first; x != null; x = x.next) {
            q.enqueue(x.key);
        }
        return q;
    }
}

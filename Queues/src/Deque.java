import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node sentinel;

    private class Node {
        public Item item;
        private Node prev;
        private Node next;

        private Node() {
            this.prev = null;
            this.next = null;
        }

        private Node(Item i, Node n, Node p) {
            this.item = i;
            this.next = n;
            this.prev = p;
        }

    }

    private class LinkedlistIterator implements Iterator<Item> {
        private Node wizPos;

        private LinkedlistIterator() {
            wizPos = sentinel;
        }

        @Override
        public boolean hasNext() {
            return wizPos.next != sentinel;
        }

        @Override
        public Item next() {
            if (wizPos == null) {
                throw new NoSuchElementException();
            }
            Item res = wizPos.next.item;
            wizPos = wizPos.next;
            return res;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("this deque implementation doesn't support remove in iterator");
        }
    }

    public Iterator<Item> iterator() {
        return new LinkedlistIterator();
    }

    public Deque() {
        sentinel = new Node();
        sentinel.next = sentinel.prev = sentinel;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("can not add null into the deque");
        }
        Node node = new Node(item, sentinel.next, sentinel);
        sentinel.next.prev = node;
        sentinel.next = node;
        size += 1;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("can not add null into the deque");
        }
        Node node = new Node(item, sentinel, sentinel.prev);
        this.sentinel.prev.next = node;
        this.sentinel.prev = node;
        size += 1;
    }

    public int size() {
        return size;
    }


    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("can not remove value from an empty deque");
        }
        Item item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return item;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("can not remove value from an empty deque");
        }
        Item item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return item;
    }

    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            dq.addFirst("A" + i);
        }
        for (int i = 0; i < 5; i++) {
            dq.addLast("B" + i);
        }
        for (String s : dq) {
            System.out.println(s);
        }
        System.out.println("dq has " + dq.size() + " elements in total");
        for (int i = 0; i < 10; i++) {
            System.out.println(dq.removeFirst());
            System.out.println(dq.removeLast());
            System.out.println(dq.size());
        }
    }


}

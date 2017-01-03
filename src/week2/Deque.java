package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node head;
    private Node tail;

    // construct an empty deque
    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("item can't be null");
        Node newNode = new Node();
        newNode.item = item;
        if (isEmpty()) {
            newNode.next = null;
            newNode.last = null;
            head = newNode;
            tail = newNode;
        } else {
            newNode.last = null;
            newNode.next = head;
            head.last = newNode;
            head = newNode;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("item can't be null");
        Node newNode = new Node();
        newNode.item = item;
        if (isEmpty()) {
            newNode.next = null;
            newNode.last = null;
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = null;
            newNode.last = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("can't remove from empty deque");
        Node returned = head;
        Node newHead = returned.next;
        if (newHead == null) {
            head = null;
            tail = null;
        } else {
            newHead.last = null;
            head = newHead;
            returned.next = null;
        }
        size--;
        return returned.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("can' remove from empty deque");
        Node returned = tail;
        Node newTail = returned.last;
        if (newTail == null) {
            head = null;
            tail = null;
        } else {
            newTail.next = null;
            tail = newTail;
            returned.last = null;
        }
        size--;
        return returned.item;
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    private class Node {
        private Item item;
        private Node next;
        private Node last;
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("sdad");
        deque.addFirst("cxcz");
        deque.addLast("dsadsa");
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        deque.addLast("cxz");
        deque.addFirst("cxzc");
        for (String str : deque) {
            System.out.println(str);
        }
    }

}

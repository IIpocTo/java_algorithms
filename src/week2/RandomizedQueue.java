package week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;
    private int head;
    private int tail;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        size = 0;
        head = 0;
        tail = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        int counter = 0;
        for (Item item : queue) {
            if (item == null) continue;
            temp[counter++] = item;
        }
        queue = temp;
        head = 0;
        tail = size - 1;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("item can't be null");
        if (tail == queue.length - 1) resize(2 * queue.length);
        if (size == 0) {
            queue[tail] = item;
        } else {
            queue[++tail] = item;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("can't remove from empty queue");
        Item removed = null;
        size--;
        if (head == tail) {
            removed = queue[head];
            queue[head] = null;
            head = 0;
            tail = 0;
            return removed;
        } else {
            int removedIndex = -1;
            while (removed == null) {
                removedIndex = StdRandom.uniform(head, tail + 1);
                removed = queue[removedIndex];
            }
            queue[removedIndex] = null;
            if (removedIndex == head) {
                while (queue[head] == null) {
                    head++;
                }
            } else if (removedIndex == tail) {
                while (queue[tail] == null) {
                    tail--;
                }
            }
            if (size > 0 && size == queue.length / 4) resize(queue.length / 2);
            return removed;
        }
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("can't remove from empty deque");
        Item returned = null;
        while (returned == null) {
            int removedIndex = StdRandom.uniform(head, tail + 1);
            returned = queue[removedIndex];
        }
        return returned;
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] shuffledClone = shuffleArr(queue.clone());
        private int currentIndex = 0;
        private int returnedAmount = 0;

        private Item[] shuffleArr(Item[] arr) {
            StdRandom.shuffle(arr);
            return arr;
        }

        @Override
        public boolean hasNext() {
            return returnedAmount < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            while (currentIndex < shuffledClone.length - 1 && shuffledClone[currentIndex] == null) {
                currentIndex++;
            }
            returnedAmount++;
            return shuffledClone[currentIndex++];
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(32);
        queue.enqueue(322);
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(32);
        for (Integer i : queue) {
            System.out.println(i);
        }
    }

}

package org.example;

import java.util.LinkedList;

public class QueueTees<T> {
    private LinkedList<T> items = new LinkedList<>();

    public void enqueue(T item) {
        items.addLast(item);
    }

    public T dequeue() {
        return items.pollFirst();
    }

    public int size() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}

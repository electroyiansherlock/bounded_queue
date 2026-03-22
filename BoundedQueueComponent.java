package com.example.demo.component;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class BoundedQueueComponent
{
    private Queue queue;
    private int capacity;
    private Lock lock;
    private Condition producerCondition;
    private Condition consumerCondition;

    public BoundedQueueComponent() {
        this.capacity = 2;
        this.queue = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.producerCondition = lock.newCondition();
        this.consumerCondition = lock.newCondition();
    }

    public void enqueue(int value) throws InterruptedException {
        lock.lock();

        try {
            while(queue.size() == capacity) {
                // then producer have to stop sending messages.
                producerCondition.await();
            }

            queue.add(value);

            // waking up one waiting thread on this condition when the queue was empty then we might
            // have put a consumer thread on hold so waiting one of them up so to consume this message
            consumerCondition.signal();

        } finally {
            lock.unlock();
        }
    }

    public int dequeue() {
        // means the current thread will only work on this queue, in a multi threaded environment.
        lock.lock();
        int value = -1;

        try {
            while(queue.isEmpty()) {
                // blocking the consumer thread when the queue is empty
                consumerCondition.await();
            }

            value = (int) (queue.poll());

            // now the queue has one element less means the queue cannot be full now we will
            // wake up one of the producer thread if we have made it asleep because when the
            // queue was full

            producerCondition.signal();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        return value;
    }

}

package com.example.demo.component;

import org.springframework.stereotype.Component;

@Component
public class ThreadClassComponent implements Runnable{

    private BoundedQueueComponent boundedQueueComponent;

    public ThreadClassComponent(BoundedQueueComponent boundedQueueComponent) {
        this.boundedQueueComponent = boundedQueueComponent;
    }

    @Override
    public void run() {
        int value = 0;

        while(true) {
            ++value;
            try {
                boundedQueueComponent.enqueue(value);
                System.out.println("The value is added in the bounded queue");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

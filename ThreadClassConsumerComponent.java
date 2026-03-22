package com.example.demo.component;

import org.springframework.stereotype.Component;

@Component
public class ThreadClassConsumerComponent implements Runnable {

    private BoundedQueueComponent boundedQueueComponent;

    public ThreadClassConsumerComponent(BoundedQueueComponent boundedQueueComponent) {
        this.boundedQueueComponent = boundedQueueComponent;
    }

    @Override
    public void run() {
        while(true) {
            int returnValue = boundedQueueComponent.dequeue();
            System.out.println("The value returned from the bounded queue is : " + returnValue);
            System.out.println(5000);
        }
    }
}

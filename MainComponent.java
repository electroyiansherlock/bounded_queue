package com.example.demo.component;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MainComponent
{
    private ThreadClassComponent threadClassComponent;
    private ThreadClassConsumerComponent threadClassConsumerComponent;

    public MainComponent(ThreadClassComponent threadClassComponent,
                         ThreadClassConsumerComponent threadClassConsumerComponent) {
        this.threadClassComponent = threadClassComponent;
        this.threadClassConsumerComponent = threadClassConsumerComponent;
    }


    @PostConstruct
    public void startThreads() {
        new Thread(threadClassComponent, "producer1").start();
        new Thread(threadClassComponent, "producer2").start();

        new Thread(threadClassConsumerComponent, "consumer1").start();
        new Thread(threadClassConsumerComponent, "consumer2").start();
    }

}

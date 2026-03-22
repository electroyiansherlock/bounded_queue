# bounded_queue
Creating a Thread safe blocking bounded queue for multi threaded environment

Used ReentrantLock → ✔️ mutex
Used 2 Conditions:
producerCondition → wait when full
consumerCondition → wait when empty
Used while (not if) → ✔️ VERY IMPORTANT
Proper try-finally → lock release guaranteed
Flow is correct:
Producer:
waits if full
adds item
signals consumer
Consumer:
waits if empty
removes item
signals producer

👉 This is exactly how a classic blocking bounded queue works.


await() does NOT guarantee condition is true when thread wakes up.

Because:

Spurious wakeups (JVM feature)
Multiple threads competing
Signal wakes only ONE thread, but condition may still not hold
❌ If you use if:
if (queue.isEmpty()) {
    await();
}

👉 After waking:

queue might STILL be empty
thread proceeds → ❌ BUG
✅ With while:
while (queue.isEmpty()) {
    await();
}

👉 Thread re-checks condition → ✔️ SAFE


We use while instead of if because of spurious wakeups and multiple threads waking up at the same time. When a thread wakes up from await(), it must re-check the condition to ensure it is safe to proceed. Using if can lead to race conditions like queue overflow or underflow, whereas while guarantees correctness.”



“A bounded blocking queue ensures thread-safe communication between producers and consumers. I use a mutex (lock) to ensure mutual exclusion and condition variables to block threads when the queue is full or empty. Producers wait on notFull and consumers wait on notEmpty. I use while loops to handle spurious wakeups and signal the opposite side after every operation. This ensures no race conditions, no deadlocks, and correct coordination between multiple threads.”

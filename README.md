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

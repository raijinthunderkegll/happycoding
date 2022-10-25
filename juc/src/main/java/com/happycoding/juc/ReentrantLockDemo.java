package com.happycoding.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    static ReentrantLock lock = new ReentrantLock();

    static AtomicInteger integer = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException{
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    read();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "read" + i).start();

            new Thread(() -> {
                try {
                    write();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "write" + i).start();
        }

    }

    static void read() throws InterruptedException {
        lock.lock();
        TimeUnit.MILLISECONDS.sleep(200L);
        System.out.println(Thread.currentThread().getName() + " read value = " + integer);
        lock.unlock();
    }

    static void write() throws InterruptedException {
        lock.lock();
        TimeUnit.MILLISECONDS.sleep(100L);
        int i = integer.incrementAndGet();
        System.out.println(Thread.currentThread().getName() + " write value = " + integer);
        lock.unlock();
    }
}

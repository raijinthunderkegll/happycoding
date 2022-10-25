package com.happycoding.juc;

import java.util.concurrent.locks.StampedLock;

/**
 * 邮戳锁，释放锁需要使用加锁时的邮戳
 */
public class StampedLockDemo {

    static StampedLock lock = new StampedLock();

    static volatile int v;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                stampedLockRead();
            },"readThread" +i).start();
            new Thread(()->{
                stampedLockWrite();
            },"writeThread" +i).start();
        }
    }

    static void stampedLockRead(){
        long stamped = lock.readLock();
        System.out.println(Thread.currentThread().getName() + " reading: " + v + "stamp=" + stamped);
        lock.unlockRead(stamped);
    }

    static void stampedLockWrite(){
        long stamped = lock.writeLock();
        System.out.println(Thread.currentThread().getName() + " writing: " + ++v);
        lock.unlockWrite(stamped);
    }
}

package com.happycoding.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {

    /**
     * 读写锁
     * 可重入锁
     * 读共享，读写互斥，写独占
     * 锁降级：写锁降级为读锁 【readLock->readUnlock->writeLock->readLock->writeUnlock->readUnlock】
     * 缺点：写饥饿（当读线程远多于写线程的时候，写线程很难获得锁）
     */
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    static AtomicInteger integer = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        lockDegradationMain();
    }

    static void read() throws InterruptedException {
        lock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + " reading");
        TimeUnit.MILLISECONDS.sleep(100L);
        System.out.println(Thread.currentThread().getName() + " read value = " + integer);
        lock.readLock().unlock();
    }

    static void write() throws InterruptedException {
        lock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + " writing");
        TimeUnit.MILLISECONDS.sleep(100L);
        int i = integer.incrementAndGet();
        System.out.println(Thread.currentThread().getName() + " write value = " + integer);
        lock.writeLock().unlock();
    }

    static void readWrite() {
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

    static volatile int v = 0;

    static void lockDegradation(boolean isRead) {
        if(isRead){
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + ": reading~~~" + v);
            lock.readLock().unlock();
            return;
        }

        lock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + ": 获取写锁");
        lock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + ": 写锁降级为读锁。 执行++v: " + ++v);
        System.out.println(Thread.currentThread().getName() + ": 释放写锁");
        lock.writeLock().unlock();
        System.out.println(Thread.currentThread().getName() + "-> use data："+ v);
        System.out.println(Thread.currentThread().getName() + ": 释放读锁");
        lock.readLock().unlock();
    }

    static void lockDegradationMain() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lockDegradation(false);
            }, "degradation write" + i).start();

            new Thread(() -> {
                lockDegradation(true);
            }, "degradation read" + i).start();
        }
    }
}

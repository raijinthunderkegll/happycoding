package com.happycoding.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportDemon {
    public static void main(String[] args) throws InterruptedException {
        lockSupport();
    }

    private static void lockSupport() {
        // unpark一次最多只能发放一个通行证
        // 允许先unpark，再park。 park时发现有通行证，直接放行
        Thread t1 = new Thread(() -> {
            System.out.println("线程park");
            LockSupport.park();
            System.out.println("线程放行");

            LockSupport.park();
            System.out.println("线程继续放行");

        });

        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println("发放通行证");
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println("发放通行证");
        }).start();

    }

    private static void waitNotify() throws InterruptedException {
        // wait 和 notify 必须获取锁才可以使用 与synchronized配合
        Thread t1 = new Thread(() -> {
            synchronized (Thread.currentThread()) {
                try {
                    System.out.println("线程等待唤醒");
                    Thread.currentThread().wait();
                    System.out.println("线程被唤醒");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();

        TimeUnit.SECONDS.sleep(2);

        synchronized (t1) {
            t1.notify();
            System.out.println("通知唤醒线程");
        }
    }

    private static void awaitSignal() throws InterruptedException {
        // await 和 signal 必须获取锁才可以使用 与lock、condition一起使用
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程等待唤醒");
                condition.await();
                System.out.println("线程被唤醒");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        TimeUnit.SECONDS.sleep(2);

        lock.lock();
        condition.signal();
        System.out.println("通知唤醒线程");
        lock.unlock();
    }

}

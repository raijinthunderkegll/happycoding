package com.happycoding.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicDemon {
    static int SIZE = 50;
    static CountDownLatch countDownLatch = new CountDownLatch(SIZE);

    public static void main(String[] args) throws InterruptedException {
        atomicReference();
    }

    private static void atomicInteger() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {
                try {
                    atomicInteger.getAndIncrement();
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }

        // 阻塞等待归零
        countDownLatch.await();

        System.out.println(atomicInteger.get());
    }

    private static void atomicArray(){
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        atomicIntegerArray.getAndIncrement(2);
        System.out.println("2 --> "+atomicIntegerArray.get(2));
        System.out.println("0 --> "+atomicIntegerArray.get(0));
        int get2 = atomicIntegerArray.getAndAdd(2, 10);
        System.out.println("2 getAndAdd 10 --> " + get2);
        System.out.println("2 after getAndAdd 10 --> " + atomicIntegerArray.get(2));
        // 通过函数计算后更新，得到计算前的值
        int get3 = atomicIntegerArray.getAndUpdate(3, pre ->{
            return pre + 100 + 1 * 3;
        });
        System.out.println("3 getAndUpdate +100+1*3 --> " + get3);
        System.out.println("3 after getAndUpdate +100+1*3 --> " + atomicIntegerArray.get(3));
        // 通过函数计算后更新，传一个计算参数
        atomicIntegerArray.getAndAccumulate(4, 100, (pre,upd) -> {
            return (pre+1) * upd + 666;
        });
        System.out.println("4 after getAndAccumulate " + atomicIntegerArray.get(4));

        // 不需要让共享变量的修改立刻让其他线程可见的时候，以设置普通变量的方式来修改共享状态，可以减少不必要的内存屏障，从而提高程序执行的效率
        atomicIntegerArray.lazySet(5, 50);
        System.out.println("5 lazySet " + atomicIntegerArray.get(5));
    }

    private static void atomicReference(){
        User user = new User("kitty");
        AtomicStampedReference<User> atomicStampedReference = new AtomicStampedReference<>(user, 5);
        System.out.println(atomicStampedReference.getReference());
        System.out.println(atomicStampedReference.getStamp());
        boolean attemptStamp = atomicStampedReference.attemptStamp(user, 1);
        System.out.println("attemptStamp = " + attemptStamp);
        System.out.println("stamp = " + atomicStampedReference.getStamp());

        attemptStamp = atomicStampedReference.attemptStamp(new User("bob"), 5);
        System.out.println("attemptStamp = " + attemptStamp);
        System.out.println("stamp = " + atomicStampedReference.getStamp());

        int[] stamp = new int[3];
        // 返回当前的值，数组[0]赋值当前的stamp
        User user1 = atomicStampedReference.get(stamp);
        System.out.println(user1);
        System.out.println(stamp[0]);

        atomicStampedReference.weakCompareAndSet(user, new User("cool"),1, 10);
        System.out.println(atomicStampedReference.getReference());
        System.out.println(atomicStampedReference.getStamp());
    }
}

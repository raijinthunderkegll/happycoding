package com.happycoding.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS是JDK提供的非阻塞原子性操作，通过硬件保证 比较-更新 的原子性
 * CAS是一条CPU原子指令（cmpxchg）。Unsafe提供的CompareAndSwapXXX方法底层实现就是CAS指令
 * cmpxchg指令，会判断当前系统是否为多核系统，如果是就给总线加锁，只有一个线程会对总线加锁成功，加锁成功后会执行cas操作。也就是说，CAS对原子性是CPU实现独占的，时间短，性能好。
 * <p>
 * CAS缺点：1。ABA问题，尽管修改的时候获取到了A,但不代表获取到的A是没有被修改成B再改回A的，不能保证过程没有问题。
 * 2。循环导致CPU开销大。
 */
public class CASDemon {

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        stampedReference();
    }

    private static void atomic(){
        //        for(int i=0;i<50;i++){
//            new Thread(() -> {
//                add();
//                System.out.println(Thread.currentThread().getName() + " ==> " + atomicInteger.get());
//            }).start();
//        }

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                if (Integer.valueOf(Thread.currentThread().getName().substring(6)) % 10 == 0) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(Thread.currentThread().getName() + " --> " + atomicInteger.getAndIncrement());
            }).start();
        }
    }

    //    自旋
    private static void add() {
        int except;
        do {
            except = atomicInteger.get();
            System.out.println(Thread.currentThread().getName() + " >>> " + except);
        } while (!atomicInteger.compareAndSet(except, ++except));
    }

    static AtomicStampedReference<Integer> asr = new AtomicStampedReference<>(0, 1);

    private static void stampedReference() {
        new Thread(() -> {
            asr.compareAndSet(0, 1, asr.getStamp(), asr.getStamp() + 1);
            System.out.println("t1 -> value = " + asr.getReference());
            asr.compareAndSet(1, 0, asr.getStamp(), asr.getStamp() + 1);
            System.out.println("t1 -> value = " + asr.getReference());
        }).start();

        new Thread(() -> {
            int stamp = 1;
            boolean flag = asr.compareAndSet(0, 1, stamp, stamp + 1);
            System.out.println("t2 -> value = " + asr.getReference());
            System.out.println("t2 -> flag = " + flag);
        }).start();
    }


}

package com.happycoding.juc;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.*;
import java.util.function.Supplier;

public class AtomicDemon {


    public static void main(String[] args) throws InterruptedException {
        countTime(() -> {
            try {
                longAdder();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "longAdder";
        });
        countTime(() -> {
            try {
                longAccumulator();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "longAccumulator";
        });
        countTime(() -> {
            try {
                atomicLong();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "atomicLong";
        });
    }

    private static void atomicLong() throws InterruptedException {
        int SIZE = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        AtomicLong atomicLong = new AtomicLong();
        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {
                try {
                    atomicLong.getAndIncrement();
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }

        // 阻塞等待归零
        countDownLatch.await();

        System.out.println(atomicLong.get());
    }

    private static void atomicArray() {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        atomicIntegerArray.getAndIncrement(2);
        System.out.println("2 --> " + atomicIntegerArray.get(2));
        System.out.println("0 --> " + atomicIntegerArray.get(0));
        int get2 = atomicIntegerArray.getAndAdd(2, 10);
        System.out.println("2 getAndAdd 10 --> " + get2);
        System.out.println("2 after getAndAdd 10 --> " + atomicIntegerArray.get(2));
        // 通过函数计算后更新，得到计算前的值
        int get3 = atomicIntegerArray.getAndUpdate(3, pre -> {
            return pre + 100 + 1 * 3;
        });
        System.out.println("3 getAndUpdate +100+1*3 --> " + get3);
        System.out.println("3 after getAndUpdate +100+1*3 --> " + atomicIntegerArray.get(3));
        // 通过函数计算后更新，传一个计算参数
        atomicIntegerArray.getAndAccumulate(4, 100, (pre, upd) -> {
            return (pre + 1) * upd + 666;
        });
        System.out.println("4 after getAndAccumulate " + atomicIntegerArray.get(4));

        // 不需要让共享变量的修改立刻让其他线程可见的时候，以设置普通变量的方式来修改共享状态，可以减少不必要的内存屏障，从而提高程序执行的效率
        atomicIntegerArray.lazySet(5, 50);
        System.out.println("5 lazySet " + atomicIntegerArray.get(5));
    }

    private static void atomicReference() {
        User user = new User("kitty");
        AtomicStampedReference<User> atomicStampedReference = new AtomicStampedReference<>(user, 5);
        System.out.println(atomicStampedReference.getReference());
        System.out.println(atomicStampedReference.getStamp());
        // 修改stamp的值，对象必须与当前对象相同，成功返回true
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

        atomicStampedReference.weakCompareAndSet(user, new User("cool"), 1, 10);
        System.out.println(atomicStampedReference.getReference());
        System.out.println(atomicStampedReference.getStamp());

        AtomicMarkableReference<User> atomicMarkableReference = new AtomicMarkableReference<>(user, false);
        atomicMarkableReference.attemptMark(user, true);
        boolean isMarked = atomicMarkableReference.isMarked();
        atomicMarkableReference.compareAndSet(user, new User("mark"), true, false);
        System.out.println(atomicMarkableReference.getReference());
    }

    private static void fieldUpdater() {
        // 修改不安全对象的属性时，保证原子性。被修改的属性必须使用volatile修饰。
        AtomicReferenceFieldUpdater<User, String> atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "name");
        User user = new User("lulu");
        boolean compareAndSet = atomicReferenceFieldUpdater.compareAndSet(user, "lulu", "mimi");
        System.out.println(compareAndSet);
        System.out.println(atomicReferenceFieldUpdater.get(user));
        compareAndSet = atomicReferenceFieldUpdater.compareAndSet(user, "lulu", "bibi");
        System.out.println(compareAndSet);
        System.out.println(atomicReferenceFieldUpdater.get(user));
    }

    static LongAdder longAdder = new LongAdder();
    static LongAccumulator longAccumulator = new LongAccumulator((left, right) -> {
        return left + right;
    }, 0);

    /**
     * JDK1.8新增的4个类，极大的提升性能，比AtomicXXX快很多，适合高并发场景
     * <p>
     * LongAdder
     * LongAccumulator
     * DoubleAdder
     * DoubleAccumulator
     */
    private static void longAdder() throws InterruptedException {
        // 累加
        int times = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            new Thread(() -> {
                longAdder.increment();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(longAdder.longValue());
    }

    private static void longAccumulator() throws InterruptedException {
        // 自定义计算函数
        int times = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            new Thread(() -> {
                longAccumulator.accumulate(2);
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(longAccumulator.get());
    }

    private static void countTime(Supplier supplier) {
        Instant start = Instant.now();
        String sup = (String) supplier.get();
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println(sup + "耗时：" + duration.toMillis() + "毫秒");
    }
}

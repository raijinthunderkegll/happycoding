package com.happycoding.juc;

import java.util.concurrent.TimeUnit;

public class VolatileDemon {
    //在每个volatile写操作前插入StoreStore屏障，在写操作后插入StoreLoad屏障；
    //在每个volatile读操作前插入LoadLoad屏障，在读操作后插入LoadStore屏障；

    // 读屏障使缓存中数据失效，从主内存中获取数据
    // 写屏障使缓存中数据立刻写回内存

    /**
     * 【UNSAFE】
     * public native void loadFence();
     * <p>
     * public native void storeFence();
     * <p>
     * public native void fullFence();
     */

    public static void main(String[] args) throws InterruptedException {
        add();
    }

    // 普通flag修改后对其他内存不可见, 加了volatile保证了变量的可见行
//    static boolean flag = true;
    static volatile boolean flag = true;
    // volatile不要用于i++的操作，因为不具备原子性，最好还是用于boolean值，修改后直接可见
    static volatile int i = 0;
    // volatile禁止指令重排序
    static volatile VolatileDemon instance;

    public static VolatileDemon getInstance(){
        if(instance == null){
            synchronized (VolatileDemon.class){
                if(instance == null){
                    // 此处可能发生指令冲排序，导致其他线程getInstance的时候获取到未构建完全的对象
                    instance = new VolatileDemon();
                }
            }
        }
        return instance;
    }

    public static void see() throws InterruptedException {
        new Thread(() -> {
            while (flag) {
            }
            System.out.println("flag = false stop");
        }).start();

        TimeUnit.SECONDS.sleep(2);

        flag = false;
        System.out.println("update flag = false");
    }


    // volatile不具备原子性
    public static void add() {
        new Thread(() -> {
            while (i < 100) {
//                synchronized (volatileDemon.class){
                    i++;
                    System.out.println("1 ====> " + i);
//                }
            }
        }).start();
        new Thread(() -> {
            while (i < 100) {
//                synchronized (volatileDemon.class){
                    i++;
                    System.out.println("2 ====> " + i);
//                }
            }
        }).start();
    }
}

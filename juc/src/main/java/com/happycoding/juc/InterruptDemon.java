package com.happycoding.juc;

import java.util.concurrent.TimeUnit;

public class InterruptDemon {

    /**
     * 通过interrupt方法将线程中断状态设置为true，线程内可以通过isInterrupted方法判断是否为中断状态，从而决定是否中断线程
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " -> " + Thread.currentThread().isInterrupted());

                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("sleep 时被中断，抛出异常，且清除中断状态");
                    Thread.currentThread().interrupt();
//                    throw new RuntimeException(e);
                }

                System.out.println("hello");

                if(Thread.interrupted()){
                    System.out.println("线程中断，清除中断状态");
                    break;
                }

                if(Thread.currentThread().isInterrupted()){
                    System.out.println("线程中断～ ");
                    break;
                }
            }
        });
        t1.start();

        while(true){
            TimeUnit.SECONDS.sleep(1);
            t1.interrupt();
        }


    }

    private static void sleep(long t){
        try {
            TimeUnit.SECONDS.sleep(t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

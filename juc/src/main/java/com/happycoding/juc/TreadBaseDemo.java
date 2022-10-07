package com.happycoding.juc;

public class TreadBaseDemo {

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("hello world");
        },"tread-1").start();

        // start()方法底层调用的上 native start0()方法， 这个native方法调用c语言，直接调用操作系统接口 os::start_thread(thread)
    }
}

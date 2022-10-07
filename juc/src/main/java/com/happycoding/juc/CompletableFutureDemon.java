package com.happycoding.juc;

import java.util.concurrent.*;

public class CompletableFutureDemon {

    Executors executors;

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        completableFutureCombine();
    }

    public static void completableFutureCombine() {
        // thenCombine 将两个结果合并处理
        CompletableFuture.supplyAsync(() -> {
            System.out.println("stage 1 thread = " + Thread.currentThread().getName());
            return 1;
        }, Executors.newFixedThreadPool(3)).thenCombine(CompletableFuture.supplyAsync(()->{
            return 2;
        }),(a,b) -> {
            System.out.println("stage 2 thread = " + Thread.currentThread().getName());
            return a + b;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("when complete " + v);
            }
        }).exceptionally(e -> {
            System.out.println("异常了");
            return null;
        }).join();
    }

    public static void completableFutureAsync() {
        // 异步调用，thenXxx不会使用上一步的线程池
        CompletableFuture.supplyAsync(() -> {
            System.out.println("stage 1 thread = " + Thread.currentThread().getName());
            return 1;
        }, Executors.newFixedThreadPool(3)).thenApplyAsync(v -> {
            System.out.println("stage 2 thread = " + Thread.currentThread().getName());
            return v + 1;
        }, Executors.newFixedThreadPool(3)).thenApplyAsync(v -> {
            sleep(2);
            System.out.println("stage 3 thread = " + Thread.currentThread().getName());
            return v + 1;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("when complete " + v);
            }
        }).exceptionally(e -> {
            System.out.println("异常了");
            return null;
        }).join();
    }

    public static void completableFutureAccept() {
        // handle 一步一步往下执行，上一步的执行结果和异常对象可以传递给下一步
        // 上一步异常了，下一步继续执行，但上一步的返回结果为null，最终进入exceptionally，如果没有实现exceptionally，则抛出异常
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("stage 1");
            return 1;
        }, Executors.newFixedThreadPool(3)).thenAccept(v -> {
            System.out.println("stage 2 value = " + v);
        }).thenAccept(v -> {
            System.out.println("stage 3 value = " + v);
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("when complete " + v);
            }
        }).exceptionally(e -> {
            System.out.println("异常了");
            return null;
        });

        System.out.println("join = " + completableFuture.join());
    }

    public static void completableFutureHandle() {
        // handle 一步一步往下执行，上一步的执行结果和异常对象可以传递给下一步
        // 上一步异常了，下一步继续执行，但上一步的返回结果为null，最终进入exceptionally，如果没有实现exceptionally，则抛出异常
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("stage 1");
            return 1;
        }, Executors.newFixedThreadPool(3)).handle((v, e) -> {
            System.out.println("stage 2 value = " + v);
            int i = 1 / 0;
            return v + 1;
        }).handle((v, e) -> {
            System.out.println("stage 3 value = " + v);
            return v + 1;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("when complete " + v);
            }
        }).exceptionally(e -> {
            System.out.println("异常了");
            return 0;
        });

        System.out.println("join = " + completableFuture.join());
    }

    public static void completableFutureThenApply() {
        // thenApply 一步一步往下执行，上一步的执行结果可以传递给下一步
        // 如果上一步异常了，下一步不执行
        // * JVM执行到thenApply到时候，前置任务已经提前完成，则后续的任务会被main线程执行
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("stage 1");
            System.out.println("stage 1 thread = " + Thread.currentThread().getName());
            return 1;
        }, Executors.newFixedThreadPool(3)).thenApply(func -> {
            System.out.println("stage 2");
            System.out.println("stage 2 thread = " + Thread.currentThread().getName());
            return func + 1;
        }).thenApply(func -> {
            System.out.println("stage 3");
            System.out.println("stage 3 thread = " + Thread.currentThread().getName());
            return func + 1;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println(v);
            }
        }).exceptionally(e -> {
            System.out.println("异常了");
            e.printStackTrace();
            return 0;
        });

        System.out.println("join = " + completableFuture.join());
    }

    public static void completableFutureGet() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("execute complete!");
            return "hello 1";
        }, Executors.newFixedThreadPool(3));
        // 阻塞，抛异常
        String get = completableFuture.get();
        // 阻塞，超时抛异常
        String getTimeout = completableFuture.get(1, TimeUnit.SECONDS);
        // 阻塞，不抛异常
        String join = completableFuture.join();
        // 未计算完成，返回默认值
        String getNow = completableFuture.getNow("defaultValue");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 如果此调用导致CompletableFuture转换为已完成状态，则为true，否则为false
        // 如果计算未完成，将value值设置为返回值，不会打断CompletableFuture中的程序继续运行，计算结果不会再赋值给CompletableFuture的返回值
        boolean isBeComplete = completableFuture.complete("completeValue");
        System.out.println(isBeComplete);
        System.out.println(completableFuture.join());

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        boolean isBeComplete2 = completableFuture.complete("~~");
        System.out.println(completableFuture.join());
    }


    public static void completableFuture1() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync 默认线程池-> " + Thread.currentThread().getName() + "是守护线程");
            String value = "10";
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("结果：" + value);

            return value;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("success complete value = " + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println(e.getCause() + "\t" + e.getMessage());
            return null;
        });

        System.out.println("主线程搞事情");

        // 防止主线程结束，completableFuture默认线程池也关闭了的问题, 阻塞一下
//        System.out.println(completableFuture.get());
        System.out.println(completableFuture.join());
    }


    public static void future() throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return "hello world";
            }
        });

        new Thread(futureTask, "futureTask").start();

        System.out.println(futureTask.get());

    }

    private static void sleep(long t){
        try {
            TimeUnit.SECONDS.sleep(t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

class User {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

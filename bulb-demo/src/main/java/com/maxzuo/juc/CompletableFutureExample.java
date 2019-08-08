package com.maxzuo.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * CompletableFuture 的使用
 * <p>
 * Created by zfh on 2019/08/08
 */
class CompletableFutureExample {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * 在JDK1.5已经提供了Future和Callable的实现,可以用于阻塞式获取结果,
     * 如果想要异步获取结果,通常都会以轮询的方式去获取结果.
     *
     * 缺点：轮询的方式会耗费无谓的CPU资源，而且也不能及时地得到计算结果
     */
    @Test
    void testFutureAndCallable () {

        Future<String> future = executor.submit(()->{
            Thread.sleep(2000);
            return "hello world";
        });

        try {
            // 阻塞获取结果
            future.get();

            //轮询获取结果
            while (true){
                if(future.isDone()) {
                    String result = future.get();
                    System.out.println(result);
                    break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * DK1.8中的CompletableFuture就为我们提供了异步函数式编程,CompletableFuture提供了非常强大的Future的扩展功能，
     * 可以帮助我们简化异步编程的复杂性，提供了函数式编程的能力，可以通过回调的方式处理计算结果，并且提供了转换和组合
     * CompletableFuture的方法。
     *
     * <pre>
     *  CompletionStage代表异步计算过程中的某一个阶段，一个阶段完成以后可能会触发另外一个阶段
     *  一个阶段的计算执行可以是一个Function，Consumer或者Runnable。比如：stage.thenApply(x -> square(x)).thenAccept(x -> System.out.print(x)).thenRun(() -> System.out.println())
     *  一个阶段的执行可能是被单个阶段的完成触发，也可能是由多个阶段一起触发
     * </pre>
     */
    @Test
    void testSupplyAsyncMethod () {
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> {
                    return "hello world";
                })
                // thenAccept 接收上一阶段的输出作为本阶段的输入
                .thenApply(s -> s + " 1")
                .whenComplete((r, e) -> System.out.println(r));
    }
}

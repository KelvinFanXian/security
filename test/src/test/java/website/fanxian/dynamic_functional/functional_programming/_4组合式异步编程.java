package website.fanxian.dynamic_functional.functional_programming;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 26.4组合式异步编程
 *      方便的将多个有一定依赖关系的异步任务以流水线的方式组合在一起
 *
 */
public class _4组合式异步编程 {

    @Test
    public void _1异步任务管理(){/*
        CompletableFuture
            实现了两个接口：Future（表示异步任务的结果）、CompletionStage（完成阶段）。

            多个CompletionStage可以以流水线的方式组合起来，对于其中一个CompletionStage，
            它有一个计算任务，但可能需要等待其他一个或多个阶段完成才能开始，它完成后，可能
            会触发其他阶段开始运行。

        CompletionStage提供了大量方法，使用它们，可以方便地响应任务事件，构建任务流水线，实现组合式异步编程。
     */
    }

    static class _2与Future_FutureTask对比{/*
        回顾异步任务执行服务和Future
        用Callable或Runnable表示任务。
     */
        /**
         * 1.基本的任务执行服务
         */
        private static Random rnd = new Random();
        static int delayRandom(int min, int max) {
            int milli = max > min ? rnd.nextInt(max - min) : 0;
            try {
                Thread.sleep(min + milli);
            } catch (InterruptedException e) {
            }
            return milli;
        }
        static Callable<Integer> externalTaskOfCallable = () -> {
            int time = delayRandom(20, 2000);
            return time;
        };

        private static ExecutorService executor =
                Executors.newFixedThreadPool(10);

        public static Future<Integer> callExternalService(){
            return executor.submit(externalTaskOfCallable);
        }

        public static void main(String[] args) {
            // 执行异步任务
            Future<Integer> asyncRet = callExternalService();
            // 执行其他任务……
            // 获取异步任务的结果，处理可能的异常
            try {
                Integer ret = asyncRet.get();
                System.out.println(ret);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        /**
         * 2.基本的CompletableFuture
         *   不支持使用Callable 表示异步任务，
         *   而支持Runnable和Supplier。
         *
         *   Supplier替代Callable表示有返回结果的异步任务，与Callable的区别是，
         *   它不能抛出受检异常，如果会发生异常，可以抛出运行时异常。
         */
        // 类型替换：Callable-->Supplier
        static Supplier<Integer> externalTaskSupplier = () -> {
            int time = delayRandom(20, 2000);
            return time;
        };
        // CompletableFuture.supplyAsync(externalTaskSupplier, executor);
        // --> executor.submit(externalTaskOfCallable);
        public static Future<Integer> callExternalService0(){
            return CompletableFuture.supplyAsync(externalTaskSupplier, executor);
        }

        /**
         * supplyAsync还有一个不带executor参数的方法：
         *  public static <U> completableFuture<U> supplyAsync(Supplier<U> externalTaskSupplier)
         *
         *  没有executor，任务被谁执行？
         *      与环境配置有关：
         *          CUP多核：会使用Java7引入的Fork/Join任务执行服务，即
         *          ForkJoinPool.common-Pool()，该任务执行服务背后的工作线程数一般为CPU核数减1，
         *          即Runtime.getRuntime().availableProcessors()-1，
         *          否则，会使用ThreadPerTaskExecutor，他会为每个 任务创建一个线程。
         */

        /**
         * 对于类型为Runnable的任务，构建CompletableFuture的方法为
         *  public static CompletableFuture<Void> runAsync(Runnable runnable)
         *  public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
         */


        /**
         * 3.CompletableFuture对Future的基本增强
         *  Future有的接口，CompletableFuture都是支持的。
         *  CompletableFuture还有一些额外的方法：
         *      public T join()  // 与get方法类似，也会等待任务结束，但不会抛出受检异常
         *                       // 如果异常结束了，join会将异常包装为运行时异常CompletionException抛出
         *      public boolean isCompletedExceptionally()  // Future有isDone方法检查任务是否结束了，但不知道任务是正常结束
         *                                          //还是异常结束。isCompletedExceptionally可以判断是否异常结束。
         *      public T getNow(T valueIfAbsent) //与join类似， 区别是，如果任务还没结束，getNow不会等待，
         *                                       //而是会返回传入的参数 valueIfAbsent
         */

        /**
         * 4.进一步理解Future/CompletableFuture
         *   任务执行服务 与 异步结果Future 不是绑在一起的， 可以自己创建线程返回异步结果：
         */
        // 使用FutureTask调用外部服务:
        public static Future<Integer> callExternalService1() {
            FutureTask<Integer> future = new FutureTask<>(externalTaskOfCallable);
            new Thread(() -> future.run()).start();
            return future;
        }

        // 使用CompletableFuture，也可以直接创建线程，并返回异步结果：
        public static Future<Integer> callExternalService2() {
            CompletableFuture<Integer> future = new CompletableFuture<>();
            new Thread() {
                public void run() {
                    try {
                        future.complete(externalTaskSupplier.get());
                    } catch (Exception e) {
                        future.completeExceptionally(e);
                    }
                }
            }.start();
            return future;
        }
    }

    @Test
    public void _3响应结果或异常(){/*
        1.whenComplete
        2.handle
        3.exceptionally
     */
    }
    @Test
    public void _4构建依赖单一阶段的任务流(){/*
        thenRun thenAccept/thenApply thenCompose
     */
    }
    @Test
    public void _5构建依赖两个阶段的任务流(){/*
        public CompletableFuture<Void> runAfterBoth(
            CompletionStage<?> other, Runnable action)
        public <U,V> CompletableFuture<V> thenCombine(
            CompletionStage<? extends U> other,
            BiFunction<? super T,? super U,? extends V> fn)
        public <U> CompletableFuture<Void> thenAcceptBoth(
            CompletionStage<? extends U> other,
            BiConsumer<? super T, ? super U> action)
     */
    }
    @Test
    public void _6构建依赖多个阶段的任务流(){/*
        public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)
        public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)
     */
    }
    @Test
    public void _7小结(){/*
     */
    }
}

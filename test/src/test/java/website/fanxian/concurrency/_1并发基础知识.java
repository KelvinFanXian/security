package website.fanxian.concurrency;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 第15章 并发基础知识
 *
 * 1) 线程的基本概念
 * 2) 理解synchronized
 * 3) 线程的基本协作机制
 * 4) 线程的中断
 */
public class _1并发基础知识 {
    @Test
    public void _1线程的基本概念() throws Exception{/*
        线程表示一条单独的执行流，它有自己的程序执行计数器，有自己的栈。
        Thread的方法：
            public static native Thread currentThread(); // 获取当前执行的线程对象

        线程的基本属性：
            1 id
                递增int
            name
                有构造器，和setter。设置友好名称
            2 优先级
                Java中，优先级从1到10，默认为5,相关方法：
                    public final void setPriority(int newPriority)
                    public final int getPriority()
                    优先级对操作系统而言主要是一种建议和提示，编程中，不要过于依赖优先级。
            3 状态
                public State getState()
                    返回类型：
                    public enum State {
                      NEW, // 没有调用start的线程状态
                      TIMED_WAITING, // 线程运行结束
                      RUNNABLE, // 可能在执行(就绪状态+运行状态)，也可能在等待操作系统分配时间片
                      // 下面状态都表示线程被阻塞了，在等待一些条件
                      BLOCKED,
                      WAITING,
                      TERMINATED;
                    }
                // 线程是否还活着。run方法结束前，返回值都是true
                public final native boolean isAlive()
            4 是否daemo线程
                public final void setDaemon(boolean on)
                public final boolean isDaemon()
                当整个程序中剩下的都是daemon线程的时候，程序就会退出。
                作用：
                    它一般是其他线程的辅助线程，在它辅助的主线程退出的时候，它就没有存在的意义了。在我们运行一个
                即使最简单的hello world类型的程序时，实际上，Java也会创建多个线程，除了main线程外，至少还有一个
                负责垃圾回收的线程，这个线程就是daemon线程，在main线程结束的时候，垃圾回收线程也会退出。
            5 sleep方法
                // 让出CPU，睡眠期间，线程可以被中断，会抛出InterruptedException
                public static native void sleep(long millis) throws InterruptedException;
            6 yield方法
                // 让出CPU： 我现在不急着占用CPU（建议）
                public static native void yield();
            7 join方法
                // 让调用join的线程等待该线程的结束。
                // 让父线程等待子线程结束之后才能继续运行
                public final void join() throws InterruptedException
                // 限定等待时长（毫秒），0表示无限期等待
                public final synchronized void join(long millis) throws InterruptedException
                如果希望main线程在子线程结束后再退出：
                    public static void main(String[] args) throws InterruptedException {
                        Thread thread = new HelloThread();
                        thread.start();
                        thread.join();
                    }
            过时方法
                public final void stop()
                public final void suspend()
                public final void resume()

        1.4 线程的优点及成本
            为什么要创建单独的执行流？或者说线程有什么优点？
            1）充分利用多CPU的计算能力，但线程只能利用一个CPU，使用多线程可以利用多CPU的计算能力
            2）充分利用硬件资源，CPU和硬盘、网络是可以同时工作的，一个线程在等待网络IO的同时，另一个线程完全可以利用CPU，
                对多个独立的网络请求，完全可以使用多个线程同时请求。
            3）在用户界面（GUI）应用程序中，保持程序的响应性，界面和后台任务通常是不同的线程，否则，如果所有事情都是一个线程来
                执行，当执行一个很慢的任务时，整个界面将停止响应，也无法取消该任务。
            4）简化建模及IO处理，比如在服务器应用程序中，对每个用户请求使用一个单独的线程处理，相比使用一个线程，处理来自各种
                用户的各种请求，以及各种网络和文件IO之间，建模和编写程序要容易得多。
            成本：
                操作系统会为每个线程创建必要的数据结构、栈、程序计数器等，创建也需要一定的时间。
            线程调度和切换成本：
                当又大量可运行线程的时候，操作系统会忙于调度，为一个线程分配一段时间，执行完后，在让另一个线程执行，一个线程切换
                出去后，操作系统需要保存它的当前上下文状态到内存，上下文状态包括当前CPU寄存器的值、程序计数器的值等，而一个线程
                被切换回来后，操作系统需要回复它原来的上下文状态，整个过程称为【上下文切换】，这个切换不仅耗时，而且使CPU中的很
                多缓存失效。

                CPU切换相对成本，如果只是做counter++这样的操作，那么切换的相对成本就太高了。

                如果执行的任务都是CPU密集型的，主要消耗的都是CPU，那创建超过CPU数量的线程就是没有必要的，不会加快程序的执行。

     */
    }

    static class _1_3共享内存及可能存在的问题 {/*
        线程{自己的程序计数器，自己的栈}
        但可以共享内存：访问和操作相同的对象。
    */
        /**
         * 共享内存示例
         *   在分析代码执行过程时，理解代码在被哪个线程执行时很重要的。
         *   每条执行流都有单独的栈，方法中的参数和局部变量都有自己的一份。
         *
         *   当多条执行流可以操作相同的变量时，可能会出现一些意料之外的结果，包括
         *   【竞态条件】和【内存可见性】 问题：
         */
        private static int shared = 0;
        private static void incrShared(){
            shared ++;
        }
        static class ChildThread extends Thread {
            List<String> list;
            public ChildThread(List<String> list) {
                this.list = list;
            }
            @Override
            public void run() {
                incrShared();
                list.add(Thread.currentThread().getName());
            }
        }
        static void main(String[] args) throws InterruptedException {
            List<String> list = new ArrayList<String>();
            Thread t1 = new ChildThread(list);
            Thread t2 = new ChildThread(list);
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println(shared);
            System.out.println(list);
        }

        void _1竞态条件() {/*
            race condition：当多个线程访问和操作同一个对象时，最终执行结果与执行时序有关，
                可能正确也可能不正确。
            // 静态条件示例
            public class CounterThread extends Thread {
                private static int counter = 0;
                @Override
                public void run() {
                    for(int i = 0; i < 1000; i++) {
                        counter++;
                    }
                }
                public static void main(String[] args) throws InterruptedException {
                    int num = 1000;
                    Thread[] threads = new Thread[num];
                    for(int i = 0; i < num; i++) {
                        threads[i] = new CounterThread();
                        threads[i].start();
                    }
                    for(int i = 0; i < num; i++) {
                        threads[i].join();
                    }
                    System.out.println(counter);
                }
            }

            期望结果是100万，但大多是99万多。
            原因：
                因为counter++这个操作不是原子操作，它分为3个步骤：
                  1）去counter的当前值
                  2）在当前值基础上加1
                  3）将新值重新赋值给counter
            所以就会产生线程安全问题，解决方法：
                1）使用synchronized关键字
                2）使用显示锁
                3）使用原子变量
        */
        }
        void _2内存可见性() {/*
            多个线程可以共享访问和操作相同的变量，但一个线程对一个共享变量的修改，另一个线程不一定马上
            就能看到，甚至永远也看不到。

            // 内存可见性实例
            public class VisibilityDemo {
                private static boolean shutdown = false;
                static class HelloThread extends Thread {
                    @Override
                    public void run() {
                        while(!shutdown){
                            // do nothing
                        }
                        System.out.println("exit hello");
                    }
                }
                public static void main(String[] args) throws InterruptedException {
                    new HelloThread().start();
                    Thread.sleep(1000);
                    shutdown = true;
                    System.out.println("exit main");
                }
            }
            期望结果是两个线程都退出，但实际上，很可能会发现HelloThread用于都不会退出，也就是说，在HelloThread执行流看来，shutdown
            永远为false，即使main线程已经更改为了true。

            这就是【内存可见性问题】。 操作系统中，除了内存，数据还会被缓存在CPU的寄存器以及各级缓存中，当访问一个变量时，可能直接
            充寄存器或CPU缓存中获取，而不一定到内存中去取，当修改一个变量时，也可能是先写到缓存中，稍后才会同步更新到内存中。在
            单线程的程序中，这一般不是问题，但在多线程的程序中，尤其是在有多CPU的情况下，这就是严重的问题。一个线程对内存的修改，
            另一个线程看不到，一时修改没有及时同步到内存，二是另一个线程根本就没从内存读。
            问题解决方法：
                1）使用volatile关键字
                2）使用synchronized关键字或显示锁同步。
        */
        }
    }

    @Test
    public void _2理解synchronized() throws Exception{/*
        synchronized可以用于修饰类的实例方法、静态方法和代码块
        1.实例方法
        // 用synchronized修饰的Counter类
        public class Counter {
            private int count;
            public synchronized void incr(){
                count ++;
            }
            public synchronized int getCount() {
                return count;
            }
        }
        加了synchronized后，方法内的代码就变成了原子操作，当多个线程并发更新同一个Counter对象的时候，也不会出问题。

        synchronized到底干了什么？
            synchronized是的同时只能有一个线程执行实例方法，但这个理解不确切。
            【多个线程是可以同时执行同一个synchronized实例方法的，只要他们访问的对象是不同的即可。】比如：
                Counter counter1 = new Counter();
                Counter counter2 = new Counter();
                Thread t1 = new CounterThread(counter1);
                Thread t2 = new CounterThread(counter2);
                t1.start();
                t2.start();
            所以synchronized实例方法实际保护的是同一个对象的方法调用。
            保护当前实例对象，即this，this对象有一个锁和一个等待队列，锁只能被一个线程持有，其他试图获取同样锁的线程需要等待：
                1）尝试获得锁，获得，继续下一步；否则，加入等待队列，阻塞并等待唤醒。
                2）执行实例方法体代码。
                3）释放锁，如果等待队列上有等待的线程，从中取一个并唤醒，如果有多个等待的线程，唤醒哪一个是不一定的，不保证公平性。

        2.静态方法(保护类对象，即StaticCounter.class)
        3.代码块【任意对象都有一个锁和等待队列】


        2.2 进一步理解synchronized
            1）可重入性
                【可重入是通过记录锁的持有线程和持有数量来实现的】，当调用被synchronized保护的代码时，检查对象是否已被锁，如果
                是，再坚持是否被当前线程锁定，如果是，增加持有数量，如果不是被当前线程锁定，才加入等待队列，当释放锁时，减少持有
                数量，当数量变为0时才释放整个锁。
            2）内存可见性
                synchronized保证内存可见性，在释放锁时，所有写入都会写回内存，而获得锁后，都会从内存中读最新数据。
                不过只是为了保证内存可见性，synchronized成本有点高，更轻量级的方式，给变量加修饰符volatile，加了volatile之后，
                Java会在操作对应变量时插入特殊的指令，保证读写到内存最新值，而非缓存值。
            3）死锁
                public class DeadLockDemo {
                    private static Object lockA = new Object();
                    private static Object lockB = new Object();
                    private static void startThreadA() {
                        Thread aThread = new Thread() {
                            @Override
                            public void run() {
                                synchronized (lockA) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                    }
                                    synchronized (lockB) {
                                    }
                                }
                            }
                        };
                        aThread.start();
                    }
                    private static void startThreadB() {
                        Thread bThread = new Thread() {
                            @Override
                            public void run() {
                                synchronized (lockB) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                    }
                                    synchronized (lockA) {
                                    }
                                }
                            }
                        };
                        bThread.start();
                    }
                    public static void main(String[] args) {
                        startThreadA();
                        startThreadB();
                    }
                }
               【应该尽量避免在持有一个锁的同时去申请另一个锁，如果确是需要多个锁，所有代码都应该按照相同的顺序去申请锁。】
                jstack会生成死锁的报告图。

        2.3 同步容器及注意事项
             public static <T> Collection<T> synchronizedCollection(Collection<T> c)
             public static <T> List<T> synchronizedList(List<T> list)
             public static <K,V> Map<K,V> synchronizedMap(Map<K,V> m)
             加了synchronized，所有方法调用变成了院子操作，客户端在调用时，是不是就绝对安全了呢？不是的，至少有以下情况需要注意：
                1）复合操作
                    比如先检查再更新
                2）伪同步
                    同步错对象了
                    所有方法必须使用相同的锁
                3）迭代
                    需要在遍历的时候给整个容器对象加锁。

        2.4 并发容器
            CopyOnWriteArrayList
            ConcurrentHashMap
            ConcurrentLinkedQueue
            ConcurrentSkipListSet
     */
    }
    @Test
    public void _3线程的基本协作机制() throws Exception{/*
        Java多线程协作的基本机制wait/notify
            Java在Object类而非Thread类中定义了一些线程协作的基本方法， 是的每个对象都可以调用这些方法，这些方法有两类，
            一类是wait，另一类是notify。

            主要由两个wait方法：
                public final void wait() throws InterruptedException
                public final native void wait(long timeout) throws InterruptedException;

            除了用于锁的等待队列，每个对象还有另一个等待队列，表示条件队列，该队列用于线程间的协作。
            调用wait就会把当前线程放到条件队列上并阻塞，表示当前线程执行不下去了，需要等待一个条件，这个条件它自己改变不了，
            需要其他线程改变。当其他线程改变了条件后，应该调用Object的notify方法：
                public final native void notify();
                public final native void notifyAll();

            notify做的事情就是从条件队列中选一个线程，将其从队列中移除并唤醒，notifyAll和notify的区别是，它会移除条件队列中所有
            的线程并全部唤醒。


        协作场景：
            1）生产者/消费者协作模式
            2）同时开始
            3）等待结束
            4）异步结果
            5）集合点
     */
    }
    @Test
    public void _4线程的中断() throws Exception{/*
     */
    }
}

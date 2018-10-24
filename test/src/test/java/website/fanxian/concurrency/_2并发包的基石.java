package website.fanxian.concurrency;

import javafx.util.Pair;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 1) 原子变量和CAS
 * 2) 显示锁
 * 3) 显示条件
 */
public class _2并发包的基石 {
    @Test
    public void _1原子变量和CAS() throws Exception{/*

        对于counter这种操作来说，使用synchronized成本太高了，需要先获取锁，最后需要释放锁，获取不到锁的情况下需要等待，还会有
        线程的上下文切换，这些都需要成本。

        对于这种情况，完全可以使用原子变量代替，Java并发包的基本原子变量类型有：
            1）AtomicBoolean：原子Boolean类型，常用来在程序中表示一个标志位。
            2）AtomicInteger：原子Integer类型
            3）AtomicLong：原子Long类型，常用来在程序中生成唯一序列号。
            4）AtomicReference：原子引用类型，用来以原子方式更新复杂类型。
        Java8新增，用于高并发统计汇总的场景中更为适合：
            LongAdder
            LongAccumulator
            DoubleAdder
            DoubleAccumulator

        AtomicInteger:
    1)基本用法
            public AtomicInteger(int initialValue) // 可设初始值
            public AtomicInteger() // 初始值为0

            // 直接获取或设置值
            public final int get() // 初始
            public final void set(int newValue)

            // 之所以成为原子变量，是因为它包含一些以原子方式实现组合操作的方法：
                public final int getAndSet(int newValue)
                public final int getAndIncrement()
                public final int getAndDecrement()
                public final int getAndAdd(int delta)
                public final int incrementAndGet()
                public final int decrementAndGet()
                public final int addAndGet(int delta)
                // 上面的方法的实现都依赖下面的public方法
                public final boolean compareAndSet(int expect, int update)

        compareAndSet是一个非常重要的方法，【比较并设置】，我们以后将简称为【CAS】。
            有两个参数expect和update，以原子方式实现了功能：
                如果当前值等于expect，则更新为update，否则不更新，
                如果更新成功，返回true，否则返回false。
        AtomicInteger可以在程序中用作一个计数器，多线程并发更新，也总能实现正确性。

    2）基本原理和思维
            //AtomicInteger的主要内部成员, volatile保证内存可见性
            private volatile int value;

            incrementAndGet源码：
            public final int incrementAndGet() {
                for(;;) {
                    int current = get();
                    int next = current + 1;
                    if(compareAndSet(current, next))
                        return next;
                }
            }
            // 代码驻地是一个死循环，先获取当前值current，计算期望的值next，然后调用CAS方法进行更新，如果更新没有成功，
            // 说明value被别的线程改了，则再去取最新值并尝试更新直到成功为止。

        原子变量的更新逻辑是非阻塞式的，更新冲突的时候，它就重试，不会阻塞，不会有上下文切换开销。对于大部分比较简单的操作，
        无论是在低并发还是高并发情况下，这种乐观非阻塞方式的性能都远高于悲观阻塞式方式。

        对于复杂一些的数据结构和算法，非阻塞方式往往难于实现和理解，Java并发包中已经提供了一些非阻塞容：
            ConcurrentLinkedQueue和ConcurrentLinkedDeque： 非阻塞并发队列。
            ConcurrentSkipListMap和ConcurrentSkipListSet： 非阻塞并发Map和Set。

        compareAndSet是怎么实现的：
           public final boolean compareAndSet(int expect, int update) {
                return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
           }
       unsafe“不安全” 一般应用程序不应该直接使用。原理上，一般的计算机系统都在硬件层次上直接支持CAS指令，而Java的实现都会利用
       这些特殊指令。

   3)实现锁
        基于CAS，除了可以实现乐观非阻塞算法之外，还可以实现悲观阻塞式算法。 比如锁。实际上，Java并发包中的所有阻塞式工具、容器、
        算法也都是基于CAS的（不过，也需要一些别的支持）。怎么实现锁？

        // 使用AtomicInteger实现锁MyLock
        // 这种锁过于消耗CPU，实际开发中应该用ReentrantLock（重入锁）
        public class MyLock {
            private AtomicInteger status = new AtomicInteger(0); // 0未锁定 1锁定
            public void lock() {
                while(!status.compareAndSet(0, 1)) {
                    Thread.yield();
                }
            }
            public void unlock() {
                status.compareAndSet(1, 0);
            }
        }


    1.2 ABA问题
            使用CAS方式更新有一个ABA问题：假设当前值为A，如果另一个线程先将A修改为B，再修改回A，当前线程的CAS操作无法分辨当前值
            发送过变化。

        ABA是不是问题，与程序的逻辑有关，一般不是问题。 而如果是问题，解决方法是使用
        AtomicStampedReference，在修改值的同时附加一个时间戳，只有值和时间戳都相同才进行修改，其CAS方法声明为：
            public boolean compareAndSet( V expectedReference, V newReference, int expectedStamp, int newStamp)
        比如：
        Pair pair = new Pair(100, 200);
        int stamp = 1;
        AtomicStampedReference<Pair> pairRef = new
                AtomicStampedReference<Pair>(pair, stamp);
        int newStamp = 2;
        pairRef.compareAndSet(pair, new Pair(200, 200), stamp, newStamp);

        对于并发环境中的计数、产生序列号等需求，应该使用原子变量而非锁，CAS是Java并发包的基础，基于它可以实现高效的、
        乐观、非阻塞式数据结构和算法，它也是并发包中锁、同步工具和各种容器的基础。
     */
    }
    @Test
    public void _2显示锁() throws Exception{/*
    Java并发包中的显示锁接口和类位于包java.util.concurrent.locks下，主要接口和类有：
        1）锁接口Lock，主要实现类是ReentrantLock；
        2）读写锁接口ReadWriteLock，主要实现类是ReentrantReadWriteLock。

        2.1 接口Lock
        public interface Lock {
            void lock(); // 获取锁，会阻塞直到成功
            void lockInterruptibly() throws InterruptedException; // 可响应中断
            boolean tryLock(); // 只是尝试获取锁，立即返回，不阻塞，获取成功返回true，否则返回false
            boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
            void unlock(); // 释放锁
            Condition newCondition(); // 新建一个条件， 一个Lock可以关联多个条件
        }

        相比synchronized，显示锁支持以非阻塞方式获取锁、可以响应中断、可以限时，灵活得多。

        2.2 可重入锁ReentrantLock
            下面，先介绍ReentrantLock的基本用法，然后重点介绍如何使用tryLock避免死锁。
        1.基本用法
            Lock接口的主要实现类是ReentrantLock，它的基本用法lock/unlock实现了与synchronized一样的语义，包括：
                1）可重入，一个线程在持有一个锁的前提下，可以继续获得该锁；
                2）可以解决静态条件问题
                3）可以保证内存可见性
            ReentrantLock有两个构造方法：
                public ReentrantLock()
                public ReentrantLock(boolean fair) // fair表示是否保证公平，默认false

                所谓公平是指，等待时间最长的线程优先获得锁。 保证公平会影响性能，一般也不需要，所以默认不保证，
                synchronized锁也是不保证公平的。

                显示锁一定要记得调用unlock，一般在finally语句内释放锁：
                public class Counter {
                    private final Lock lock = new ReentrantLock();
                    private volatile int count;
                    public void incr() {
                        lock.lock();
                        try {
                            count++;
                        } finally {
                            lock.unlock();
                        }
                    }
                    public int getCount() {
                        return count;
                    }
                }
        2.使用tryLock避免死锁
            使用tryLock()，可以避免死锁。 在持有一个锁获取另一个锁而获取不到的时候，可以释放已持有的锁，给其他线程获取锁的机会，
            然后重试获取所有锁。

        3.ReentrantLock的实现原理
            ReentrantLock的用法是比较简单的。 在最底层，它依赖于CAS方法，另外，它依赖于类LockSupport中的一些方法。
            1）LockSupport
                类LockSupport也位于包java。util.concurrent.locks下，方法有：
                    // 使当前线程放弃CPU，进入WAITING状态，操作系统不再对它进行调度。
                    // 有其他线程对它调用了unpark，unpark是参数指定的线程恢复可运行状态。
                    public static void park()
                    public static void parkNanos(long nanos) // 等到的最长时间，参数是相对于当前时间的纳秒数
                    public static void parkUntil(long deadline) // 等到什么时候，绝对时间，相对纪元时的毫秒数
                    public static void unpark(Thread thread)

           public static void main(String[] args) throws InterruptedException {
                Thread t = new Thread (){
                    public void run(){
                        LockSupport.park();    // 放弃CPU
                        System.out.println("exit");
                    }
                };
                t.start();    // 启动子线程
                Thread.sleep(1000);    // 睡眠1秒确保子线程先运行
                LockSupport.unpark(t);
            }

        park不同于Thread.yield()，yield只是告诉OS可以先让其他线程运行，但自己仍然是可运行状态，
        而park会放弃调度资格，实现从进入WAITING状态。

         LockSupport其他方法：
            public static void park(Object blocker) // 表示由于该对象而进行等待的，以便于调试。通常传this
            public static Object getBlocker(Thread t) // 可返回一个线程的blocker对象

        2）AQS：  AbstractQueuedSynchronizer
            利用CAS和LockSupport提供的基本方法，就可以用来实现ReentrantLock了。但
            Java中还有很多并发工具ReentrantReadWriteLock、Semaphore、CountDownLatch，它们的实现有很多类似的地方，
            为了复用代码，Java提供了一个抽象类AbstractQueuedSynchronizer，简称AQS，它简化了并发工具的实现。

        以ReentrantLock的使用为例进行简要介绍：
            AQS封装了一个状态，给紫烈提供了查询和设置状态的方法：
                private volatile int state;
                protected final int getState()
                protected final void setState(int newState)
                protected final boolean compareAndSetState(int expect, int update)

            用于实现锁时，AQS可以保存锁的当前持有线程，提供了方法进行查询和设置：
                private transient Thread exclusiveOwnerThread;
                protected final void setExclusiveOwnerThread(Thread t)
                protected final Thread getExclusiveOwnerThread()

            AQS内部维护了一个等待队列，借助CAS方法实现了无阻塞算法进行更新。


        4.对比ReentrantLock和synchronized
            相比synchronized，ReentrantLock可以实现与synchronized相同的语义，而且支持以非阻塞方式获取锁，可以响应中断，可以限时，
        更为灵活。不过，synchronized的使用更为简单，写的代码更少，也更不容易出错。
            synchronized代表一种声明式编程思维，程序员更多的是表达一种同步声明，有Java系统负责具体实现，程序员不知道其实现细节；
        显示锁代表一种命令式编程思维，程序员实现所有细节。
            Java编译器和虚拟机可以不断优化synchronized的实现。
     */
    }
    @Test
    public void _3显示条件() throws Exception{/*
    1.用法
        锁用于解决竞态条件问题，条件是线程间的协作机制。 显示锁于synchronized相对应，而显示条件与wait/notify相对应。wait/notify
        于synchronized配合使用，显示条件与显示锁配合使用。条件与锁相关联，创建条件变量需要通过显示锁，Lock接口定义了创建方法：
            Condition newCondition();
        Condition表示条件变量，是一个接口：
        public interface Condition {
          void await() throws InterruptedException;
          void awaitUninterruptibly();
          long awaitNanos(long nanosTimeout) throws InterruptedException;
          boolean await(long time, TimeUnit unit) throws InterruptedException; // wait
          boolean awaitUntil(Date deadline) throws InterruptedException;
          void signal(); // notify
          void signalAll(); // notifyAll
        }
     */

    }

}

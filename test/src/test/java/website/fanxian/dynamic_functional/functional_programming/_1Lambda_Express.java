package website.fanxian.dynamic_functional.functional_programming;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 26.1 Lambda表达式
 */
public class _1Lambda_Express {

    @Test
    public void _1通过接口传递代码() {/*
    接口常被用于传递代码
    File有方法：
        public File[] listFiles(FilenameFilter filter)
    listFiles需要的其实不是FilenameFilter对象，而是它包含的如下方法：
        boolean accept(File dir, String name);
    或者说，listFiles希望接收一段方法代码作为参数，但没有办法直接传递这个方法代码本身，只能传递一个接口。

    再如，类Collection中的很多方法都接受一个参数Comparator，比如：
        public static <T> void sort(List<T> list, Comparator<? super T> c)
    需要的也不是Comparator对象，而是方法：
        int compare(T o1, T o2);
    但是，没有办法直接传递方法，只能传递一个接口。

    又如，异步任务执行服务ExecutorService，提交任务的方法：
        <T> Future<T> submit(Callable<T> task);
        Future<?> submit(Runnable task);
    Callable和Runnable接口也用于传递任务代码。

    以上，最简洁的方式就是使用匿名内部类：
*/
        //列出当前目录下的所有扩展名为.txt的文件
        File f = new File("");
        File[] files = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".txt")) {
                    return true;
                }
                return false;
            }
        });

        //将files按照文件名排序
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });

        // 提交一个最简单的任务：
        ExecutorService executor = Executors.newFixedThreadPool(100);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        });
    }
    @Test
    public void _2Lambda语法(){/*
     */
        File f = new File(".");
        File[] files = f.listFiles((File dir, String name) -> name.endsWith(".txt"));

        Arrays.sort(files, (f1, f2) -> f1.getName().compareTo(f2.getName()));

        ExecutorService executor = Executors.newFixedThreadPool(100);
        executor.submit(()->System.out.println("hello"));

        /**
         * 与匿名内部类类似，只能访问final类型的变量，
         *      它不要求变量声明为final，但变量事实上不能被重新赋值。比如：
         */
        String msg = "hello world";
//        msg = "good morning";  //报错，不能被重新赋值
        executor.submit(()->System.out.println(msg));

        /**
         * lambda内部访问的是变量副本。
         *    因为msg定义在栈中，当lambda表达式被执行的时候，msg可能早已经被释放了。
         *
         *  如果希望能够修改值，可以
         *      将变量定义为实例变量，或者将变量定义为数组，比如：
         */
        String[] msgArr = new String[]{"hello world"};
        msgArr[0] = "good morning";
        executor.submit(() -> System.out.println(msgArr[0]));

        /**
         * lambda虽然与匿名内部类很像，但不是语法糖，其内部实现并非内部类。
         * Java会为每个匿名内部类生成一个类，但lambda不会。
         *
         * lambda内部，Java利用了Java7引入的支持动态语言引入的invoked dynamic指令、方法句柄（method handle）等。
         * 具体实现比较复杂： http://cr.openjdk.java.net/~briangoetz/lambda/lambda-translation.html
         */
    }
    @Test
    public void _3函数式接口(){/*
        函数式接口也是接口，但只能有一个抽象方法。
        都有一个注解@FunctionalInterface（可选）
        @FunctionalInterface
        public interface Runnable{
            public abstract void run();
        }
*/
    }
    @Test
    public void _4预定义的函数式接口(){/*
    // 1 Predicate
    public static <E> List<E> filter(List<E> list, Predicate<E> pred) {
        List<E> retList = new ArrayList<>();
        for(E e : list) {
            if(pred.test(e)) {
                retList.add(e);
            }
        }
        return retList;
    }
    students = filter(students, t -> t.getScore() > 90); // 90分以上

    // 2 Function
    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        List<R> retList = new ArrayList<>(list.size());
        for(T e : list) {
            retList.add(mapper.apply(e));
        }
        return retList;
    }
    List<String> names = map(students, t -> t.getName());

    // 3 Consumer
    public static <E> void foreach(List<E> list, Consumer<E> consumer) {
        for(E e : list) {
            consumer.accept(e);
        }
    }
    foreach(students, t -> t.setName(t.getName().toUpperCase()));
*/
    }
    @Test
    public void _5方法引用(){/*
     */
    }
    @Test
    public void _6函数的复合(){/*
     */
    }
    @Test
    public void _7小结(){/*
     */
    }
}

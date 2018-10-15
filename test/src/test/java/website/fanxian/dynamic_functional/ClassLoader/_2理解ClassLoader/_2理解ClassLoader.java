package website.fanxian.dynamic_functional.ClassLoader._2理解ClassLoader;

import org.junit.Test;

/**
 * 24.2 理解ClassLoader
 * 类ClassLoader是一个抽象类，Application ClassLoader和Extension ClassLoader的具体实现类分别是
 * sun.misc.Launcher$AppClassLoader和sun.misc.Launcher$ExtClassLoader，Bootstrap ClassLoader不是有Java
 * 实现的，没有对应的类。
 */
public class _2理解ClassLoader {/*
    每个Class对象都有一个方法，可以获取实际加载它的ClassLoader，方法是：
        public classLoader getClassLoader()
    ClassLoader有一个方法，可以获取它的父ClassLoader：
        public final classLoader getParent()
    如果ClassLoader是Bootstrap ClassLoader，返回值为null。比如：
*/
   @Test
    public void test1() {
       ClassLoader c1 = getClass().getClassLoader();
       while(c1 != null){
           System.out.println(c1.getClass().getName());
           c1 = c1.getParent();
       }
       System.out.println(String.class.getClassLoader());
   }

/*
    ClassLoader有一个静态方法，可以获取默认的系统类加载器：
        public static ClassLoader getSystemClassLoader()
    ClassLoader中有一个主要方法，用于加载类：
        public Class<?> loadClass(String name) throws ClassNotFoundException

    比如：
*/
    @Test
    public void test2() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            Class<?> cls = cl.loadClass("java.util.ArrayList");
            ClassLoader actualLoader = cls.getClassLoader();
            System.out.println(actualLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

/*
    需要说明的是，由于委派机制，Class的getClassLoader方法返回的不一定是调用loadClass的
    ClassLoader，比如，上面代码中，java.util.ArrayList实际有BootStrap ClassLoader加载，
    所以返回值就是null。

    在反射一章，我们介绍过Class的两个静态方法forName：
    public static Class<?> forName(String className)
    public static Class<?> forName(String name, boolean initialize, ClassLoader loader)
*/
}
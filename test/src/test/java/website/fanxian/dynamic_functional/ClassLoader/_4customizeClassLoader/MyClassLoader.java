package website.fanxian.dynamic_functional.ClassLoader._4customizeClassLoader;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * name表示类名，b是存放字节码数据的字节数组，有效数据从off开始，长度为len。
 */
public class MyClassLoader extends ClassLoader {
    private static final String BASE_DIR = "data/c87/";
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = name.replaceAll("\\.", "/");
        fileName = BASE_DIR + fileName + ".class";
        try {
//            byte[] bytes = BinaryFileUtils.readFileToByteArray(fileName);
            byte[] bytes = FileCopyUtils.copyToByteArray(new File(fileName));
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException ex) {
            throw new ClassNotFoundException("failed to load class " + name, ex);
        }
    }

    /**
     * MyClassLoader没有指定父ClassLoader，默认是系统类加载器，即
     * ClassLoader.getSystemClassLoader()的返回值，
     * 不过，ClassLoader有一个可重写的构造方法，可以指定父ClassLoader：
     *   protected ClassLoader(ClassLoader parent)
     */

    /**
     * MyClassLoader有什么用呢？将BASE_DIR加到classpath中不就行了？ 确是可以，这里主要是演示基本用法，
     * 实际中，可以从Web服务器、数据库或缓存服务器获取bytes数组，这就不是系统类加载器能做到的了。
     */

    /**
     *  不过，不把BASE_DIR放到classpath中，而是使用MyClassLoader加载，还有一个很大的好处，那就是可以
     *   创建多个MyClassLoader，
     *   对同一个类，每个MyClassLoader都可以加载一次，得到同一个类的不同
     *   Class对象，比如：
     */
    void test1() throws Exception{
        MyClassLoader cl1 = new MyClassLoader();
        String className = "shuo.laoma.dynamic.c87.HelloService";
        Class<?> class1 = cl1.loadClass(className);

        MyClassLoader cl2 = new MyClassLoader();
        Class<?> class2 = cl2.loadClass(className);
        if(class1 != class2) {
            System.out.println("different classes");
        }
    }

    /**
     * 有不同的类对象，有什么用？
     *  1）可以实现隔离。
     *      一个复杂的程序，内部可能按模块组织，不同模块可能使用同一个类，但使用的是不同版本，如果使用同一个类加载器，
     *      他们是无法共存的，不同模块使用不同的类加载器就可以实现隔离，Tomcat使用它隔离不同的web应用，OSGI使用它隔离不同模块。
     *
     *  2）可以实现热部署。
     *          使用同一个ClassLoader，类只会被加载一次，加载后，即使class文件已经变了，再次加载，得到的也
     *      还是原来的Class对象，
     *          而使用MyClassLoader，则可以先创建一个新的ClassLoader，在用它加载Class，
     *      得到的Class对象就是新的，从而实现动态更新。
     */
}

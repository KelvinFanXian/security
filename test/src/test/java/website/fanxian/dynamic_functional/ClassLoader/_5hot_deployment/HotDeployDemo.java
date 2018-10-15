package website.fanxian.dynamic_functional.ClassLoader._5hot_deployment;

import website.fanxian.dynamic_functional.ClassLoader._4customizeClassLoader.MyClassLoader;

import java.io.File;

/**
 * @author Kelvin范显
 * @createDate 2018年10月15日
 */
public class HotDeployDemo {
    private static final String CLASS_NAME = "shuo.laoma.dynamic.c87.HelloImpl";
    private static final String FILE_NAME = "data/c87/"
            +CLASS_NAME.replaceAll("\\.", "/")+".class";
    private static volatile IHelloService helloService;


    public static IHelloService getHelloService() {
        if(helloService != null) {
            return helloService;
        }
        synchronized (HotDeployDemo.class) {
            if(helloService == null) {
                helloService = createHelloService();
            }
            return helloService;
        }
    }

    private static IHelloService createHelloService() {
        try {
            MyClassLoader cl = new MyClassLoader();
            Class<?> cls = cl.loadClass(CLASS_NAME);
            if(cls != null) {
                return (IHelloService) cls.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 模拟一个客户端线程，它不停地获取IHelloService对象，并调用其方法，然后睡1秒钟
     */
    public static void client() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        IHelloService helloService = getHelloService();
                        helloService.sayHello();
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    /**
     * 怎么知道类的class文件发生了变化，并重新创建helloService对象呢？
     * 我们使用一个单独的线程模拟这一过程
     */
    public static void monitor() {
        Thread t = new Thread() {
            private long lastModified = new File(FILE_NAME).lastModified();
            @Override
            public void run() {
                try {
                    while(true) {
                        Thread.sleep(100);
                        long now = new File(FILE_NAME).lastModified();
                        if(now != lastModified) {
                            lastModified = now;
                            reloadHelloService();
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    /**
     * 我们使用文件的最后修改时间来跟踪文件是否发生了变化，当文件修改后，
     * 调用reloadHelloService()来重新加载：
     */
    public static void reloadHelloService() {
        helloService = createHelloService();
    }

    public static void main(String[] args) {
        monitor();
        client();
    }
}

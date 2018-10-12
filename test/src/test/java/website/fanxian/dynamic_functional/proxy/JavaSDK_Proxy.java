package website.fanxian.dynamic_functional.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  23.2 Java SDK动态代理
 *   用法
 *   原理
 *   优点
 */
public class JavaSDK_Proxy {

    interface IService {
        void sayHello();
    }
    static class RealService implements IService {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }

    /**
     * key1 实现InvocationHandler，织入跟踪测试逻辑
     */
    static class SimpleInvocationHandler implements InvocationHandler {
        private Object realObj;
        public SimpleInvocationHandler(Object realObj) {
            this.realObj = realObj;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("entering " + method.getName());
            Object result = method.invoke(realObj, args);
            System.out.println("leaving " + method.getName());
            return result;
        }
    }

//    public static void main(String[] args) {
//        IService realService = new RealService();
//        /**
//         * key2 通过Proxy.newProxyInstance方法生成代理
//         */
//        IService proxyService = (IService) Proxy.newProxyInstance(
//                IService.class.getClassLoader(), // ClassLoader loader
//                new Class<?>[]{IService.class},  // Class<?>[] interfaces
//                new SimpleInvocationHandler(realService)  // InvocationHandler
//        );
//        proxyService.sayHello();
//    }

    private static <T> T getProxy(Class<T> intf, T realObj) {
        return (T) Proxy.newProxyInstance(intf.getClassLoader(),
                new Class[]{intf},
                new SimpleInvocationHandler(realObj));
    }
    interface IServiceB {
        void fly();
    }
    static class ServiceB implements IServiceB {
        @Override
        public void fly() {
            System.out.println("flying");
        }
    }
    public static void main(String[] args) {
        IService realService = new RealService();
        IService proxy = getProxy(IService.class, realService);
        proxy.sayHello();

        ServiceB realServiceB = new ServiceB();
        IServiceB proxy1 = getProxy(IServiceB.class, realServiceB);
        proxy1.fly();
    }
}

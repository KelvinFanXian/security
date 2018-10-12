package website.fanxian.dynamic_functional.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 23.3 cglib动态代理
 */
public class cglib_Proxy {
    /**
     * Java SDK动态代理的局限在于，
     * 它只能为接口创建代理，
     * 返回的代理对象也只能转换到某个接口类型
     * <p>
     * <p>
     * 如果一个类没有接口，
     * 或者希望代理非接口中定义的方法，那么就要借助第三方类库cglib：
     * https://github.com/cglib/cglib
     * Spring、Hibernate等都使用该类库
     */

    static class RealService {
        public void sayHello() {
            System.out.println("hello");
        }
    }
    static class SimpleInterceptor implements MethodInterceptor {
        public Object intercept(Object object, Method method,
                                Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("entering " + method.getName());
            Object result = proxy.invokeSuper(object, args);
            System.out.println("leaving " + method.getName());
            return result;
        }
    }
    private static <T> T getProxy(Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new SimpleInterceptor());
        return (T) enhancer.create();
    }
    public static void main(String[] args) {
        RealService proxyService = getProxy(RealService.class);
        proxyService.sayHello();
    }

    /**
     * cglib的实现机制与Java SDK不同，它是通过继承实现的。
     *   它也是动态的创建一个类，但这个类的父类是被代理的类，
     *   代理类重写了父类的所有public非final方法，
     *   该为调用Callback中的相关方法
     *   在上例中，调用SimpleInterceptor的intercept方法。
     */
}

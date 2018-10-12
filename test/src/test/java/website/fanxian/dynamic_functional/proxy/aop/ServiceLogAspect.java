package website.fanxian.dynamic_functional.proxy.aop;

import website.fanxian.dynamic_functional.annotation.DIContainer.ServiceA;
import website.fanxian.dynamic_functional.annotation.DIContainer.ServiceB;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Kelvin范显
 * @createDate 2018年10月12日
 */
@Aspect({ ServiceA.class, ServiceB.class })
public class ServiceLogAspect {
    public static void before(Object object, Method method, Object[] args) {
        System.out.println("entering " + method.getDeclaringClass()
                .getSimpleName() + "::" + method.getName()
                + ", args: " + Arrays.toString(args));
    }
    public static void after(Object object, Method method, Object[] args,
                             Object result) {
        System.out.println("leaving " + method.getDeclaringClass()
                .getSimpleName() + "::" + method.getName()
                + ", result: " + result);
    }
}

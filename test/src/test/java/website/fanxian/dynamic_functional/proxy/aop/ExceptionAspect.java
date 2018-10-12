package website.fanxian.dynamic_functional.proxy.aop;

import website.fanxian.dynamic_functional.annotation.DIContainer.ServiceB;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Kelvin范显
 * @createDate 2018年10月12日
 */
@Aspect({ ServiceB.class })
public class ExceptionAspect {
    public static void exception(Object object,
                                 Method method, Object[] args, Throwable e) {
        System.err.println("exception when calling: "
                + method.getName() + "," + Arrays.toString(args));
    }
}


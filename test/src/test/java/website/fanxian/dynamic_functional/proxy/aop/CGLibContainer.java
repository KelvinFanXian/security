package website.fanxian.dynamic_functional.proxy.aop;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import sun.reflect.misc.ReflectUtil;
import website.fanxian.dynamic_functional.annotation.DIContainer.ServiceA;
import website.fanxian.dynamic_functional.annotation.DIContainer.SimpleInject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static sun.reflect.misc.MethodUtil.getMethod;

/**
 * @author Kelvin范显
 * @createDate 2018年10月12日
 */
public class CGLibContainer{

    /**
     * 定义切点
     */
    public static enum InterceptPoint {
        BEFORE, AFTER, EXCEPTION
    }

    /**
     * 类的每个切点的方法列表
     */
    static Map<Class<?>, Map<InterceptPoint, List<Method>>> interceptMethodsMap
            = new HashMap<>();


    /**
     *  分析每个带@Aspect注解的类
     */
    static Class<?>[] aspects = new Class<?>[] {
            ServiceLogAspect.class, ExceptionAspect.class };

    static {
        init();
    }
    private static void init() {
        for(Class<?> cls : aspects) {
            Aspect aspect = cls.getAnnotation(Aspect.class);
            if(aspect != null) {
                Method before = getMethod(cls, "before", new Class<?>[] {
                        Object.class, Method.class, Object[].class });
                Method after = getMethod(cls, "after", new Class<?>[] {
                        Object.class, Method.class, Object[].class, Object.class });
                Method exception = getMethod(cls, "exception", new Class<?>[] {
                            Object.class, Method.class, Object[].class, Throwable.class });
                Class<?>[] intercepttedArr = aspect.value();
                for(Class<?> interceptted : intercepttedArr) {
                    addInterceptMethod(interceptted,
                            InterceptPoint.BEFORE, before);
                    addInterceptMethod(interceptted,
                            InterceptPoint.AFTER, after);
                    addInterceptMethod(interceptted,
                            InterceptPoint.EXCEPTION, exception);
                }
            }
        }
    }

    // sun.reflect.misc.MethodUtil.getMethod
    public static Method getMethod(Class<?> var0, String var1, Class<?>[] var2) {
        ReflectUtil.checkPackageAccess(var0);
        try {
            return var0.getMethod(var1, var2);
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        }
        return null;
    }

    private static void addInterceptMethod(Class<?> cls,
                                           InterceptPoint point, Method method) {
        if(method == null) {
            return;
        }
        Map<InterceptPoint, List<Method>> map = interceptMethodsMap.get(cls);
        if(map == null) {
            map = new HashMap<>();
            interceptMethodsMap.put(cls, map);
        }
        List<Method> methods = map.get(point);
        if(methods == null) {
            methods = new ArrayList<>();
            map.put(point, methods);
        }
        methods.add(method);
    }

    private static <T> T createInstance(Class<T> cls)
            throws InstantiationException, IllegalAccessException {
        if(!interceptMethodsMap.containsKey(cls)) {
            return cls.newInstance();
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new AspectInterceptor());
        return (T) enhancer.create();
    }

    static class AspectInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object object, Method method,
                                Object[] args, MethodProxy proxy) throws Throwable {
            // 执行before方法
            List<Method> beforeMethods = getInterceptMethods(
                    object.getClass().getSuperclass(), InterceptPoint.BEFORE);
            for(Method m : beforeMethods) {
                m.invoke(null, new Object[] { object, method, args });
            }
            try {
                // 调用原始方法
                Object result = proxy.invokeSuper(object, args);
                // 调用after方法
                List<Method> afterMethods = getInterceptMethods(
                        object.getClass().getSuperclass(), InterceptPoint.AFTER);
                for(Method m : afterMethods) {
                    m.invoke(null, new Object[] { object, method, args, result });
                }
                return result;
            } catch (Throwable e) {
                // 执行exception方法
                List<Method> exceptionMethods = getInterceptMethods(
                        object.getClass().getSuperclass(), InterceptPoint.EXCEPTION);
                for(Method m : exceptionMethods) {
                    m.invoke(null, new Object[] { object, method, args, e });
                }
                throw e;
            }
        }
    }

    static List<Method> getInterceptMethods(Class<?> cls,
                                            InterceptPoint point) {
        Map<InterceptPoint, List<Method>> map = interceptMethodsMap.get(cls);
        if(map == null) {
            return Collections.emptyList();
        }
        List<Method> methods = map.get(point);
        if(methods == null) {
            return Collections.emptyList();
        }
        return methods;
    }

    public static <T> T getInstance(Class<T> cls) {
        try {
            T obj = createInstance(cls);
            Field[] fields = cls.getDeclaredFields();
            for(Field f : fields) {
                if(f.isAnnotationPresent(SimpleInject.class)) {
                    if(!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    Class<?> fieldCls = f.getType();
                    f.set(obj, getInstance(fieldCls));
                }
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ServiceA a = getInstance(ServiceA.class);
        a.callB();
    }
}

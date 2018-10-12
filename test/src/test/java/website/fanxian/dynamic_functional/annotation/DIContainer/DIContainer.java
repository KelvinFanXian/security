package website.fanxian.dynamic_functional.annotation.DIContainer;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class DIContainer {
    private static Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

    public static <T> T getInstance(Class<T> cls) {
        try {
            boolean singleton = cls.isAnnotationPresent(SimpleSingleton.class);
            if(!singleton) {
                return createInstance(cls);
            }

            Object obj = instances.get(cls);
            if(obj != null) {
                return (T) obj;
            }
            synchronized (cls) { // 使用类型作为锁
                obj = instances.get(cls);
                if(obj == null) { // 在第一个进程创建实例的过程中产生的等待队列，会进入同步块，需再次判断
                    obj = createInstance(cls);
                    instances.put(cls, obj);
                }
            }
            return (T) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> T createInstance(Class<T> cls) throws Exception {
        T obj = cls.newInstance();
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
        return obj; }


    public static void main(String[] args) {
        ServiceA a = getInstance(ServiceA.class);
        a.callB();
    }
}
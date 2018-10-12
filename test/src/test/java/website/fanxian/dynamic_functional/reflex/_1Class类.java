package website.fanxian.dynamic_functional.reflex;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * 21.1 _1Class类
 */
public class _1Class类 {/*
        Object类有一个方法，可以获取对象的Class对象：
            public final native class<?> getClass()

        可以通过 <类名>.class获取Class对象：
            Class<Date> cls = Date.class;

        接口也适用：
            Class<Comparable> cls = Comparable.class;

        基本类型没有getClass方法，但也都有对应的Class对象，类型参数为对应的包装类型，比如：
            Class<Integer> intCls = int.class;
            Class<Byte> byteCls = byte.class;
            Class<Character> charCls = char.class;
            Class<Double> doubleCls = double.class;

        void作为特殊的返回类型，也有对应的Class
            Class<Void> voidCls = void.class;

        对于数组，每种类型都有对应数组类型的Class对象，每个维度都有一个，即一维数组有一个，二维数组有一个不同的类型。比如：
            String[] strArr = new String[10];
            int[][] twoDimArr = new int[3][2];
            int[] oneDimArr = new int[10];
            Class<? extends String[]> strArrCls = strArr.getClass();
            Class<? extends int[][]> twoDimArrCls = twoDimArr.getClass();
            Class<? extends int[]> oneDimArrCls = oneDimArr.getClass();

        枚举类型也有对应的Class，比如：
            enum Size{
                SMALL, MEDIUM, BIG
            }
            Class<Size> sizeClass = Size.class;

        Class有一个静态方法forName,可以根据类名直接加载Class，获取Class对象，比如：
            try {
                Class<?> cls = Class.forName("java.util.HashMap");
                System.out.println(cls.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

 */
    public static final int MAX_NAME_LEN = 255;

    /**
     * Class对象的方法，分组介绍：
     * 名称信息、字段信息、方法信息、创建对象、构造方法、类型信息
     */

    @Test
    public void _1名称信息(){/*
        public String getName() // Java内部使用的真正的名称
        public String getSimpleName() // 不带包信息的名称
        public String getCanonicalName() // 更为友好的名称
        public Package getPackage() // 包信息
     */
    }
    @Test
    public void _2字段信息() throws Exception {/*
        Class有4个获取字段信息的方法：
            public Field[] getFields() // 返回所有的public字段，包括其父类的，如果没有字段，返回空数组
            public Field[] getDeclaredFields() // 返回本类声明的所有字段，包括非public的，但不包括父类的
            public Field getField(String name) // 返回本类或父类中指定名称的public字段，找不到抛出异常NoSuchFieldException
            public Field getDeclaredField(String name) // 返回本类中声明的指定名称字段，找不到抛出异常NoSuchFieldException

        Field的方法：
            public String getName()
            public boolean isAccessible()
            public void setAccessible(boolean flag)
            public Object get(Object obj) // 获取指定对象obj中该字段的值
            public void set(Object obj, Object value) // 将指定对象obj中该字段的值设为value

            public int getModifiers() // 字段修饰符
            public Class<?> getType() // 字段类型
            // 基本类型操作字段
            public void setBoolean(Object obj, boolean z)
            public boolean getBoolean(Object obj)
            public void setDouble(Object obj, double d)
            public double getDouble(Object obj
            // 查询字段的注解信息
            public <T extends Annotation> T getAnnotation(Class<T> annotationClass)
            public Annotation[] getDeclaredAnnotations()
     */

        List<String> obj = Arrays.asList(new String[]{"老马","编程"});
                Class<?> cls = obj.getClass();
        for(Field f : cls.getDeclaredFields()){
            f.setAccessible(true);
            System.out.println(f.getName()+" - "+f.get(obj));
        }

        // Modifier
        Field f = _1Class类.class.getField("MAX_NAME_LEN");
        int mod = f.getModifiers();
        System.out.println(Modifier.toString(mod));
        System.out.println(Modifier.isStatic(mod));
        System.out.println(Modifier.isFinal(mod));
        System.out.println(Modifier.isVolatile(mod));
    }
    @Test
    public void _3方法信息() throws Exception{/*
        方法，用Method表示，Class有如下相关方法：
            public Method[] getMethods()
            public Method[] getDeclaredMethods()
            public Method getMethod(String name, Class<?>... parameterTypes)
            public Method getDeclaredMethod(String name, Class<?>... parameterTypes)

        Method的方法：
            public String getName()
            public void setAccessible(boolean flag)
     */
        // invoke方法，，如果Method为静态方法，obj被忽略，可以为null
        Class<?> cls = Integer.class;
        try {
            Method method = cls.getMethod("parseInt", new Class[]{String.class});
            System.out.println(method.invoke(null, "123"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void _4创建对象和构造方法(){/*
     */
    }
    @Test
    public void _5类型检查和转换(){/*
     */
    }
    @Test
    public void _6Class的类型信息(){/*
     */
    }
    @Test
    public void _7类的声明信息(){/*
     */
    }
    @Test
    public void _8类的加载(){/*
     */
    }
    @Test
    public void _9反射与数组(){/*
     */
    }
    @Test
    public void _10反射与枚举(){/*
     */
    }
}


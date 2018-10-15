package website.fanxian.dynamic_functional.reflex;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    public void _4创建对象和构造方法() throws Exception{/*
        Class有一个方法，可以用来创建对象：
            public T newInstance() throws InstantiationException, IllegalAccessException
     */
        HashMap map = HashMap.class.newInstance();
        map.put("hello", 123);

        /*
        newInstance只能使用默认构造方法。Class还有一些方法，可以获取所有的构造方法:
            public Constructor<?>[] getConstructors()
            public Constructor<?>[] getDeclaredConstructors()
            public Constructor<T> getConstructor(Class<?>... parameterTypes)
            public Constructor<T> getDeclaredConstructor(Class<?>... parameterTypes)
*/

        /*
        类Constructor表示构造方法，通过它可以创建对象，方法为：
        public T newInstance(Object ... initargs) throws InstantiationException,
        IllegalAccessException, IllegalArgumentException, InvocationTargetException
 */
        Constructor<StringBuilder> constructor = StringBuilder.class
                .getConstructor(new Class[]{int.class});
        StringBuilder sb = constructor.newInstance(100);
    }
    @Test
    public void _5类型检查和转换() throws Exception{/*
        instanceof关键字，可以用来判断变量指向的实际对象类型。 instanceof后面的类型
    是在代码中确定的，如果要检查的类型是动态的，可以使用Class类的如下方法：
        public native boolean isInstance(Object obj)
     */
        List list = new ArrayList();
        if (list instanceof ArrayList) {
            System.out.println("array list");
        }
        // 上面代码，和下面代码同
        Class cls = Class.forName("java.util.ArrayList");
        if (cls.isInstance(list)) {
            System.out.println("array list");
        }

        /**
         * 类型转换
         */
        if (list instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) list;
        }
        /*如果是动态的，可以使用Class的如下方法：
            public T cast(Object obj)
        比如：
        punlic static <T> T toType(Object obj, Class<T> cls){
            return cls.cast(obj);
        }
*/
        ArrayList castAsList = ArrayList.class.cast(list);

        /*
        isInstance/cast描述的都是对象和类之间的关系，Class还有一个方法，可以判断Class之间的关系：
        public native boolean isAssignableFrom(Class<?> cls);
 */
        // 下面返回都是true
        Object.class.isAssignableFrom(String.class);
        String.class.isAssignableFrom(String.class);
        List.class.isAssignableFrom(ArrayList.class);
    }
    @Test
    public void _6Class的类型信息(){/*
        public native boolean isArray()  // 数组？
        public native boolean isPrimitive()  // 基本类型？
        public native boolean isInterface()  // 接口？
        public boolean isEnum()  // 枚举？
        public boolean isAnnotation()  // 注解？
        public boolean isAnonymousClass()  // 匿名内部类？
        public boolean isMemberClass()  // 成员类？
        public boolean isLocalClass()  // 本地类？
 */
    }
    @Test
    public void _7类的声明信息() throws Exception{/* Class类的方法： 修饰符、父类、接口、注解
        public native int getModifiers()
        public native Class<? super T> getSuperclass()
        public native Class<?>[] getInterfaces()
        public Annotation[] getDeclaredAnnotations()
        public Annotation[] getAnnotations() // 所有的注解，包括继承得到的
        public <A extends Annotation> A getAnnotation(Class<A> annotationClass)
        public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass)
 */
    }
    @Test
    public void _8类的加载() throws Exception{/* Class有两个静态方法，可以根据类名加载类：
        public static Class<?> forName(String className)
        public static Class<?> forName(String name, boolean initialize,
            ClassLoader loader)
 */
        // 这里className与Class.getName的返回值是一致的。比如，对于String数组：
        String name = "[Ljava.lang.String;";
        Class cls = Class.forName(name);
        System.out.println(cls == String[].class);//true
    }
    @Test
    public void _9反射与数组(){/*
        对于数组类型，获取它的元素类型：
            public native Class<?> getComponentType()
     */
        String[] arr = new String[]{};
        Class<?> cls = arr.getClass().getComponentType(); //class java.lang.String
    /*
        public static Object newInstance(Class<?> componentType, int length) // 指定长度
        public static Object newInstance(Class<?> componentType, int... dimensions) // 多维数组
        public static native Object get(Object array, int index) // 指定的索引位置index出的值
        public static native void set(Object array, int index, Object value) // 修改数组array指定的索引位置index出的值为value
        public static native int getLength(Object array) // 返回数组的长度
 */
        int[] intArr = (int[])Array.newInstance(int.class, 10);
        String[] strArr = (String[])Array.newInstance(String.class, 10);

    /*
        public static native double getDouble(Object array, int index)
        public static native void setDouble(Object array, int index, double d)
        public static native void setLong(Object array, int index, long l)
        public static native long getLong(Object array, int index)
 */
    }
    enum A{
        a,o,e
    }
    @Test
    public void _10反射与枚举(){/*
        public T[] getEnumConstants()
 */
        A[] enumConstants = A.class.getEnumConstants();
        System.out.println(enumConstants); // [a,o,e]
    }
}


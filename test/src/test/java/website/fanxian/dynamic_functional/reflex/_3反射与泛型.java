package website.fanxian.dynamic_functional.reflex;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kelvin范显
 * @createDate 2018年10月15日
 */
public class _3反射与泛型 {/*
    泛型参数在运行时会被擦除。 但在类信息Class中依然有关于泛型的一些信息，可以通过反射得到。

    Class有如下方法，可以获取类的泛型参数信息：
        public <Class<T>>[] getTypeParameters()

    Field有如下方法：
        public Type getGenericType()

    Method有如下方法：
        public Type getGenericReturnType()
        public Type[] getGenericParameterTypes()
        public Type[] getGenericExceptionTypes()

    Constructor有如下方法：
        public Type[] getGenericParameterTypes()

    Type是一个接口，Class实现了Type，Type的其他子接口还有：
        TypeVariable: 类型参数，可以有上界，比如 T extends Number
        ParameterizedType: 参数化的类型，有原始类型和具体的类型参数，比如List；
        WildcardType: 通配符类型， 比如?、?extends Number、 ? super Integer。
*/

    static class GenericTest<U extends Comparable<U>, V> {
        U u;
        V v;
        List<String> list;
        public U test(List<? extends Number> numbers) {
            return null;
        }
    }
    public static void main(String[] args) throws Exception {
        Class<?> cls = GenericTest.class;
        // 类的类型参数
        for(TypeVariable t : cls.getTypeParameters()) {
            System.out.println(t.getName() + " extends " +
                    Arrays.toString(t.getBounds()));
        }
        // 字段：泛型类型
        Field fu = cls.getDeclaredField("u");
        System.out.println(fu.getGenericType());
        // 字段：参数化的类型
        Field flist = cls.getDeclaredField("list");
        Type listType = flist.getGenericType();
        if(listType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) listType;
            System.out.println("raw type: " + pType.getRawType()
                    + ",type arguments:"
                    + Arrays.toString(pType.getActualTypeArguments()));
        }
        // 方法的泛型参数
        Method m = cls.getMethod("test", new Class[] { List.class });
        for(Type t : m.getGenericParameterTypes()) {
            System.out.println(t);
        }
    }

    /**
     * 反射虽然灵活，但一般不推荐使用。 原因：
     *  1）反射更容易出现运行时错误。因为编译器没法对反射做类型检查
     *  2) 反射性能要低一些。 在访问字段，调用方法前，反射先要查找对应的Field/Method，要慢一些。
     */
}

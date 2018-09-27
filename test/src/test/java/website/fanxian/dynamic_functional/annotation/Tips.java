package website.fanxian.dynamic_functional.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 *  annotation tips
 */
public class Tips {

    public void _22_4查看注解信息(){
        /**
        Class Field Method  Constructor 这些类中都有下面的方法：

        获取所有的注解
        public Annotation[] getAnnotations()

        获取所有本元素上直接声明的注解，忽略inherited过来的
        public Annotation[] getDeclaredAnnotations()

        获取指定类型的注解，没有则返回null
        public <A extends Annotation> A getAnnotation(Class<A> annotationClass)

        判断是否有指定类型的注解
        public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass)

        Method & Constructor，都有方法注解，而参数也可以有注解，所以它们都有如下方法
        返回值是一个二维数组，每个参数对应一个一维数组
        public Annotation[][] getParameterAnnotations()

         定义了注解，通过反射获取到注解信息。
        */
    }

    public void _22_5定制序列化(){
        /**

         */
    }

    public void _22_6DI容器(){
        /**

         */
    }



}

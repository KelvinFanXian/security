package website.fanxian.dynamic_functional.proxy.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Kelvin范显
 * @createDate 2018年10月12日
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Aspect {
    Class<?>[] value();
}

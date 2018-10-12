package website.fanxian.dynamic_functional.annotation.DIContainer;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Kelvin范显
 * @createDate 2018年10月12日
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface SimpleInject {
}

/**
 * 23.5 动态代理的应用：AOP
 *  利用cglib动态代理，实现一个极简的AOP框架
 */


/**
 * 用于注解切面类，它有一个参数，可以指定要增强的类，比如：
 *
 * > @Aspect({ServiceA.class,ServiceB.class})
 * > public class ServiceLogAspect
 * ServiceLogAspect就是一个切面，负责类ServiceA,ServiceB的日志切面，即为这两个类增加日志功能。
 *
 *> @Aspect({ServiceB.class})
 *> public class ExceptionAspect
 *ExceptionAspect也是一个切面，它负责类ServiceB的异常切面
 *
 */
package website.fanxian.dynamic_functional.proxy.aop;
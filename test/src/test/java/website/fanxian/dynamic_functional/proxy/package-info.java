/** 第23章
 *  动态代理可以在在运行时动态创建
 *  一个类，
 *  实现一个或多个接口，
 *  在不修改原有类的基础上，动态通过该类获取的对象添加方法、修改行为等。
 *  这些特性广泛用于各种框架： Spring、Hibernate、MyBaties、Guice等。
 *
 *  动态代理是实现AOP的基础
 *
 *  要理解动态代理，首先了解静态代理。
 *
 *  动态代理有两种实现方式：
 *      1. Java SDK提供的
 *      2. 第三方库提供（如cglib）
 */
package website.fanxian.dynamic_functional.proxy;

/**
 * 24.4 自定义ClassLoader
 *  Java类加载机制的强大之处在于，我们可以创建自定义的ClassLoader，自定义Class-Loader是
 *  Tomcat实现应用隔离、支持JSP、OSGI实现动态模块化的基础
 *
 *  继承类ClassLoader，重写findClass
 *
 *  怎么实现findClass呢？ 使用自己的逻辑寻找class文件字节码的 【字节形式】，
 *  找到后，使用如下方法转换为 【Class对象】：
 *      protected final Class<?> defineClass(String name, byte[] b, int off, int len)
 */
package website.fanxian.dynamic_functional.ClassLoader._4customizeClassLoader;
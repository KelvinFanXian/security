/**
 * 24 类加载机制
 * ClassLoader就是加载其他类的类， 负责将字节码文件加载到内存，创建Class对象。
 *  更好的理解反射方法： Class.forName
 *
 * 通过创建自定义的ClassLoader，可以实现一些强大灵活的功能，比如：
 *  1) 热部署。 （JSP、OSGI,Open Service Gateway Initiative。
 *      在不重启Java程序的情况下，动态替换类的实现，比如JavaWeb开发中的JSP技术就利用自定义的ClassLoader
 *      实现修改JSP代码即生效， OSGI框架使用自定义ClassLoader实现动态更新）
 *  2）应用的模块化和相互隔离。 （不同的ClassLoader可以加载相同的类但互相隔离、互不影响。
 *      Web服务器如Tomcat利用这一点在一个程序中管理多个Web应用程序，每个Web应用使用自己ClassLoader，
 *          这些Web互不干扰。
 *      OSGI、Java9利用这一点实现了一个动态模块化架构，每个模块自己的ClassLoader，
 *      不同模块可以互不干扰。）
 *  3）从不同地方灵活加载。（系统默认的ClassLoader一般从本地的.class文件或jar文件中加载字节码文件，
 *      通过自定义的ClassLoader，我们可以从共享的Web服务器、数据库、缓存服务器等其他地方加载字节码文件。）
 */
package website.fanxian.dynamic_functional.ClassLoader;
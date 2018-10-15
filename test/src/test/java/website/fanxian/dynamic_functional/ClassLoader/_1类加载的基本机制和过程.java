package website.fanxian.dynamic_functional.ClassLoader;

/**
 * classpath可以有多个，
 *  对于直接的class文件，路径是class文件的根目录，
 *  对于jar包，路径是jar包的完整名称（包括路径和jar包名）
 *
 *  类加载器不是只有一个，一般程序运行时，都会有三个：
 *  1）启动类加载器（Bootstrap ClassLoader）：
 *      启动类加载器是Java虚拟机实现的一部分，不是Java语言实现的，一般是C++实现的，
 *      它负责加载Java的基础类，主要是<JAVA_HOEM>/lib/rt.jar，我们日常用的Java类库比如String、ArrayList等都位于该包内。
 *
 *  2）扩展类加载器（Extension ClassLoader）：
 *      扩展类加载器的实现类是sun.misc.Launcher$ExtClassLoader，它负责加载Java的一些扩展类，
 *      一般是<JAVA_HOME>/lib/ext目录中的jar包。
 *
 *  3）应用程序类加载器（Application ClassLoader）：
 *      应用程序类加载器的实现类是sun.misc.Launcher$AppClassLoader，它负责加载应用程序的类，包括自己写的和引入的第三方法类库，
 *      即所有在类路径中指定的类。
 */
public class _1类加载的基本机制和过程 {/*
    这三个类加载器有一定的关系，可以认为是父子关系。
    Bootstrap ClassLoader
    -->Extension ClassLoader
    -->Application ClassLoader
    注意不是父子继承关系，而是父子委派关系，子ClassLoader有一个变量parent指向父ClassLoader，
    在子ClassLoader加载类时，一般会首先通过父ClassLoader加载，
    具体来说，在加载一个类时，基本过程是：
        1）判断是否已经加载过了，加载过了，直接返回Class对象， 一个类只会被一个ClassLoader加载一次。
        2）如果没有被加载，先让父ClassLoader去加载，如果加载成功，返回得到的Class对象。
        3）在父ClassLoader没有加载成功的前提下，自己尝试加载类。


    这个过程一般被称为“双亲委派”模型，即优先让父ClassLoader去加载。这样，可以避免Java类库被覆盖的问题。


    需要了解的是，“双亲委派”虽然是一般模型，但也有一些例外，比如：
        1）自定义的加载顺序。
            以java开头的类也不能被自定义类加载器加载，这是有Java的安全机制保证的，以避免混乱
        2）网状加载顺序。
            在OSGI框架和Java9模块化系统中，类加载器之间的关系是一个网，每个模块有一个类加载器，不同模块之间可能
            有依赖关系，在一个模块加载一个类时，可能是从自己模块加载，也可能是委派给其他模块的类加载器加载。
        3）父加载器委派给子加载器加载。
            典型的例子有JNDI服务（Java Naming and Directory Interface），
            它是Java企业级应用中的一项服务。

*/}

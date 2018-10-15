/*
    24.3 类加载的应用： 可配置的策略
可以通过ClassLoader的loadClass或Class.forName自己加载类，但什么情况需要自己加载类呢？
    很多应用使用面向接口的编程，接口具体的实现类可能有很多，使用于不同的场合，具体使用哪个实现类在
    配置文件中配置，通过更改配置，不用改变代码，就可以改变程序的行为，在设计模式中，这是一种【策略模式】。
*/
package website.fanxian.dynamic_functional.ClassLoader._3configableStrategy;


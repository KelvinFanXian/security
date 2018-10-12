package website.fanxian.dynamic_functional.proxy;

/**
 * 23.1 静态代理
 */
public class Static_Proxy {
    /**
     * 代理背后一般至少有一个实际对象，代理的外部功能和实际对象一般是一样的，用户和代理打交道，不直接接触实际对象。
     *
     * 代理存在的价值:
     *    1) 节省创建开销： 按需延迟加载，创建代理时并不真正创建实际对象，而只是保存实际对象的地址，需要时再加载或创建。
     *    2) 执行权限检查： 代理检查权限后，再调用实际对象。
     *    3) 屏蔽网络差异和复杂性： 代理在本地，而实际对象在其他服务器上，调用本地代理时，本地代理再请求其他服务器。
     */


    interface IService {
        void sayHello();
    }
    static class RealService implements IService {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }
    static class TraceProxy implements IService {
        private IService realService;

        public TraceProxy(IService realService) {
            this.realService = realService;
        }
        @Override
        public void sayHello() {
            System.out.println("entering sayHello");
            this.realService.sayHello();
            System.out.println("leaving sayHello");
        }
    }

    public static void main(String[] args) {
        IService realService = new RealService();
        IService proxyService = new TraceProxy(realService);
        proxyService.sayHello();
    }

    /**
     *  代理和实际对象一般有相同的接口，IService。
     *  代理内部有一个IService成员，指向实际对象，在构造方法中被初始化，
     *    对方法sayHello的调用，它转发给了实际对象，并在调用前后输出了一些跟踪调试信息。
     */

    /**
     *  【适配器】和【装饰器】，他们与【代理模式】有点类似：
     *    1.背后都有一个别的实际对象，
     *    2.都是通过组合的方式指向该对象
     *
     *   不同之处：
     *   【适配器】 提供了一个不一样的新接口。
     *   【装饰器】 对原接口起到了“装饰”作用。 可能是增加新接口、修改了原有的行为等。
     *   【代理模式】 代理一般不改变接口。
     */
}

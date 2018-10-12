package website.fanxian.dynamic_functional.annotation.DIContainer;

/**
 * @author Kelvin范显
 * @createDate 2018年10月12日
 */
public class ServiceA {
    @SimpleInject
    ServiceB b;
    public void callB(){
        b.action();
    }
}

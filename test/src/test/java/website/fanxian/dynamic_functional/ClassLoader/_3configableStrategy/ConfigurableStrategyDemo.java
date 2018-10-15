package website.fanxian.dynamic_functional.ClassLoader._3configableStrategy;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author Kelvin范显
 * @createDate 2018年10月15日
 */
public class ConfigurableStrategyDemo {
    public static IService createService() {
        try {
            Properties prop = new Properties();
            String fileName = "F:\\github\\SpringSecurity\\security\\test\\src\\test\\java\\website\\fanxian\\dynamic_functional\\ClassLoader\\_3configableStrategy\\config.properties";
            prop.load(new FileInputStream(fileName));
            String className = prop.getProperty("service");
            Class<?> cls = Class.forName(className);
            return (IService) cls.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        IService service = createService();
        service.action();
    }
}

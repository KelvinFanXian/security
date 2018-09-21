package website.fanxian.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Kelvin范显
 * @createDate 2018年09月20日
 */
public class FastFailTest {

    private static List<String> list = new ArrayList<>();
//    private static List<String> list = new CopyOnWriteArrayList<>();
    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }

    public static void printAll() {
        System.out.println();
        String value;
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            value = iterator.next();
            System.out.println(value + ", ");
        }
    }

    static class Thread1 extends Thread {
        @Override
        public void run() {
            int i =0;
            while (i < 6) {
                list.add(String.valueOf(i));
                printAll();
                i++;
            }
        }
    }
    static class Thread2 extends Thread {
        @Override
        public void run() {
            int i =10;
            while (i < 16) {
                list.add(String.valueOf(i));
                printAll();
                i++;
            }
        }
    }
}

package website.fanxian.concurrency._2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kelvin范显
 * @createDate 2018年10月24日
 */
public class Account {
    private Lock lock = new ReentrantLock();
    private volatile double money;
    public Account(double initialMoney) {
        this.money = initialMoney;
    }
    public void add(double money) {
        lock.lock();
        try {
            this.money += money;
        } finally {
            lock.unlock();
        }
    }
    public void reduce(double money) {
        lock.lock();
        try {
            this.money -= money;
        } finally {
            lock.unlock();
        }
    }
    public double getMoney() {
        return money;
    }
    void lock() {
        lock.lock();
    }
    void unlock() {
        lock.unlock();
    }
    boolean tryLock() {
        return lock.tryLock();
    }
}

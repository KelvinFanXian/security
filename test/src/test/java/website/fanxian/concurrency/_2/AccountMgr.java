package website.fanxian.concurrency._2;

import java.util.Random;

/**
 * @author Kelvin范显
 * @createDate 2018年10月24日
 */
public class AccountMgr {
    public static class NoEnoughMoneyException extends Exception {}

    /**
     * 转账的错误写法
     * @param from
     * @param to
     * @param money
     * @throws NoEnoughMoneyException
     */
    public static void transfer(Account from, Account to, double money)
            throws NoEnoughMoneyException {
        from.lock();
        try {
            to.lock();
            try {
                if(from.getMoney() >= money) {
                    from.reduce(money);
                    to.add(money);
                } else {
                    throw new NoEnoughMoneyException();
                }
            } finally {
                to.unlock();
            }
        } finally {
            from.unlock();
        }
    }


    /**
     * 模拟账户转账的死锁过程
     */
    public static void simulateDeadLock() {
        final int accountNum = 10;
        final Account[] accounts = new Account[accountNum];
        final Random rnd = new Random();
        for(int i = 0; i < accountNum; i++) {
            accounts[i] = new Account(rnd.nextInt(10000));
        }
        int threadNum = 100;
        Thread[] threads = new Thread[threadNum];
        for(int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(() -> {
                int loopNum = 100;
                for(int k = 0; k < loopNum; k++) {
                    int i1 = rnd.nextInt(accountNum);
                    int j = rnd.nextInt(accountNum);
                    int money = rnd.nextInt(10);
                    if(i1 != j) {
                        try {
//                            transfer(accounts[i1], accounts[j], money);
                            transfer2(accounts[i1], accounts[j], money);
                        } catch (NoEnoughMoneyException e) {
                        }
                    }
                }
            });
            threads[i].start();
        }
    }

    /**
     *  使用tryLock尝试转账
     * @param from
     * @param to
     * @param money
     * @return
     * @throws NoEnoughMoneyException
     */
    public static boolean tryTransfer(Account from, Account to, double money)
            throws NoEnoughMoneyException {
        if (from.tryLock()) { // tryLock
            try {
                if (to.tryLock()) {
                    try {
                        if (from.getMoney() >= money) {
                            from.reduce(money);
                            to.add(money);
                        } else {
                            throw new NoEnoughMoneyException();
                        }
                        return true; // 如果两个锁都能获得，且转账成功，true：false
                    } finally {
                        to.unlock();
                    }
                }
            } finally {
                from.unlock();
            }
        }
        return false;
    }

    /**
     * 循环调用上面的tryTransfer()方法避免死锁
     * @param from
     * @param to
     * @param money
     * @throws NoEnoughMoneyException
     */
    public static void transfer2(Account from, Account to, double money)
            throws NoEnoughMoneyException {
        boolean success = false;
        do {
            success = tryTransfer(from, to, money);
            if (!success) {
                Thread.yield();
            }
        } while (!success);

    }
    public static void main(String[] args) {
        simulateDeadLock();
    }
}

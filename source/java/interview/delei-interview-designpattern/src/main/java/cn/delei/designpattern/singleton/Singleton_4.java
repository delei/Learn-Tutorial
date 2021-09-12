package cn.delei.designpattern.singleton;

/**
 * 双重锁机制
 * <p>volatile结合synchronized关键字,线程安全</p>
 *
 * @author deleiguo
 */
public class Singleton_4 {
    
    private volatile static Singleton_4 instance;

    private Singleton_4() {
    }

    public static Singleton_4 getInstance() {
        if (instance == null) {
            // 为类(对象)加锁
            synchronized (Singleton_4.class) {
                if (instance == null) {
                    instance = new Singleton_4();
                }
            }
        }
        return instance;
    }

}

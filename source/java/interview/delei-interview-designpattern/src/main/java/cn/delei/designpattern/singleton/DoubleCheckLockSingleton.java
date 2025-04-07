package cn.delei.designpattern.singleton;

/**
 * 双重锁机制
 * <p>volatile结合synchronized关键字,线程安全</p>
 *
 * @author deleiguo
 */
public class DoubleCheckLockSingleton {
    
    private volatile static DoubleCheckLockSingleton instance;

    private DoubleCheckLockSingleton() {}

    public static DoubleCheckLockSingleton getInstance() {
        // 检查是否要阻塞
        if (instance == null) {
            // 为类(对象)加锁
            synchronized (DoubleCheckLockSingleton.class) {
                // 检查是否要重新创建实例
                if (instance == null) {
                    instance = new DoubleCheckLockSingleton();
                }
            }
        }
        return instance;
    }
}

package cn.delei.designpattern.singleton;

/**
 * 基本实现方式
 * <p>不支持多线程 非线程安全 lazy loading</p>
 *
 * @author deleiguo
 */
public class LazyNormalUnSafeSingleton {
    // 静态块，公共内存区域
    private static LazyNormalUnSafeSingleton instance;

    private LazyNormalUnSafeSingleton() {}

    public static LazyNormalUnSafeSingleton getInstance() {
        if (instance == null) {
            instance = new LazyNormalUnSafeSingleton();
        }
        return instance;
    }
}

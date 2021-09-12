package cn.delei.designpattern.singleton;

/**
 * 基本实现方式
 * <p>不支持多线程 非线程安全 lazy loading</p>
 *
 * @author deleiguo
 */
public class Singleton_1 {
    private static Singleton_1 instance;

    private Singleton_1() {
    }

    public static Singleton_1 getInstance() {
        if (instance == null) {
            instance = new Singleton_1();
        }
        return instance;
    }
}

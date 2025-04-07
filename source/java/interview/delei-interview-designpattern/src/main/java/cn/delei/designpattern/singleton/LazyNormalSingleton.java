package cn.delei.designpattern.singleton;

/**
 * 懒汉式
 * <p>lazy loading 支持多线程，但是同步效率低</p>
 *
 * @author deleiguo
 */
public class LazyNormalSingleton {
    private static LazyNormalSingleton instance;

    private LazyNormalSingleton() {

    }

    public static synchronized LazyNormalSingleton getInstance() {
        if (instance == null) {
            instance = new LazyNormalSingleton();
        }
        return instance;
    }
}

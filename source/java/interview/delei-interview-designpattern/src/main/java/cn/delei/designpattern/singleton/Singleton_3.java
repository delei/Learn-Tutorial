package cn.delei.designpattern.singleton;

/**
 * 饿汉式
 * <p>java classloader类加载时就初始化，线程安全,no lazy loading</p>
 *
 * @author deleiguo
 */
public class Singleton_3 {
    private static Singleton_3 instance = new Singleton_3();

    public Singleton_3() {
    }

    public static Singleton_3 getInstance() {
        return instance;
    }
}

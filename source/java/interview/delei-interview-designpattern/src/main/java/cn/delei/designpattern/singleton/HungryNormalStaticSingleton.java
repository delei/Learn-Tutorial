package cn.delei.designpattern.singleton;

/**
 * 饿汉式
 * <p>java classloader类加载时就初始化，线程安全,no lazy loading</p>
 *
 * @author deleiguo
 */
public class HungryNormalStaticSingleton {
    private static final HungryNormalStaticSingleton instance;

    static {
        instance = new HungryNormalStaticSingleton();
    }

    public HungryNormalStaticSingleton() {}

    public static HungryNormalStaticSingleton getInstance() {
        return instance;
    }
}

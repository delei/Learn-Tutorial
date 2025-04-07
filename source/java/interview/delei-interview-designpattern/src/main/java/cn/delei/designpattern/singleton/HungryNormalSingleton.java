package cn.delei.designpattern.singleton;

/**
 * 饿汉式
 * <p>java classloader类加载时就初始化，线程安全,no lazy loading</p>
 *
 * @author deleiguo
 */
public class HungryNormalSingleton {
    private static HungryNormalSingleton instance = new HungryNormalSingleton();

    public HungryNormalSingleton() {
    }

    public static HungryNormalSingleton getInstance() {
        return instance;
    }
}

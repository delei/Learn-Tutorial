package cn.delei.designpattern.singleton;

/**
 * 利用classloader机制保证初始化instance时只有一个线程
 * lazy loading,线程安全
 *
 * @author deleiguo
 */
public class StaticInnerClassSingleton {

    private StaticInnerClassSingleton() {
        if(SingletonHolder.INSTANCE != null){
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    public static StaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 静态内部类，默认不会加载
     */
    private static class SingletonHolder {
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }
}

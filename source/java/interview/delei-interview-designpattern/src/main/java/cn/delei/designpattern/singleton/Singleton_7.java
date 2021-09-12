package cn.delei.designpattern.singleton;

/**
 * 使用枚举来实现单实例控制
 * 无偿地提供了序列化机制，并由JVM从根本上提供保障，绝对防止多次实例化
 *
 * @author deleiguo
 */
public class Singleton_7 {

    static enum SingletonEnum {
        INSTANCE;

        private Singleton_7 singleton;

        private SingletonEnum() {
            singleton = new Singleton_7();
        }

        public Singleton_7 getInstnce() {
            return singleton;
        }
    }

    /**
     * 对外暴露一个获取对象的静态方法
     *
     * @return Singleton_6 实例
     */
    public static Singleton_7 getInstance() {
        return SingletonEnum.INSTANCE.getInstnce();
    }
}

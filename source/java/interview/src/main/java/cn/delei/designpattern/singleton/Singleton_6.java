package cn.delei.designpattern.singleton;

/*
 * 使用枚举来实现单实例控制
 * 无偿地提供了序列化机制，并由JVM从根本上提供保障，绝对防止多次实例化
 */
public class Singleton_6 {

    static enum SingletonEnum {
        INSTANCE;

        private Singleton_6 singleton;

        private SingletonEnum() {
            singleton = new Singleton_6();
        }

        public Singleton_6 getInstnce() {
            return singleton;
        }
    }

    //对外暴露一个获取User对象的静态方法
    public static Singleton_6 getInstance() {
        return SingletonEnum.INSTANCE.getInstnce();
    }
}

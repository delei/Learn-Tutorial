package cn.delei.designpattern.singleton;

/**
 * 使用枚举来实现单实例控制
 * 无偿地提供了序列化机制，并由JVM从根本上提供保障，绝对防止多次实例化
 *
 * @author deleiguo
 */
public enum EnumSingletonWithData {
    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSingletonWithData getInstance(){return INSTANCE;}
}

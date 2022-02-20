package cn.delei.java.feature;

/**
 * Interface Default
 *
 * @author deleiguo
 * @since 1.8
 */
public interface IDefaultInterface {
    
    void calu(String a);

    default void call(String a) {
        System.out.println("default call: " + a);
    }
}

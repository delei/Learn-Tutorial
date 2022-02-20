package cn.delei.java.feature;

/**
 * Interface Default
 *
 * @author deleiguo
 * @since 1.8
 */
public class DefaultInterfaceDemo {
    public static void main(String[] args) {
        IDefaultInterface defaultInterface = new DefaultInterfaceImpl();
        defaultInterface.calu("aaaa");
        defaultInterface.call("bbbb");
    }

    static class DefaultInterfaceImpl implements IDefaultInterface {
        @Override
        public void calu(String a) {
            System.out.println("DefaultInterfaceImpl#calu:" + a);
        }
    }
}

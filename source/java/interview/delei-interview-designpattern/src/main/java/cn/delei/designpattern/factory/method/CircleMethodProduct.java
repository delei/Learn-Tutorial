package cn.delei.designpattern.factory.method;

/**
 * 圆形实现类
 *
 * @author deleiguo
 */
public class CircleMethodProduct implements ShapeMethod {
    @Override
    public void draw() {
        System.out.println(CircleMethodProduct.class.getName() + ":draw() ");
    }
}

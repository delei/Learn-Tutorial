package cn.delei.designpattern.factory.method;

/**
 * 圆形实现类
 *
 * @author deleiguo
 */
public class RectangleMethodProduct implements ShapeMethod {
    @Override
    public void draw() {
        System.out.println(RectangleMethodProduct.class.getName() + ":draw() ");
    }
}

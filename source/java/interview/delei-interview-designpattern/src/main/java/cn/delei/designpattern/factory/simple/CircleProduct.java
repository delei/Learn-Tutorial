package cn.delei.designpattern.factory.simple;

/**
 * 圆形实现类
 *
 * @author deleiguo
 */
public class CircleProduct implements ShapeSimple {
    @Override
    public void draw() {
        System.out.println(CircleProduct.class.getName() + ":draw() ");
    }
}

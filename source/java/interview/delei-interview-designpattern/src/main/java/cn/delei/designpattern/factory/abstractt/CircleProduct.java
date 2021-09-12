package cn.delei.designpattern.factory.abstractt;

/**
 * 形状实现类：圆形
 *
 * @author deleiguo
 */
public class CircleProduct implements ShapeAbstract {
    @Override
    public void draw() {
        System.out.println(CircleProduct.class.getName() + ":draw() ");
    }
}

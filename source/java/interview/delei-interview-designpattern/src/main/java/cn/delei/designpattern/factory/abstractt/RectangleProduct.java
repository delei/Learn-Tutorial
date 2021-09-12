package cn.delei.designpattern.factory.abstractt;

/**
 * 形状实现类：长方形
 *
 * @author deleiguo
 */
public class RectangleProduct implements ShapeInterface {
    @Override
    public void draw() {
        System.out.println(RectangleProduct.class.getName() + ":draw() ");
    }
}

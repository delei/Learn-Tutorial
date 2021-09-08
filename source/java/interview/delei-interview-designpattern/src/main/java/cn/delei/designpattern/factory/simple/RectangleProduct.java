package cn.delei.designpattern.factory.simple;

/**
 * 长方形实现类
 *
 * @author deleiguo
 */
public class RectangleProduct implements ShapeSimple {
    @Override
    public void draw() {
        System.out.println(RectangleProduct.class.getName() + ":draw()");
    }

}

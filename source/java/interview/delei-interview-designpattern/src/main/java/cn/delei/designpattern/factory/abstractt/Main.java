package cn.delei.designpattern.factory.abstractt;

import cn.delei.util.PrintUtil;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {
    public static void main(String[] args) {

        PrintUtil.printDivider("形状工厂");
        ShapeAbstractFactory productFactory = new ShapeFactory();
        ShapeInterface shape = productFactory.getShape(ShapeAbstractFactory.SHAPE_CIRCLE);
        shape.draw();
        shape = productFactory.getShape(ShapeAbstractFactory.SHAPE_RECTANGLE);
        shape.draw();

        PrintUtil.printDivider("颜色工厂");
        productFactory = new ColorFactory();
        ColorInterface color = productFactory.getColor(ShapeAbstractFactory.COLOR_GREEN);
        color.draw();
        color = productFactory.getColor(ShapeAbstractFactory.COLOR_RED);
        color.draw();
    }
}

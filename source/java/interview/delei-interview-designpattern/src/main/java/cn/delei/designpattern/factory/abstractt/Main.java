package cn.delei.designpattern.factory.abstractt;

import cn.delei.util.PrintUtil;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {
    public static void main(String[] args) {

        ShapeAbstractFactory shapeFactory = new ShapeFactory();
        ShapeAbstractFactory colorFactory = new ColorFactory();

        PrintUtil.printDivider("形状工厂");
        ShapeAbstract shape = shapeFactory.getShape(ShapeAbstractFactory.SHAPE_CIRCLE);
        shape.draw();
        shape = shapeFactory.getShape(ShapeAbstractFactory.SHAPE_RECTANGLE);
        shape.draw();

        PrintUtil.printDivider("颜色工厂");
        ColorAbstract color = colorFactory.getColor(ShapeAbstractFactory.COLOR_GREEN);
        color.draw();
        color = colorFactory.getColor(ShapeAbstractFactory.COLOR_RED);
        color.draw();
    }
}
package cn.delei.designpattern.factory.simple;

import cn.delei.util.PrintUtil;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {
    public static void main(String[] args) {
        PrintUtil.printDivider();
        ShapeSimple shapeSimple = ShapeSimpleFactory.createShape(ShapeSimpleFactory.TYPE_CIRCLE);
        shapeSimple.draw();

        PrintUtil.printDivider();
        shapeSimple = ShapeSimpleFactory.createShape(ShapeSimpleFactory.TYPE_RECTANGLE);
        shapeSimple.draw();

        PrintUtil.printDivider();
        shapeSimple = ShapeSimpleFactory.createShape("else");
        shapeSimple.draw();
    }
}

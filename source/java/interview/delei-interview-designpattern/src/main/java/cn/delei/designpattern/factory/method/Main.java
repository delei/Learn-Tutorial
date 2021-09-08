package cn.delei.designpattern.factory.method;

import cn.delei.util.PrintUtil;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {
    public static void main(String[] args) {

        PrintUtil.printDivider();
        ShapeMethod shapeMethod = ShapeMethodFactory.getFactory().createShape(ShapeMethodFactory.TYPE_CIRCLE);
        shapeMethod.draw();

        PrintUtil.printDivider();
        shapeMethod = ShapeMethodFactory.getFactory().createShape(ShapeMethodFactory.TYPE_RECTANGLE);
        shapeMethod.draw();
    }
}

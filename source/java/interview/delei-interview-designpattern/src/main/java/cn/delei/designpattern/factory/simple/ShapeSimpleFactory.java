package cn.delei.designpattern.factory.simple;

import cn.hutool.core.lang.Assert;

/**
 * 简单工厂
 *
 * @author deleiguo
 */
public class ShapeSimpleFactory {

    public static final String TYPE_CIRCLE = "CIRCLE";
    public static final String TYPE_RECTANGLE = "RECTANGLE";

    private ShapeSimpleFactory() {
    }

    public static ShapeSimple createShape(String shapeType) {
        Assert.notEmpty(shapeType, "shapeType must not be null or empty");
        switch (shapeType) {
            case TYPE_CIRCLE:
                return new CircleProduct();
            case TYPE_RECTANGLE:
                return new RectangleProduct();
            default:
                Assert.state(false, "can not find " + shapeType + " product instance");
                return null;
        }
    }
}

package cn.delei.designpattern.factory.method;

import cn.hutool.core.lang.Assert;

/**
 * 具体工厂实现
 *
 * @author deleiguo
 */
public class ShapeMethodFactoryImpl implements ShapeMethodFactory {
    @Override
    public ShapeMethod createShape(String shapeType) {
        Assert.notEmpty(shapeType, "shapeType must not be null or empty");
        switch (shapeType) {
            case ShapeMethodFactory.TYPE_CIRCLE:
                return new CircleMethodProduct();
            case ShapeMethodFactory.TYPE_RECTANGLE:
                return new RectangleMethodProduct();
            default:
                Assert.state(false, "can not find " + shapeType + " product instance");
                return null;
        }
    }
}

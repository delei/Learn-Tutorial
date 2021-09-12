package cn.delei.designpattern.factory.abstractt;

import cn.hutool.core.lang.Assert;

/**
 * 形状工厂
 *
 * @author deleiguo
 */
public class ShapeFactory extends ShapeAbstractFactory {

    @Override
    public ShapeInterface getShape(String shapeType) {
        Assert.notEmpty(shapeType, "shapeType must not be null or empty");
        switch (shapeType) {
            case ShapeAbstractFactory.SHAPE_CIRCLE:
                return new CircleProduct();
            case ShapeAbstractFactory.SHAPE_RECTANGLE:
                return new RectangleProduct();
            default:
                Assert.state(false, "can not find " + shapeType + " product instance");
                return null;
        }
    }

    @Override
    public ColorInterface getColor(String colorType) {
        return null;
    }
}

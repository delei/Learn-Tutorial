package cn.delei.designpattern.factory.abstractt;

import cn.hutool.core.lang.Assert;

/**
 * 颜色工厂
 *
 * @author deleiguo
 */
public class ColorFactory extends ShapeAbstractFactory {

    @Override
    public ShapeInterface getShape(String shapeType) {
        return null;
    }

    @Override
    public ColorInterface getColor(String colorType) {
        Assert.notEmpty(colorType, "shapeType must not be null or empty");
        switch (colorType) {
            case ShapeAbstractFactory.COLOR_GREEN:
                return new GreenProduct();
            case ShapeAbstractFactory.COLOR_RED:
                return new RedProduct();
            default:
                Assert.state(false, "can not find " + colorType + " product instance");
                return null;
        }
    }
}

package cn.delei.designpattern.factory.method;

/**
 * 具体工厂实现
 *
 * @author deleiguo
 */
public class ShapeMethodFactoryImpl implements ShapeMethodFactory {
    @Override
    public ShapeMethod createShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        switch (shapeType) {
            case ShapeMethodFactory.TYPE_CIRCLE:
                return new CircleMethodProduct();
            case ShapeMethodFactory.TYPE_RECTANGLE:
                return new RectangleMethodProduct();
            default:
                return null;
        }
    }
}

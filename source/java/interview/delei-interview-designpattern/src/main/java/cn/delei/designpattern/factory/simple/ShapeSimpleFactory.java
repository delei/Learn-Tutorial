package cn.delei.designpattern.factory.simple;

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
        if (shapeType == null) {
            return null;
        }
        switch (shapeType) {
            case TYPE_CIRCLE:
                return new CircleProduct();
            case TYPE_RECTANGLE:
                return new RectangleProduct();
            default:
                return null;
        }
    }
}

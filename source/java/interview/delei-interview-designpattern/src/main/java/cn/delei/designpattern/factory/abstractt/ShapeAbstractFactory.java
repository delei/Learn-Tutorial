package cn.delei.designpattern.factory.abstractt;

/**
 * 工厂抽象
 *
 * @author deleiguo
 */
public abstract class ShapeAbstractFactory {
    public static final String SHAPE_CIRCLE = "CIRCLE";
    public static final String SHAPE_RECTANGLE = "RECTANGLE";

    public static final String COLOR_RED = "RED";
    public static final String COLOR_GREEN = "GREEN";

    abstract ShapeInterface getShape(String shapeType);

    abstract ColorInterface getColor(String colorType);
}

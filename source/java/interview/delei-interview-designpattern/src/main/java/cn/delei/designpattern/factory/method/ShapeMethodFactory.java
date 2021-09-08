package cn.delei.designpattern.factory.method;

/**
 * 工厂抽象
 *
 * @author deleiguo
 */
public interface ShapeMethodFactory {

    String TYPE_CIRCLE = "CIRCLE";
    String TYPE_RECTANGLE = "RECTANGLE";

    ShapeMethod createShape(String shapeType);

    static ShapeMethodFactory getFactory() {
        return new ShapeMethodFactoryImpl();
    }
}

package cn.delei.designpattern.decorator;

/**
 * Shape实现类，抽象装饰类
 *
 * @author deleiguo
 */
public abstract class ShapeDecorator implements Shape {

    /**
     * 被装饰的实体
     */
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw() {
        decoratedShape.draw();
    }

}

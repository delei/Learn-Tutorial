package cn.delei.designpattern.decorator;

/**
 * 具体装饰类，抽象装饰类的子类
 *
 * @author deleiguo
 */
public class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    /**
     * 通过继承覆盖父类的方法实现
     */
    @Override
    public void draw() {
        // 原始方法
        decoratedShape.draw();
        // 增加新功能
        setReadBorder(decoratedShape);
    }

    /**
     * 添加"红色边框"功能
     *
     * @param decoratedShape shape实体
     */
    private void setReadBorder(Shape decoratedShape) {
        System.out.println("添加红色边框");
    }

}

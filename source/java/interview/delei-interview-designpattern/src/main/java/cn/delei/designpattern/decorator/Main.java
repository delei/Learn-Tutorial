package cn.delei.designpattern.decorator;

import cn.delei.util.PrintUtil;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {

    public static void main(String[] args) {
        // 原始圆形
        Shape circle = new CircleShape();
        // 红色圆形
        Shape redCircle = new RedShapeDecorator(new CircleShape());
        // 红色长方形
        Shape redRectangle = new RedShapeDecorator(new RectangleShape());

        PrintUtil.printDivider();
        circle.draw();
        PrintUtil.printDivider();
        redCircle.draw();
        PrintUtil.printDivider();
        redRectangle.draw();
    }

}

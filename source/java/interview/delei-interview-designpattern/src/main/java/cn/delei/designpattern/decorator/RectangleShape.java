package cn.delei.designpattern.decorator;

/**
 * 长方形，接口实体类
 *
 * @author deleiguo
 */
public class RectangleShape implements Shape {

    @Override
    public void draw() {
        System.out.println(RectangleShape.class.getName() + ": draw(),长方形");
    }

}

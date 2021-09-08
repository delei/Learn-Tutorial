package cn.delei.designpattern.decorator;

/**
 * 圆形，接口实体类
 *
 * @author deleiguo
 */
public class CircleShape implements Shape {

    @Override
    public void draw() {
        System.out.println(CircleShape.class.getName() + ": draw(),圆形");
    }

}

package cn.delei.designpattern.factory.abstractt;

/**
 * 颜色实现类：绿色
 *
 * @author deleiguo
 */
public class GreenProduct implements ColorInterface {
    @Override
    public void draw() {
        System.out.println(GreenProduct.class.getName() + ":draw() ");
    }
}

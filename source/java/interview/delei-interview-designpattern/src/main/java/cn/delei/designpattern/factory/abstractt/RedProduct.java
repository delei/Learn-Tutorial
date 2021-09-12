package cn.delei.designpattern.factory.abstractt;

/**
 * 颜色实现类：红色
 *
 * @author deleiguo
 */
public class RedProduct implements ColorAbstract {
    @Override
    public void draw() {
        System.out.println(RedProduct.class.getName() + ":draw() ");
    }
}

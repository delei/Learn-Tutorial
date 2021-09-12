package cn.delei.designpattern.builder;

/**
 * 鸡肉汉堡
 *
 * @author deleiguo
 */
public class ChickenBurger implements Item {
    @Override
    public double price() {
        return 12.5D;
    }

    @Override
    public String name() {
        return "Chicken Burger";
    }

}

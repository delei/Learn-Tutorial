package cn.delei.designpattern.builder;

/**
 * 蔬菜汉堡
 *
 * @author deleiguo
 */
public class VegBurger implements Item {
    @Override
    public double price() {
        return 10.0D;
    }

    @Override
    public String name() {
        return "Veg Burger";
    }

}

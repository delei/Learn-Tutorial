package cn.delei.designpattern.builder;

/**
 * 百事可乐
 *
 * @author deleiguo
 */
public class PepsiCola implements Item {
    @Override
    public double price() {
        return 3.5D;
    }

    @Override
    public String name() {
        return "Pepsi Cola";
    }
}

package cn.delei.designpattern.builder;

/**
 * 可口可乐
 *
 * @author deleiguo
 */
public class CocaCoke implements Item {
    @Override
    public double price() {
        return 3.0D;
    }

    @Override
    public String name() {
        return "Coca Coke";
    }
}

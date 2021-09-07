package cn.delei.designpattern.strategy;

/**
 * 具体策略类：八折
 *
 * @author deleiguo
 */
public class PreferentialStrategy implements DiscountStrategy {

    @Override
    public double calculate(double price) {
        // 八折
        System.out.println(PreferentialStrategy.class.getName() + ": calculate");
        return price * 0.8D;
    }
}

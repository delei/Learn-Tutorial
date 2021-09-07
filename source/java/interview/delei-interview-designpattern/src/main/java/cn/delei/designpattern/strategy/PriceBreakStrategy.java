package cn.delei.designpattern.strategy;

/**
 * 具体策略类：满减
 *
 * @author deleiguo
 */
public class PriceBreakStrategy implements DiscountStrategy {
    @Override
    public double calculate(double price) {
        // 满100减10元
        System.out.println(PriceBreakStrategy.class.getName() + ": calculate");
        return price > 100 ? price - 10 : price;
    }
}

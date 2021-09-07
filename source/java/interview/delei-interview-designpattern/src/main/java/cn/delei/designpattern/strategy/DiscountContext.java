package cn.delei.designpattern.strategy;

/**
 * Context(环境类)
 *
 * @author deleiguo
 */
public class DiscountContext {

    private DiscountStrategy discountStrategy;

    public DiscountContext(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    /**
     * 折扣方法
     *
     * @param price 原价
     * @return double 结果值
     */
    public double discount(double price) {
        // 调用策略方法
        return discountStrategy.calculate(price);
    }
}

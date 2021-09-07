package cn.delei.designpattern.strategy;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {
    public static void main(String[] args) {
        // 原价
        double price = 200D;
        // 策略01：八折
        DiscountContext discountContext = new DiscountContext(new PreferentialStrategy());
        System.out.println("Strategy 八折: " + discountContext.discount(price));
        // 策略02：满减
        discountContext = new DiscountContext(new PriceBreakStrategy());
        System.out.println("Strategy 满100减10块: " + discountContext.discount(price));
    }
}


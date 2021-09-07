package cn.delei.designpattern.strategy;

/**
 * Strategy(抽象策略类)
 *
 * @author deleiguo
 */
public interface DiscountStrategy {
    /**
     * 计算方法
     *
     * @param price 原价
     * @return double 计算结果
     */
    double calculate(double price);
}

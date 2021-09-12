package cn.delei.designpattern.builder;

/**
 * 菜品接口
 */
public interface Item {
    /**
     * 菜品名称
     *
     * @return String 名称
     */
    String name();

    /**
     * 菜品价格
     *
     * @return float 价格
     */
    double price();
}

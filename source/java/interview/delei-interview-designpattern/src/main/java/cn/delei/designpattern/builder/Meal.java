package cn.delei.designpattern.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 餐食
 *
 * @author deleiguo
 */
public class Meal {
    private List<Item> items = new ArrayList<Item>();

    /**
     * 添加菜单
     *
     * @param item 菜品
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * 获取餐食总价格
     *
     * @return float 总价格
     */
    public double getCost() {
        double cost = 0.0D;
        for (Item item : items) {
            cost += item.price();
        }
        return cost;
    }

    /**
     * 展示餐食中包含的所有菜品
     */
    public void showItems() {
        for (Item item : items) {
            System.out.printf("菜单 %s ,单价 %s\n", item.name(), item.price());
        }
    }
}

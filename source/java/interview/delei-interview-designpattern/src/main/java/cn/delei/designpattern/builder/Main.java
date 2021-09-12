package cn.delei.designpattern.builder;

import cn.delei.util.PrintUtil;

public class Main {

    public static void main(String[] args) {
        PrintUtil.printDivider("蔬菜汉堡套餐");
        MealBuilder mealBuilder = new MealBuilder();
        Meal meal = mealBuilder.prepareVegMeal();
        System.out.println("Veg Meal:");
        meal.showItems();
        System.out.println("Cost:" + meal.getCost());

        PrintUtil.printDivider("鸡肉汉堡套餐");
        meal = mealBuilder.prepareNonVegMeal();
        System.out.println("Non Veg Meal:");
        meal.showItems();
        System.out.println("Cost:" + meal.getCost());

    }

}

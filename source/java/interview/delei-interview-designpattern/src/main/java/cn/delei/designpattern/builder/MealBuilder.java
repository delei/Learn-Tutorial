package cn.delei.designpattern.builder;

/**
 * 构建套餐
 *
 * @author deleiguo
 */
public class MealBuilder {
    
    public Meal prepareVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new CocaCoke());
        return meal;
    }

    public Meal prepareNonVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new PepsiCola());
        return meal;
    }
}

package cn.delei.designpattern.builder;

import cn.delei.designpattern.builder.po.ChickenBurger;
import cn.delei.designpattern.builder.po.Coke;
import cn.delei.designpattern.builder.po.Meal;
import cn.delei.designpattern.builder.po.Pepsi;
import cn.delei.designpattern.builder.po.VegBurger;

public class MealBuilder {
	public Meal prepareVegMeal() {
		Meal meal = new Meal();
		meal.addItem(new VegBurger());
		meal.addItem(new Coke());
		return meal;
	}
	
	public Meal prepareNonVegMeal() {
		Meal meal = new Meal();
		meal.addItem(new ChickenBurger());
		meal.addItem(new Pepsi());
		return meal;
	}
}

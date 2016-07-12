package cn.delei.designpattern.builder;

import cn.delei.designpattern.builder.po.Meal;

public class Main {
	
	public static void main(String[] args) {
		MealBuilder mealBuilder = new MealBuilder();
		Meal meal = mealBuilder.prepareVegMeal();
		System.out.println("Veg Meal:");
		meal.showItems();
		System.out.println("Cost:" + meal.getCost());
		
		meal = mealBuilder.prepareNonVegMeal();
		System.out.println("Non Veg Meal:");
		meal.showItems();
		System.out.println("Cost:" + meal.getCost());
		
	}
	
}

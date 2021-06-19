package cn.delei.designpattern.builder.po;

import cn.delei.designpattern.builder.impl.Burger;

public class VegBurger extends Burger {
	@Override
	public float price() {
		return 25.0f;
	}
	
	public String name() {
		return "Veg Burger";
	}
}

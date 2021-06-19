package cn.delei.designpattern.builder.po;

import cn.delei.designpattern.builder.impl.Burger;

public class ChickenBurger extends Burger {
	@Override
	public float price() {
		return 50.5f;
	}
	
	public String name() {
		return "Chicken Burger";
	}
	
}

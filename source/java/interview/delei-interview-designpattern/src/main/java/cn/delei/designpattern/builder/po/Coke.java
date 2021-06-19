package cn.delei.designpattern.builder.po;

import cn.delei.designpattern.builder.impl.ColdDrink;

public class Coke extends ColdDrink {
	@Override
	public float price() {
		return 30.0f;
	}
	
	public String name() {
		return "Coke";
	}
}

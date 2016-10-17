package cn.delei.designpattern.builder.po;

import cn.delei.designpattern.builder.impl.ColdDrink;

public class Pepsi extends ColdDrink {
	@Override
	public float price() {
		return 35.0f;
	}
	
	public String name() {
		return "Pepsi";
	}
}

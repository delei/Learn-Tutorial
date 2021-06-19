package cn.delei.designpattern.builder.impl;

import cn.delei.designpattern.builder.Item;
import cn.delei.designpattern.builder.Packing;

public abstract class ColdDrink implements Item {
	
	public Packing packing() {
		// TODO Auto-generated method stub
		return new Bottle();
	}
	
	public abstract float price();
	
}

package cn.delei.designpattern.factory.color.impl;

import cn.delei.designpattern.factory.color.Color;

public class Blue implements Color {
	
	@Override
	public void fill() {
		System.out.println("Inside Blue::fill() method.");
		
	}
	
}

package cn.delei.designpattern.factory.color.impl;

import cn.delei.designpattern.factory.color.Color;

public class Green implements Color {
	
	@Override
	public void fill() {
		System.out.println("Inside Green::fill() mehod.");
		
	}
	
}

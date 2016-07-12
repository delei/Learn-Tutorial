package cn.delei.designpattern.factory.color.impl;

import cn.delei.designpattern.factory.color.Color;

public class Red implements Color {
	
	@Override
	public void fill() {
		System.out.println("Inside Red::fille() medhod.");
	}
	
}

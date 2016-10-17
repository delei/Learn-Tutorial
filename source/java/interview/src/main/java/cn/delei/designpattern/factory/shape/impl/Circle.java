package cn.delei.designpattern.factory.shape.impl;

import cn.delei.designpattern.factory.shape.Shape;

public class Circle implements Shape {
	@Override
	public void draw() {
		System.out.println("Inside Circle::draw() method.");
	}
}

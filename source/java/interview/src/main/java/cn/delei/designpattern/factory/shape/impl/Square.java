package cn.delei.designpattern.factory.shape.impl;

import cn.delei.designpattern.factory.shape.Shape;

public class Square implements Shape {
	@Override
	public void draw() {
		System.out.println("Inside Square::draw() method.");
	}
}

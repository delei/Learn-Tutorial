package cn.delei.designpattern.factory;

import cn.delei.designpattern.factory.shape.Shape;

public class Main {
	public static void main(String[] args) {
		AbstractFactory shapeFactory = FactoryProducer.getFactory("SHAPE");
		Shape shape = shapeFactory.getShape("CIRCLE");
		shape.draw();
		shape = shapeFactory.getShape("RECTANGLE");
		shape.draw();
		shape = shapeFactory.getShape("SQUARE");
		shape.draw();
	}
}

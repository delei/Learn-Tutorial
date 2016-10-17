package cn.delei.designpattern.factory.shape;

import cn.delei.designpattern.factory.AbstractFactory;
import cn.delei.designpattern.factory.color.Color;
import cn.delei.designpattern.factory.shape.impl.Circle;
import cn.delei.designpattern.factory.shape.impl.Rectangle;
import cn.delei.designpattern.factory.shape.impl.Square;

public class ShapeFactory extends AbstractFactory {
	@Override
	public Shape getShape(String shapeType) {
		if (shapeType == null) {
			return null;
		}
		switch (shapeType) {
			case "CIRCLE":
				return new Circle();
			case "RECTANGLE":
				return new Rectangle();
			case "SQUARE":
				return new Square();
		}
		return null;
	}
	
	@Override
	public Color getColor(String color) {
		return null;
	}
}

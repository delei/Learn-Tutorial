package cn.delei.designpattern.factory.color;

import cn.delei.designpattern.factory.AbstractFactory;
import cn.delei.designpattern.factory.color.impl.Blue;
import cn.delei.designpattern.factory.color.impl.Green;
import cn.delei.designpattern.factory.color.impl.Red;
import cn.delei.designpattern.factory.shape.Shape;

public class ColorFactory extends AbstractFactory {
	@Override
	public Shape getShape(String shapeType) {
		return null;
	}
	
	@Override
	public Color getColor(String color) {
		if (color == null) {
			return null;
		}
		switch (color) {
			case "RED":
				return new Red();
			case "GREEN":
				return new Green();
			case "BLUE":
				return new Blue();
		}
		return null;
	}
}

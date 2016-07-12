package cn.delei.designpattern.factory;

import cn.delei.designpattern.factory.color.ColorFactory;
import cn.delei.designpattern.factory.shape.ShapeFactory;

public class FactoryProducer {
	public static AbstractFactory getFactory(String choice) {
		switch (choice) {
			case "SHAPE":
				return new ShapeFactory();
			case "COLOR":
				return new ColorFactory();
		}
		return null;
	}
}

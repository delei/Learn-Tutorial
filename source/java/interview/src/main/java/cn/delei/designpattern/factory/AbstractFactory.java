package cn.delei.designpattern.factory;

import cn.delei.designpattern.factory.color.Color;
import cn.delei.designpattern.factory.shape.Shape;

public abstract class AbstractFactory {
	public abstract Color getColor(String color);
	
	public abstract Shape getShape(String shapeType);
}

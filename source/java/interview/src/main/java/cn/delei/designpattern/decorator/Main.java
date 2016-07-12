package cn.delei.designpattern.decorator;

import cn.delei.designpattern.decorator.impl.Circle;
import cn.delei.designpattern.decorator.impl.Rectangle;

public class Main {
	
	public static void main(String[] args) {
		Shape circle = new Circle();
		Shape redCircle = new RedShapeDecorator(new Circle());
		Shape redRectangle = new RedShapeDecorator(new Rectangle());
		
		circle.draw();
		redCircle.draw();
		redRectangle.draw();
	}
	
}

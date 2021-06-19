package cn.delei.designpattern.decorator;

public class RedShapeDecorator extends ShapeDecorator {
	public RedShapeDecorator(Shape decoratedShape) {
		super(decoratedShape);
	}
	
	@Override
	public void draw() {
		decoratedShape.draw();
		setReadBorder(decoratedShape);
	}
	
	private void setReadBorder(Shape decoratedShape) {
		System.out.println("Red border");
	}
	
}

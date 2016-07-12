package cn.delei.designpattern.proxy.impl;

import cn.delei.designpattern.proxy.Image;

public class ProxyImage implements Image {
	
	private RealImage	realImage;
	private String		fileName;
	
	public ProxyImage(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void display() {
		// TODO Auto-generated method stub
		if (realImage == null) {
			realImage = new RealImage(fileName);
		}
		realImage.display();
	}
	
}

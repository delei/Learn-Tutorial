package cn.delei.designpattern.proxy;

import cn.delei.designpattern.proxy.impl.ProxyImage;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Image image = new ProxyImage("11.jpg");
		image.display();
		image.display();
	}
	
}

package cn.delei.designpattern.singleton;

/*
 * 懒汉式
 * lazy loading 支持多线程，但是同步效率低
 */
public class Singleton_2 {
	private static Singleton_2 instance;
	
	private Singleton_2() {
		
	}
	
	public static synchronized Singleton_2 getInstance() {
		if (instance == null) {
			instance = new Singleton_2();
		}
		return instance;
	}
}

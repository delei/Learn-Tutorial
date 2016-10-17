package cn.delei.designpattern.singleton;

/*
 * 利用classloader机制保证初始化instance时只有一个线程
 * lazy loading,线程安全
 */
public class Singleton_5 {
	private static class SingletonHolder {
		private static final Singleton_5 INSTANCE = new Singleton_5();
	}
	
	private Singleton_5() {
		
	}
	
	public static final Singleton_5 getInstance() {
		return SingletonHolder.INSTANCE;
	}
}

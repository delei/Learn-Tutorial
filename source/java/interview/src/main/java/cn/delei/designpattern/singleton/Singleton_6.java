package cn.delei.designpattern.singleton;

/*
 * 利用classloader机制保证初始化instance时只有一个线程
 * lazy loading,线程安全
 */
public class Singleton_6 {
	private static class SingletonHolder {
		private static final Singleton_6 INSTANCE = new Singleton_6();
	}

	private Singleton_6() {
		
	}
	
	public static final Singleton_6 getInstance() {
		return SingletonHolder.INSTANCE;
	}
}

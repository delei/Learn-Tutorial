package cn.delei.designpattern.singleton;

/*
 * 双重锁机制
 * volatile加synchronized关键字
 * 线程安全
 */
public class Singleton_4 {
	private volatile static Singleton_4 instance;// volatile:永远从main memeroy中获取
	
	private Singleton_4() {
		
	}
	
	public static Singleton_4 getInstance() {
		if (instance == null) {
			synchronized (Singleton_4.class) {// 为类(对象)加锁
				if (instance == null) {
					instance = new Singleton_4();
				}
			}
		}
		return instance;
	}
	
}

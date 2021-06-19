package cn.delei.java.concurrent;

/**
 * 非安全的自增长计数器
 *
 * @author deleiguo
 */
public class UnsafeCounter {
    public int count = 0;

    public UnsafeCounter() {
        this.count = 0;
    }

    public void next() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

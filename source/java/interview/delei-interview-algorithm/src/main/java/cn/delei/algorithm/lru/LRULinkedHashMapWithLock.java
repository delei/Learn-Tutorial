package cn.delei.algorithm.lru;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 带有 Lock 操作
 *
 * @param <K> 键
 * @param <V> 键值
 * @author deleiguo
 */
public class LRULinkedHashMapWithLock<K, V> extends LRULinkedHashMap<K, V> {
    private static final long serialVersionUID = -480172344142509767L;

    private final Lock lock = new ReentrantLock();

    LRULinkedHashMapWithLock(int capacity) {
        super(capacity);
    }

    @Override
    public boolean containsValue(Object value) {
        lock.lock();
        try {
            return super.containsValue(value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V get(Object key) {
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        try {
            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        lock.lock();
        try {
            return super.size();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            super.clear();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        lock.lock();
        try {
            return super.containsKey(key);
        } finally {
            lock.unlock();
        }
    }
}

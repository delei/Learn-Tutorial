package cn.delei.algorithm.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU实现：LinkedHashMap
 *
 * @param <K> 键
 * @param <V> 键值
 * @author deleiguo
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = -3788497546136920185L;

    /**
     * 容量
     */
    private final int capacity;

    LRULinkedHashMap(int capacity) {
        // 初始大小，0.75是装载因子，true是表示按照访问时间排序，最近访问的放在头部，最老访问的放在尾部
        super(capacity, 0.75f, true);
        // 指定最大容量
        this.capacity = capacity;
    }

    /**
     * 核心实现
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 如果map里面的元素个数大于了缓存最大容量，则删除链表的顶端元素，即最老的数据
        return size() > capacity;
    }

}

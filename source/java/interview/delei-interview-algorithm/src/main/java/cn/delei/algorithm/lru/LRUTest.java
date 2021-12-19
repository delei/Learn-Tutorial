package cn.delei.algorithm.lru;

/**
 * LRU 测试
 */
public class LRUTest {

    public static void main(String[] args) {
        linkedHashMapTest();
    }

    private static void linkedHashMapTest() {
        LRULinkedHashMap<Integer, String> lru = new LRULinkedHashMap<>(5);
        for (int i = 0; i < 10; i++) {
            lru.put(i, i + "i");
        }
        System.out.println(lru.toString());
    }
}

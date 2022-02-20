package cn.delei.java.util;

public final class MapUtil {

    static final int HASH_MAP_DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    static final int HASH_MAP_MAXIMUM_CAPACITY = 1 << 30;

    static final float HASH_MAP_DEFAULT_LOAD_FACTOR = 0.75f;

    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= HASH_MAP_MAXIMUM_CAPACITY) ? HASH_MAP_MAXIMUM_CAPACITY : n + 1;
    }

    static int calcHashMapInitCapacity(int targetCapacity) {
        if (targetCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    targetCapacity);
        }
        if (targetCapacity > HASH_MAP_MAXIMUM_CAPACITY) {
            targetCapacity = HASH_MAP_MAXIMUM_CAPACITY;
        }
        // float ft = ((float)s / loadFactor) + 1.0F;
        return (int) ((float) targetCapacity / HASH_MAP_DEFAULT_LOAD_FACTOR + 1.0F);
        //return tableSizeFor(capacity);
    }
}

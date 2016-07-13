
#### java.util.HashMap

* 采用数组存储key、value的Entry对象，无容量控制；
* 基于key hash寻找Entry对象存放到数组的位置，对于hash冲突采用链表的方式解决；
* HashMap在插入元素时可能会扩大数组的容量，在扩大容量时需要重新计算hash，并复制对象到新的数组中；
* HashMap是非线程安全的；

######HashMap:
默认的初始化大小相关参数：
```
/**
 * The default initial capacity - MUST be a power of two.
 */
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

/**
 * The maximum capacity, used if a higher value is implicitly specified
 * by either of the constructors with arguments.
 * MUST be a power of two <= 1<<30.
 */
static final int MAXIMUM_CAPACITY = 1 << 30;

/**
 * The load factor used when none specified in constructor.
 */
static final float DEFAULT_LOAD_FACTOR = 0.75f;

```

将loadFactor设为默认的0.75,threshold(临界值)设置为12，并创建一个大小为16的Entry对象数组

构造：
```
public HashMap(int initialCapacity, float loadFactor) {
   if (initialCapacity < 0)
       throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
   if (initialCapacity > MAXIMUM_CAPACITY)
       initialCapacity = MAXIMUM_CAPACITY;
   if (loadFactor <= 0 || Float.isNaN(loadFactor))
       throw new IllegalArgumentException("Illegal load factor: " + loadFactor);

   this.loadFactor = loadFactor;
   threshold = initialCapacity;
   init();
}

public HashMap(Map<? extends K, ? extends V> m) {
    this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1,DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
    inflateTable(threshold);

    putAllForCreate(m);
}

/**
 * Inflates the table.
 */
private void inflateTable(int toSize) {
    // Find a power of 2 >= toSize
    int capacity = roundUpToPowerOf2(toSize);

    threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
    table = new Entry[capacity];
    initHashSeedAsNeeded(capacity);
}

// internal utilities

/**
 * Initialization hook for subclasses. This method is called
 * in all constructors and pseudo-constructors (clone, readObject)
 * after HashMap has been initialized but before any entries have
 * been inserted.  (In the absence of this method, readObject would
 * require explicit knowledge of subclasses.)
 */
void init() {
}

/**
 * Initialize the hashing mask value. We defer initialization until we
 * really need it.
 */
final boolean initHashSeedAsNeeded(int capacity) {
    boolean currentAltHashing = hashSeed != 0;
    boolean useAltHashing = sun.misc.VM.isBooted() &&
            (capacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
    boolean switching = currentAltHashing ^ useAltHashing;
    if (switching) {
        hashSeed = useAltHashing
            ? sun.misc.Hashing.randomHashSeed(this)
            : 0;
    }
    return switching;
}
```

######put
源码：
```
public V put(K key, V value) {
    if (table == EMPTY_TABLE) {
        inflateTable(threshold);
    }
    if (key == null)
        return putForNullKey(value);
    int hash = hash(key);
    int i = indexFor(hash, table.length);
    for (Entry<K,V> e = table[i]; e != null; e = e.next) {
        Object k;
        if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }

    modCount++;
    addEntry(hash, key, value, i);
    return null;
}

private V putForNullKey(V value) {
    for (Entry<K,V> e = table[0]; e != null; e = e.next) {
        if (e.key == null) {
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }
    modCount++;
    addEntry(0, null, value, 0);
    return null;
}
```
如果传入的key为null，则遍历table[0]即Entry数组的第一个Entry对象，并基于该对象的next属性进行遍历，当找到了key为null，则将其value赋值为新的value,然后返回。如果传入的key不是null,则增加一个Entry对象，增加时为先获取当前数


######hash冲突
在找到要存储的目标数组的位置后，获取该数组对应的Entry对象，通过调用Entry对象的next来进行遍历，寻找hash值和计算出来的hash值相等，且key相等或equals的Entry对象，如寻找到，则替换词Entry对象的值，完成put操作，并返回旧的值；如未找到，则往对应的数组位置增加新的Entry对象，增加时采取的办法和Key为null的情况基本相同，只是它是替换指定位置的Entry对象，可以看出，HashMap解决hash冲突采用的是链表的方式，而不是开发定址的方法

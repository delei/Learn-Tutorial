# ConcurrentHashMap
> 非特殊说明时，源码均基于AdoptOpenJDK 11
> 
> 作者: DeleiGuo
> 版权: 本文非特别声明外，均采用 © CC-BY-NC-SA 4.0 许可协议

## 1. 结构
```java
public class ConcurrentHashMap<K,V> extends AbstractMap<K,V>
    implements ConcurrentMap<K,V>, Serializable
```

## 2. 主要属性
和`HashMap`类不同的属性
```java
/**
 * Minimum number of rebinnings per transfer step. Ranges are
 * subdivided to allow multiple resizer threads.  This value
 * serves as a lower bound to avoid resizers encountering
 * excessive memory contention.  The value should be at least
 * DEFAULT_CAPACITY.
 * 扩容相关，每个线程负责最小桶的个数
 */
private static final int MIN_TRANSFER_STRIDE = 16;

/**
 * The number of bits used for generation stamp in sizeCtl.
 * Must be at least 6 for 32bit arrays.
 * 扩容相关，用于计算 sizeCtl
 */
private static final int RESIZE_STAMP_BITS = 16;

/**
 * The maximum number of threads that can help resize.
 * Must fit in 32 - RESIZE_STAMP_BITS bits.
 * 最大辅助扩容线程数量
 */
private static final int MAX_RESIZERS = (1 << (32 - RESIZE_STAMP_BITS)) - 1;

/**
 * The bit shift for recording size stamp in sizeCtl.
 * 扩容相关，用于计算 sizeCtl
 */
private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;

/*
 * Encodings for Node hash fields. See above for explanation.
 * 
 */
static final int MOVED     = -1; // hash for forwarding nodes 移动状态，表示可能正在扩容，或进行数据移动等操作
static final int TREEBIN   = -2; // hash for roots of trees
static final int RESERVED  = -3; // hash for transient reservations
static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash 计算 hash 值使用

/**
* Number of CPUS, to place bounds on some sizings 
* 获取可用 CPU 数量 
*/
static final int NCPU = Runtime.getRuntime().availableProcessors();


/* ---------------- Fields -------------- */

/**
 * The array of bins. Lazily initialized upon first insertion.
 * Size is always a power of two. Accessed directly by iterators.
 * 使用volatile保证可见性，用于存放 Node 数据，默认为16
 */
transient volatile Node<K,V>[] table;

/**
 * The next table to use; non-null only while resizing.
 * 扩容时新生的数据，数组为 table 的2倍
 */
private transient volatile Node<K,V>[] nextTable;

/**
 * Base counter value, used mainly when there is no contention,
 * but also as a fallback during table initialization
 * races. Updated via CAS.
 * 计数的基准值
 */
private transient volatile long baseCount;

/**
 * Table initialization and resizing control.  When negative, the
 * table is being initialized or resized: -1 for initialization,
 * else -(1 + the number of active resizing threads).  Otherwise,
 * when table is null, holds the initial table size to use upon
 * creation, or 0 for default. After initialization, holds the
 * next element count value upon which to resize the table.
 * 控制标识符，不同场景有不同用途
 */
private transient volatile int sizeCtl;

/**
 * The next table index (plus one) to split while resizing.
 * 并发搬运过程中CAS获取区段的下限值
 */
private transient volatile int transferIndex;

/**
 * Spinlock (locked via CAS) used when resizing and/or creating CounterCells.
 * 计数cell初始化或者扩容时基于此字段使用自旋锁
 */
private transient volatile int cellsBusy;

/**
 * Table of counter cells. When non-null, size is a power of 2.
 * 加速多核CPU 计数的 cell 数组，大小为2的幂
 */
private transient volatile CounterCell[] counterCells;

/** jdk.internal.misc.Unsafe */
private static final Unsafe U = Unsafe.getUnsafe();
```

## 3. 内部类
### Node
```java
static class Node<K,V> implements Map.Entry<K,V> {
    final int hash;
    final K key;
    volatile V val; // volatile保证可见性
    volatile Node<K,V> next; // volatile保证可见性
    
    ...... // 省略非重要代码
    
    public final boolean equals(Object o) {
        Object k, v, u; Map.Entry<?,?> e;
        return ((o instanceof Map.Entry) &&
                (k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
                (v = e.getValue()) != null &&
                (k == key || k.equals(key)) &&
                (v == (u = val) || v.equals(u)));
    }

    /**
     * Virtualized support for map.get(); overridden in subclasses.
     */
    Node<K,V> find(int h, Object k) {
        Node<K,V> e = this;
        if (k != null) {
            do {
                K ek;
                if (e.hash == h &&
                    ((ek = e.key) == k || (ek != null && k.equals(ek))))
                    return e;
            } while ((e = e.next) != null);
        }
        return null;
    }
}    
```

## 4.主要方法

### 构造函数
```java
/**
* 无实现，懒加载
*/
public ConcurrentHashMap() {
}


public ConcurrentHashMap(int initialCapacity) {
    this(initialCapacity, LOAD_FACTOR, 1);
}

/**
* @param concurrencyLevel 并发级别，预估的并发更新线程数
* 此参数为了兼容之前的 JDK 版本函数
*/
public ConcurrentHashMap(int initialCapacity,
                             float loadFactor, int concurrencyLevel) {
    // 参数验证
    if (!(loadFactor > 0.0f) || initialCapacity < 0 || concurrencyLevel <= 0)
        throw new IllegalArgumentException();
    // 比较初始容量，并发级别
    if (initialCapacity < concurrencyLevel)   // Use at least as many bins
        initialCapacity = concurrencyLevel;   // as estimated threads
    // 根据初始容量和阀值计算 size
    long size = (long)(1.0 + (long)initialCapacity / loadFactor);
    // tableSizeFor:获取接近 size 的最小2的幂
    // 不超过MAXIMUM_CAPACITY
    int cap = (size >= (long)MAXIMUM_CAPACITY) ?
        MAXIMUM_CAPACITY : tableSizeFor((int)size);
    // 赋值给 sizeCtl
    this.sizeCtl = cap;
}
```

### table 数组操作
```java
/** 获取table中索引 i 处的元素 */
static final <K,V> Node<K,V> tabAt(Node<K,V>[] tab, int i) {
    return (Node<K,V>)U.getObjectAcquire(tab, ((long)i << ASHIFT) + ABASE);
}
/** 通过CAS设置table索引为 i 处的元素 */
static final <K,V> boolean casTabAt(Node<K,V>[] tab, int i,
                                    Node<K,V> c, Node<K,V> v) {
    return U.compareAndSetObject(tab, ((long)i << ASHIFT) + ABASE, c, v);
}
/** 修改table 索引 i 处的元素 */
static final <K,V> void setTabAt(Node<K,V>[] tab, int i, Node<K,V> v) {
    U.putObjectRelease(tab, ((long)i << ASHIFT) + ABASE, v);
}
```

### 哈希相关
```java
/**
* 传入参数为 key 的 hashCode
* HASH_BITS = 0x7fffffff
*/
static final int spread(int h) {
    return (h ^ (h >>> 16)) & HASH_BITS;
}
```

### get方法
```java
public V get(Object key) {
    Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
    // 重新计算 hash
    int h = spread(key.hashCode());
    // (n - 1) & h  计算下标。n 为数组长度
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (e = tabAt(tab, (n - 1) & h)) != null) {
        // 判断头结点是否就是我们需要的节点
        if ((eh = e.hash) == h) {
            if ((ek = e.key) == key || (ek != null && key.equals(ek)))
                return e.val;
        }
        // 如果该位置节点的 hash 值小于 0，说明正在扩容，或者是红黑树
        else if (eh < 0)
            return (p = e.find(h, key)) != null ? p.val : null;
        // 遍历链表
        while ((e = e.next) != null) {
            if (e.hash == h &&
                ((ek = e.key) == key || (ek != null && key.equals(ek))))
                return e.val;
        }
    }
    // 如果没有找到，返回 null
    return null;
}
```

### put 方法
```java
public V put(K key, V value) {
    return putVal(key, value, false);
}

final V putVal(K key, V value, boolean onlyIfAbsent) {
    // 细节：ConcurrentHashMap不允许key 或 value为null，会抛出 NPE
    if (key == null || value == null) throw new NullPointerException();
    
    int hash = spread(key.hashCode()); // 重新获取 hash
    int binCount = 0; // 用于记录相应链表的长度
    // 自旋，死循环保证新增一定可以成功，不成功则进入下一次循环
    for (Node<K,V>[] tab = table;;) {
        Node<K,V> f; int n, i, fh; K fk; V fv;
        // 懒加载实现，第一次 put 时才初始化 table 数组
        if (tab == null || (n = tab.length) == 0)
            tab = initTable();
        /*
        * i = (n - 1) & hash) 计算下标索引
        * 通过tableAt()方法该索引位置的Node
        * 当Node为null时为没有hash冲突的话，使用casTabAt()方法CAS操作将元素插入到table 数组中
        * CAS 无锁化操作，在高并发hash冲突低的情况下，性能良好
        */
        else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
            if (casTabAt(tab, i, null, new Node<K,V>(hash, key, value)))
                break;                   // no lock when adding to empty bin
        }
        else if ((fh = f.hash) == MOVED) // f.hash == -1
            // 帮助数据迁移,hash为-1 说明是一个forwarding nodes节点，表明正在扩容
            tab = helpTransfer(tab, f);
        // 如果onlyIfAbsent为 true，检查第一个 Node 节点
        else if (onlyIfAbsent // check first node without acquiring lock
                 && fh == hash
                 && ((fk = f.key) == key || (fk != null && key.equals(fk)))
                 && (fv = f.val) != null)
            return fv;
        else {
            // 以上情况都不满足的时,处理已存在 Node 的情况
            //  f 是该位置的头结点，而且不为空
            V oldVal = null;
            // 获取数组该位置的头结点的监视器锁, 节点加锁
            synchronized (f) {
                // 再次判断，索引位置的数据没有被修改
                if (tabAt(tab, i) == f) {
                    if (fh >= 0) { // hash 值大约0，说明是链表
                        binCount = 1; 
                        // 遍历链表，binCount 累加，记录链表的长度
                        for (Node<K,V> e = f;; ++binCount) {
                            K ek;
                            // 判断 hash 相等，key 相等，key equals 相等
                            // 如果进行值覆盖，则进行替换覆盖
                            if (e.hash == hash &&
                                ((ek = e.key) == key ||
                                 (ek != null && key.equals(ek)))) {
                                oldVal = e.val;
                                if (!onlyIfAbsent)
                                    e.val = value;
                                break;
                            }
                            Node<K,V> pred = e;
                            // next 为 null，则说明循环到了链表末尾
                            // 进行插入操作，将新值放到链表的最后，break，退出自旋循环
                            if ((e = e.next) == null) {
                                pred.next = new Node<K,V>(hash, key, value);
                                break;
                            }
                        }
                    }
                    else if (f instanceof TreeBin) { // 红黑树
                        Node<K,V> p;
                        binCount = 2;
                        // 调用红黑树的插入方法进行插入
                        if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                       value)) != null) {
                            oldVal = p.val;
                            if (!onlyIfAbsent)
                                p.val = value;
                        }
                    }
                    else if (f instanceof ReservationNode)
                        // 如果是保留节点ReservationNode，抛出异常
                        throw new IllegalStateException("Recursive update");
                }
            }
            // synchronized锁结束
            // binCount 不为0，说明新增成功
            if (binCount != 0) {
                // 判断是否要转换为红黑树
                if (binCount >= TREEIFY_THRESHOLD)
                    treeifyBin(tab, i); // 转换为红黑树
                if (oldVal != null)
                    return oldVal;
                break;
            }
        }
    }
    // 统计数量以及决定是否需要扩容
    addCount(1L, binCount);
    return null;
}
```

### 初始化 Table
```java
private final Node<K,V>[] initTable() {
    Node<K,V>[] tab; int sc;
    // while 自旋，保证初始化成功
    while ((tab = table) == null || tab.length == 0) {
        // 如果 sizeCtl<0 ，表名有线程正在初始化
        if ((sc = sizeCtl) < 0)
            // 释放当前 CPU 的调度权，重新发起锁竞争,同时将此时的 sizeCtl 重新赋值给变量 sc
            // 使当前线程由执行状态，变成为就绪状态，让出cpu时间
            Thread.yield(); // lost initialization race; just spin
        else if (U.compareAndSetInt(this, SIZECTL, sc, -1)) {
            // CAS 赋值保证当前只有一个线程在初始化，-1 表示正在初始化
            try {
                // 双重 check
                // 很可能执行到此的时候，table 可能已经不为空
                if ((tab = table) == null || tab.length == 0) {
                    // 如果 sc>0 则使用 sc 的值，否则为默认大小16
                    int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                    @SuppressWarnings("unchecked")
                    Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                    table = tab = nt;
                    // 设置 sc 为 table 大小的3/4
                    sc = n - (n >>> 2);
                }
            } finally {
                // 将 sc 值赋值给 sizeCtl
                sizeCtl = sc;
            }
            break;
        }
    }
    return tab;
}
```

### 扩容相关
```java
/**
* 此时保存数据所在的链表转为红黑树，还是对整个哈希表扩容
*/
private final void treeifyBin(Node<K,V>[] tab, int index) {
    Node<K,V> b; int n;
    if (tab != null) {
        // 如果 table 长度小于64，则进行链表扩容
        // table 长度大于等于64时，才转换为红黑树
        if ((n = tab.length) < MIN_TREEIFY_CAPACITY)
            tryPresize(n << 1); // n<<1,扩容2倍
        else if ((b = tabAt(tab, index)) != null && b.hash >= 0) {
            // 将 table 中 index 位置的链表转换为红黑树
            synchronized (b) {
                if (tabAt(tab, index) == b) {
                    TreeNode<K,V> hd = null, tl = null;
                    for (Node<K,V> e = b; e != null; e = e.next) {
                        // 转换构建 TreeNode 对象
                        TreeNode<K,V> p =
                            new TreeNode<K,V>(e.hash, e.key, e.val,
                                              null, null);
                        if ((p.prev = tl) == null)
                            hd = p;
                        else
                            tl.next = p;
                        tl = p;
                    }
                    // 将相应 index 位置的节点替换为新的红黑树节点，TreeBin代表红黑树
                    setTabAt(tab, index, new TreeBin<K,V>(hd));
                }
            }
        }
    }
}

/**
* 扩容，size 参数为需要扩容到的最终大小
*/
private final void tryPresize(int size) {
    // 大小不超过最大容量
    // c: size 的 1.5 倍，再加 1，再往上取最近的 2 的 n 次方
    int c = (size >= (MAXIMUM_CAPACITY >>> 1)) ? MAXIMUM_CAPACITY :
        tableSizeFor(size + (size >>> 1) + 1);
    int sc;
    
    // while 循环，sc 和 sizeCtl 通过 CAS 来更新值
    while ((sc = sizeCtl) >= 0) {
        Node<K,V>[] tab = table; int n;
        // 此处的判断 table 是否为空，是因为 pullAll 方法不调用initTable方法，而是直接调用了tryPresize方法，需要加入逻辑判断
        if (tab == null || (n = tab.length) == 0) {
            n = (sc > c) ? sc : c;
            // CAS 操作 sizeCtl
            if (U.compareAndSetInt(this, SIZECTL, sc, -1)) {
                try {
                    if (table == tab) {
                        @SuppressWarnings("unchecked")
                        Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                        table = nt;
                        sc = n - (n >>> 2);
                    }
                } finally {
                    sizeCtl = sc;
                }
            }
        }
        else if (c <= sc || n >= MAXIMUM_CAPACITY)
            // 已经达到 C 的值或者已经超过最大容量，结束扩容
            break;
        else if (tab == table) {
            // table 已经存在，那么对已有的 table 进行扩容
            int rs = resizeStamp(n);
            // CAS更新 sizeCtl 值
            if (U.compareAndSetInt(this, SIZECTL, sc,
                                    (rs << RESIZE_STAMP_SHIFT) + 2))
                // 把当前的 table 数据移入新创建的扩容后的table 中
                transfer(tab, null);
        }
    }
}

/**
 * Moves and/or copies the nodes in each bin to new table. See
 * above for explanation.
 * 数据迁移
 */
private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
    int n = tab.length, stride;
    
    /* 
    * stride 在单核下直接等于 n，多核模式下为 (n>>>3)/NCPU，最小值是 16
    * MIN_TRANSFER_STRIDE 用来控制不要占用太多CPU
    * stride 可以理解为”步长“，有 n 个位置是需要进行迁移的
    * 将这 n 个任务分为多个任务包，每个任务包有 stride 个任务
    */
    if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
        stride = MIN_TRANSFER_STRIDE; // subdivide range
    
    // 如果 nextTab 为 null，先进行一次初始化
    if (nextTab == null) {            // initiating
        try {
            @SuppressWarnings("unchecked")
            // n<<1 ,容量翻倍
            Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];
            nextTab = nt;
        } catch (Throwable ex) {      // try to cope with OOME
            sizeCtl = Integer.MAX_VALUE;
            return;
        }
        // nextTable 是 ConcurrentHashMap 中的属性
        nextTable = nextTab;
        // transferIndex 也是 ConcurrentHashMap 的属性，用于控制迁移的位置
        transferIndex = n;
    }
    // 新数组的长度
    int nextn = nextTab.length;
    
    // ForwardingNode: 正在被迁移的 Node，用来控制并发
    // 当一个节点为空或已经被转移之后，就设置为ForwardingNode
    ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
    
    boolean advance = true; // 为 true 表示可以进行下一个位置的迁移
    boolean finishing = false; // to ensure sweep before committing nextTab 完成状态，如果是 true，表示扩容结束
    
    // 无限自旋，i 的值会从原数组的最大值开始，逐步递减到0
    // i 是位置索引，bound 是边界，注意是从后往前
    for (int i = 0, bound = 0;;) {
        Node<K,V> f; int fh;
        
        // while 循环: advance为 true
        while (advance) {
            int nextIndex, nextBound;
            // 判断递减 i 超过边界，或已经结束扩容
            if (--i >= bound || finishing)
                advance = false; // 结束循环
            else if ((nextIndex = transferIndex) <= 0) {
                // transferIndex 一旦小于等于 0，说明原数组的所有位置都有相应的线程去处理
                i = -1;
                advance = false; // 结束循环
            }
            else if (U.compareAndSetInt
                     (this, TRANSFERINDEX, nextIndex,
                      nextBound = (nextIndex > stride ?
                                   nextIndex - stride : 0))) {
                // CAS 操作，nextBound 是这次迁移任务的边界，从后往前
                // 减少 i 的值
                bound = nextBound;
                i = nextIndex - 1;
                advance = false;
            }
        }
        
        // if 满足如下条件则说明移动结束
        if (i < 0 || i >= n || i + n >= nextn) {
            int sc;
            
            if (finishing) { // 如果结束状态
                nextTable = null;
                // 将新的 nextTab 赋值给 table 属性，完成迁移
                table = nextTab; 
                // 重新计算sizeCtl: n 是原数组长度，所以 sizeCtl 得出的值将是新数组长度的 0.75 倍
                sizeCtl = (n << 1) - (n >>> 1); 
                return;
            }
            
            /*
            * sizeCtl 在迁移前会设置为 (rs << RESIZE_STAMP_SHIFT) + 2
            * 每有一个线程参与迁移就会将 sizeCtl 加 1
            * 这里使用 CAS 操作对 sizeCtl 进行减 1，代表做完了属于自己的任务
            */
            if (U.compareAndSetInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                    // 任务结束，方法退出
                    return;
                finishing = advance = true;
                i = n; // recheck before commit
            }
        }
        // 如果位置 i 处是空的，没有任何节点，那么放入刚刚初始化的 ForwardingNode ”空节点“
        else if ((f = tabAt(tab, i)) == null)
            advance = casTabAt(tab, i, null, fwd);
        // 该位置处是一个 ForwardingNode，代表该位置已经迁移过了
        else if ((fh = f.hash) == MOVED)
            advance = true; // already processed
        else {
            // 上述情况均不满足
            // 对数组该位置处的结点加锁，开始处理数组该位置处的迁移工作
            synchronized (f) {
                if (tabAt(tab, i) == f) {
                    Node<K,V> ln, hn;
                    
                    // 头结点的 hash 大于 0，说明是链表的 Node 节点
                    if (fh >= 0) {
                        /*
                        *  需要将链表一分为二
                        *  找到原链表中的 lastRun，然后 lastRun 及其之后的节点是一起进行迁移的
                        *  lastRun 之前的节点需要进行克隆，然后分到两个链表中
                        */
                        int runBit = fh & n;
                        Node<K,V> lastRun = f;
                        for (Node<K,V> p = f.next; p != null; p = p.next) {
                            int b = p.hash & n;
                            if (b != runBit) {
                                runBit = b;
                                lastRun = p;
                            }
                        }
                        if (runBit == 0) {
                            ln = lastRun;
                            hn = null;
                        }
                        else {
                            hn = lastRun;
                            ln = null;
                        }
                        for (Node<K,V> p = f; p != lastRun; p = p.next) {
                            int ph = p.hash; K pk = p.key; V pv = p.val;
                            if ((ph & n) == 0)
                                ln = new Node<K,V>(ph, pk, pv, ln);
                            else
                                hn = new Node<K,V>(ph, pk, pv, hn);
                        }
                        // 其中的一个链表放在新数组的位置 i
                        setTabAt(nextTab, i, ln);
                        // 另一个链表放在新数组的位置 i+n
                        setTabAt(nextTab, i + n, hn);
                        // 将原数组该位置处设置为 fwd，代表该位置已经处理完毕，
                        // 其他线程一旦看到该位置的 hash 值为 MOVED，就不会进行迁移了
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                    else if (f instanceof TreeBin) {
                        // 红黑树的迁移
                        TreeBin<K,V> t = (TreeBin<K,V>)f;
                        TreeNode<K,V> lo = null, loTail = null;
                        TreeNode<K,V> hi = null, hiTail = null;
                        int lc = 0, hc = 0;
                        for (Node<K,V> e = t.first; e != null; e = e.next) {
                            int h = e.hash;
                            TreeNode<K,V> p = new TreeNode<K,V>
                                (h, e.key, e.val, null, null);
                            if ((h & n) == 0) {
                                if ((p.prev = loTail) == null)
                                    lo = p;
                                else
                                    loTail.next = p;
                                loTail = p;
                                ++lc;
                            }
                            else {
                                if ((p.prev = hiTail) == null)
                                    hi = p;
                                else
                                    hiTail.next = p;
                                hiTail = p;
                                ++hc;
                            }
                        }
                        // 如果一分为二后，节点数少于 8，那么将红黑树转换回链表
                        ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                            (hc != 0) ? new TreeBin<K,V>(lo) : t;
                        hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                            (lc != 0) ? new TreeBin<K,V>(hi) : t;
                            
                        // 将 ln 放置在新数组的位置 i
                        setTabAt(nextTab, i, ln);
                        // 将 hn 放置在新数组的位置 i+n
                        setTabAt(nextTab, i + n, hn);
                        // 将原数组该位置处设置为 fwd，代表该位置已经处理完毕，
                        // 其他线程一旦看到该位置的 hash 值为 MOVED，就不会进行迁移了
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                }
            }
        }
    }
}
```



## 参考资料
- [AdoptOpenJDK openjdk-jdk11u](https://github.com/AdoptOpenJDK/openjdk-jdk11u)
- [Java 全栈知识体系-JUC集合: ConcurrentHashMap详解](https://www.pdai.tech/md/java/thread/java-thread-x-juc-collection-ConcurrentHashMap.html)
- [月伴飞鱼-ConcurrentHashMap核心原理，这次彻底给整明白了](https://juejin.cn/post/6887489479998144519)
- [Ccww-死磕了ConcurrentHashMap源码和面试题(一)](https://segmentfault.com/a/1190000024432650)
- [ConcurrentHashMap源码分析(1.8)](https://www.cnblogs.com/zerotomax/p/8687425.html#go8)
- [Java ConcurrentHashMap 高并发安全实现原理解析](https://mp.weixin.qq.com/s/4sz6sTPvBigR_1g8piFxug)
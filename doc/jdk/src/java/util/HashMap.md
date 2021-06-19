
# java.util.HashMap

> 非特殊说明时，源码均基于AdoptOpenJDK11
>
> 作者: DeleiGuo<br>
> 版权: 本文非特别声明外，均采用 © CC-BY-NC-SA 4.0 许可协议

## 1. 结构
```java
public class HashMap<K,V> extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable
```

## 2. 属性
```java
/**
* The default initial capacity - MUST be a power of two.
* 默认大小为16，容量大小必须为2的幂
*/
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

/** 最大容量 */
static final int MAXIMUM_CAPACITY = 1 << 30;

/** 默认的装载因子 */
static final float DEFAULT_LOAD_FACTOR = 0.75f;

/** 单个链表长度转为红黑树的阀值 */
static final int TREEIFY_THRESHOLD = 8;

/** 单个链表由红黑树转为链表的长度阀值 */
static final int UNTREEIFY_THRESHOLD = 6;

/** 当table的容量最少为64时，才转为红黑树 */
static final int MIN_TREEIFY_CAPACITY = 64;

/** 存储数据的键值对Node对象数组 */
transient Node<K,V>[] table;

transient Set<Map.Entry<K,V>> entrySet;

/**
* The number of key-value mappings contained in this map.
* map的大小
*/
transient int size;

/** 修改的次数，fail-fast有关 */
transient int modCount;

/** 扩容阀值 */
int threshold;

/** 装载因子 */
final float loadFactor;
```

## 3. 内部类
```java
/** 单向链表结构 */
static class Node<K,V> implements Map.Entry<K,V> {
        final int hash; // hash值
        final K key; // key
        V value; // 值
        Node<K,V> next; // 下一个Node
        .....
}

```

## 4. 操作

### Hash扰动函数
```java
static final int hash(Object key) {
	int h;
	return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

```

### 构造
```java
/** 无参构造 */
public HashMap() {
  // 初始化了默认装载因子，没有初始化table
  this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
}

/** 有参构造 */
public HashMap(int initialCapacity) {
  this(initialCapacity, DEFAULT_LOAD_FACTOR);
}

/** 有参构造 */
public HashMap(int initialCapacity, float loadFactor) {
  // 初始容量必须大于0
  if (initialCapacity < 0)
    throw new IllegalArgumentException("Illegal initial capacity: " +
                                       initialCapacity);
  // 初始容量不能超过最大容量
  if (initialCapacity > MAXIMUM_CAPACITY)
    initialCapacity = MAXIMUM_CAPACITY;
  if (loadFactor <= 0 || Float.isNaN(loadFactor))
    throw new IllegalArgumentException("Illegal load factor: " +
                                       loadFactor);
  this.loadFactor = loadFactor;
  // 通过tableSizeFor获取距初始化容量值最近的2的幂
  this.threshold = tableSizeFor(initialCapacity);
}

/** 获取相应值最近的2的幂 */
static final int tableSizeFor(int cap) {
    int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```


### get操作
```java
// 根据 key 获取 value
public V get(Object key) {
  Node<K,V> e;
  return (e = getNode(hash(key), key)) == null ? null : e.value;
}

final Node<K,V> getNode(int hash, Object key) {
  Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
  if ((tab = table) != null && (n = tab.length) > 0 &&
      // (table.length-1)&hash 获取索引位置 
      (first = tab[(n - 1) & hash]) != null) {
    // 与索引位置的第一个节点开始比较
    if (first.hash == hash && // always check first node
        ((k = first.key) == key || (key != null && key.equals(k))))
      return first;
    if ((e = first.next) != null) {
      if (first instanceof TreeNode)
        // 如果此链表为红黑树，则调用红黑树的方法获取
        return ((TreeNode<K,V>)first).getTreeNode(hash, key);
      do {
        // 如果非红黑树，则为链表
        // 循环遍历链表
        if (e.hash == hash &&
            ((k = e.key) == key || (key != null && key.equals(k))))
          // 比较hash值，key是否相等
          return e;
      } while ((e = e.next) != null);
    }
  }
  return null;
}
```

### put操作

```java
public V put(K key, V value) {
  return putVal(hash(key), key, value, false, true);
}

final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
  Node<K,V>[] tab; Node<K,V> p; int n, i;

  if ((tab = table) == null || (n = tab.length) == 0)
    // 如果table数组为null，则可能是第一次put，需要扩容
    n = (tab = resize()).length;
  if ((p = tab[i = (n - 1) & hash]) == null)
    // i = (table.length - 1) & hash 获取索引
    // 如果数组当前位置没有元素，则创建新的Node对象直接放入
    tab[i] = newNode(hash, key, value, null);
  else {
    Node<K,V> e; K k;
    if (p.hash == hash &&
        ((k = p.key) == key || (key != null && key.equals(k))))
      // 如果当前位置已存在key相同的元素，直接覆盖
      e = p;
    else if (p instanceof TreeNode)
      // 如果是红黑树，调用红黑树的put相关方法
      e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
    else {
      // 如果是链表，遍历链表
      // binCount用于计算当前位置链表的长度
      for (int binCount = 0; ; ++binCount) {
        if ((e = p.next) == null) {
          // 如果是最后一个节点，则尾插
          p.next = newNode(hash, key, value, null);
          if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
            // 如果链表长度超过8，则转为红黑树
            treeifyBin(tab, hash);
          break;
        }
        if (e.hash == hash &&
            ((k = e.key) == key || (key != null && key.equals(k))))
          break;
        p = e;
      }
    }
    if (e != null) { // existing mapping for key
      V oldValue = e.value;
      if (!onlyIfAbsent || oldValue == null)
        e.value = value;
      afterNodeAccess(e); // 该方法无实现，主要用于LinkedHashMap put完成后进行额外操作
      return oldValue;
    }
  }
  ++modCount; // modCount增加
  if (++size > threshold)
    // size增加，如果size的值触发了阀值大小，需要扩容
    resize();
  afterNodeInsertion(evict);
  return null;
}
```

### 扩容相关

```java
final Node<K,V>[] resize() {
  Node<K,V>[] oldTab = table;
  int oldCap = (oldTab == null) ? 0 : oldTab.length; // 现有容量
  int oldThr = threshold; // 现有阀值
  int newCap, newThr = 0;
  if (oldCap > 0) {
    // 如果数组长度已经最大容量，无变化
    if (oldCap >= MAXIMUM_CAPACITY) {
      threshold = Integer.MAX_VALUE;
      return oldTab;
    }
    // 如果数组长度未超过最大容量
    else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
             oldCap >= DEFAULT_INITIAL_CAPACITY)
      // 新的容量 = 现有的容量 * 2 (需要保证计算后新的容量不超过最大容量)
      // 新的阀值 = 现有的阀值 * 2 (计算后新的阀值大于等于最小的容量)
      newThr = oldThr << 1; // double threshold
  }
  else if (oldThr > 0) // initial capacity was placed in threshold
    newCap = oldThr;
  else {               // zero initial threshold signifies using defaults
    // 如果现有容量为0 ，则初始化为默认容量和默认阀值
    newCap = DEFAULT_INITIAL_CAPACITY;
    newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
  }
  if (newThr == 0) {
    float ft = (float)newCap * loadFactor;
    newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
              (int)ft : Integer.MAX_VALUE);
  }
  threshold = newThr;
  @SuppressWarnings({"rawtypes","unchecked"})
  
  Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap]; // 创建新容量的Node对象数组
  table = newTab;
  if (oldTab != null) {
    // 遍历现有的数组
    for (int j = 0; j < oldCap; ++j) {
      Node<K,V> e; // 老的元素
      if ((e = oldTab[j]) != null) {
        oldTab[j] = null;
        
        // 如果老的元素.next为null，标识老数组该索引位置没有hash冲突
        if (e.next == null)
          // e.hash & (newCap - 1) 计算在新数组中的索引位置
          // 将老的元素放入新的数组中
          newTab[e.hash & (newCap - 1)] = e;
        else if (e instanceof TreeNode)
          // 如果老的元素是红黑树类型
          ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
        else { // preserve order
          
          Node<K,V> loHead = null, loTail = null;
          Node<K,V> hiHead = null, hiTail = null;
          Node<K,V> next;
          do {
            next = e.next;
            if ((e.hash & oldCap) == 0) {
              if (loTail == null)
                loHead = e;
              else
                loTail.next = e;
              loTail = e;
            }
            else {
              if (hiTail == null)
                hiHead = e;
              else
                hiTail.next = e;
              hiTail = e;
            }
          } while ((e = next) != null);
          if (loTail != null) {
            loTail.next = null;
            newTab[j] = loHead;
          }
          if (hiTail != null) {
            hiTail.next = null;
            newTab[j + oldCap] = hiHead;
          }
        }
      }
    }
  }
  return newTab;
}
```


# java.util.ArrayList

> 非特殊说明时，源码均基于AdoptOpenJDK11
>
> 作者: DeleiGuo
> 版权: 本文非特别声明外，均采用 © CC-BY-NC-SA 4.0 许可协议

## 1. 结构

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

## 2. 属性

```java
/**
* Default initial capacity.
* 默认容量大小为10
*/
private static final int DEFAULT_CAPACITY = 10;

/**
* Shared empty array instance used for empty instances.
* new ArrayList()无参构建函数时,大小为0
* 第一次add() 时初始化 elementData 为DEFAULT_CAPACITY
*/
private static final Object[] EMPTY_ELEMENTDATA = {};

private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

/** 
* 存储元素的对象数组
* transient修饰
*/
transient Object[] elementData;

/**
* 实际存储的元素数量
*/
private int size;

/** 最大的容量 */
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

/** ===== 父类AbstractList中的重要属性 ===== */
/** 修改次数，与 fast-fail 相关 */
protected transient int modCount = 0;
```

## 3. 主要方法

### 构造

``` java
/** 无参构造函数 */
public ArrayList() {
  // 默认元素数据为空的对象数组
  this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}

/** 指定初始容量的有参构造函数 */
public ArrayList(int initialCapacity) {
  // 判断容量参数并初始化数组
  if (initialCapacity > 0) {
    this.elementData = new Object[initialCapacity];
  } else if (initialCapacity == 0) {
    this.elementData = EMPTY_ELEMENTDATA;
  } else {
    throw new IllegalArgumentException("Illegal Capacity: "+
                                       initialCapacity);
  }
}
```

### 操作

```java
/** 添加元素 */
public boolean add(E e) {
  modCount++; // 此属性定义于父类AbstractList中
  add(e, elementData, size);
  return true;
}

private void add(E e, Object[] elementData, int s) {
  if (s == elementData.length)
    elementData = grow(); // 扩容判断
  elementData[s] = e; // 将元素放入数组相应位置
  size = s + 1; // size+1
}

public void add(int index, E element) {
  rangeCheckForAdd(index); // 数组下标越界检查
  modCount++;
  final int s;
  Object[] elementData;
  if ((s = size) == (elementData = this.elementData).length)
    elementData = grow();
  // 复制数组/移动
  System.arraycopy(elementData, index,
                   elementData, index + 1,
                   s - index);
  elementData[index] = element;
  size = s + 1;
}

public E remove(int index) {
  Objects.checkIndex(index, size); // 数组下标越界检车
  final Object[] es = elementData;

  @SuppressWarnings("unchecked") E oldValue = (E) es[index];
  fastRemove(es, index);

  return oldValue;
}

private void fastRemove(Object[] es, int i) {
  modCount++;
  final int newSize;
  if ((newSize = size - 1) > i)
    // 数组复制/移动
    System.arraycopy(es, i + 1, es, i, newSize - i);
  es[size = newSize] = null; // 将结尾的元素置为 null
}
```

### 扩容

```java

private Object[] grow() {
  return grow(size + 1);
}

private Object[] grow(int minCapacity) {
  // 适用 Arrays.copyOf 进行复制，扩容
  return elementData = Arrays.copyOf(elementData,
                                     newCapacity(minCapacity));
}

private int newCapacity(int minCapacity) {
  // overflow-conscious code
  int oldCapacity = elementData.length;
  // 扩容为原来的1.5倍
  int newCapacity = oldCapacity + (oldCapacity >> 1);
  if (newCapacity - minCapacity <= 0) {
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
      return Math.max(DEFAULT_CAPACITY, minCapacity);
    if (minCapacity < 0) // overflow
      throw new OutOfMemoryError();
    return minCapacity;
  }
  return (newCapacity - MAX_ARRAY_SIZE <= 0)
    ? newCapacity
    : hugeCapacity(minCapacity);
}

private static int hugeCapacity(int minCapacity) {
  if (minCapacity < 0) // overflow
    throw new OutOfMemoryError();
  // 虽然定义了最大的容量Integer.MAX_VALUE-8，但有以下情况可以让容量达到Integer.MAX_VALUE
  return (minCapacity > MAX_ARRAY_SIZE)
    ? Integer.MAX_VALUE
    : MAX_ARRAY_SIZE;
}
```

## 4. 静态内部类SubList

```java
/** ArrayList 中的 subList 方法 */
public List<E> subList(int fromIndex, int toIndex) {
  // 调用父类AbstractList中的subListRangeCheck方法进行数组下标检查
  subListRangeCheck(fromIndex, toIndex, size);
  // 返回的内部类 SubList，不可后续进行强转
  return new SubList<>(this, fromIndex, toIndex);
}
```

### 结构

```java
/**
* 该类和 ArrayList 非继承关系
*/
private static class SubList<E> extends AbstractList<E> implements RandomAccess
```

### 属性

```java
private final ArrayList<E> root; // 父类，指当前的 ArrayList
private final SubList<E> parent; 
private final int offset;
private int size;
```

### 构造

```java
/** SubList构造函数将原来的List和其部分属性赋值给了自己的相应属性，返回了父类的视图(View) */

public SubList(ArrayList<E> root, int fromIndex, int toIndex) {
  this.root = root;
  this.parent = null;
  this.offset = fromIndex;
  this.size = toIndex - fromIndex;
  this.modCount = root.modCount;
}

private SubList(SubList<E> parent, int fromIndex, int toIndex) {
  this.root = parent.root;
  this.parent = parent;
  this.offset = parent.offset + fromIndex;
  this.size = toIndex - fromIndex;
  this.modCount = parent.modCount;
}
```

### 操作

```java
/** 添加 */
public void add(int index, E element) {
  rangeCheckForAdd(index); // 下标检查
  checkForComodification(); // fail-fast 检查
  root.add(offset + index, element); // 调用的是 ArrayList 的 add 方法
  updateSizeAndModCount(1);
}
```

SubList单独定义了set、get、size、add、remove等操作方法，操作了ArrayList的同一个elementData数组，即对SubList进行修改时，也会影响到ArrayList的数据。


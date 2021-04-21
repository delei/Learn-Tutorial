# java.util.LinkedList

> 非特殊说明时，源码均基于AdoptOpenJDK11
>
> 作者: DeleiGuo
> 版权: 本文非特别声明外，均采用 © CC-BY-NC-SA 4.0 许可协议

## 1. 结构

```java
/**
* 同时实现了 List 和 Deque 接口，说明既可以是一个顺序容器，也可以是一个队列，也可以是栈
*/
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

## 2. 属性

``` java
/** 元素数量 */
transient int size = 0;

/**
* Pointer to first node.
* 第一个节点
*/
transient Node<E> first;

/**
* Pointer to last node.
* 最后一个节点
*/
transient Node<E> last;
```

### 内部类 Node

```java
private static class Node<E> {
  E item; // 数据、元素
  Node<E> next; // 下一个节点
  Node<E> prev; // 上一个节点

  Node(Node<E> prev, E element, Node<E> next) {
    this.item = element;
    this.next = next;
    this.prev = prev;
  }
}
```

## 3. 主要方法

### 构造

```java
/**
* Constructs an empty list.
* 无参构造，无相关实现
*/
public LinkedList() {}

/**
* 有参构造
*/
public LinkedList(Collection<? extends E> c) {
  this();
  addAll(c);
}
```

### add 添加

```java
/** 添加单个元素 */
public boolean add(E e) {
  linkLast(e); // 使用尾插
  return true;
}

/** 在指定位置 index 添加元素 */
public void add(int index, E element) {
  // 检查下标是否在0~size 之间
  checkPositionIndex(index);
	
  if (index == size)
    // 如果位置就是当前链表尾部，直接尾插
    linkLast(element);
  else
    // 调用 node 方法获取 index 位置的节点，插入新的元素
    linkBefore(element, node(index));
}

/** 头部插入 */
private void linkFirst(E e) {
  final Node<E> f = first;// last 节点
  // 构造新的 Node 对象，新对象的 prev 为 null
  final Node<E> newNode = new Node<>(null, e, f);
  first = newNode; // 将新的 Node 对象置为 first 第一个节点
  if (f == null)
    // 如果当前的 first 节点为 null，直接新的节点赋值给 last
    last = newNode;
  else
    // 如果当前的 first 节点不为 null，将新的节点复制给当前节点的 prev
    f.prev = newNode;
  size++;
  modCount++;
}

/** 尾部插入 */
void linkLast(E e) {
  final Node<E> l = last; // last 节点
  // 构造新的 Node 对象，新对象的 next 为 null
  final Node<E> newNode = new Node<>(l, e, null);
  last = newNode; // 将新的 Node 对象置为 last 最后一个节点
  if (l == null)
    // 如果当前的 last 节点为 null，直接新的节点赋值给 first
    first = newNode;
  else
    // 如果当前的 last 节点不为 null，将新的节点复制给当前节点的 next
    l.next = newNode;
  size++;
  modCount++;
}

/** 某一个对象的前面插入 */
void linkBefore(E e, Node<E> succ) {
  final Node<E> pred = succ.prev; // 当前对象的 prev 节点
  // 构造新的 Node 对象
  // 新对象的 prev 为当前对象的 prev 节点
  // 新对象的 next 为需要插入的节点
  final Node<E> newNode = new Node<>(pred, e, succ);
  succ.prev = newNode;
  if (pred == null)
    first = newNode;
  else
    pred.next = newNode;
  size++;
  modCount++;
}
```

### get获取

```java
/** 获取相应位置的元素 */
public E get(int index) {
  // 检查下标是否在0~size 之间
  checkElementIndex(index);
  // 调用 node 方法
  return node(index).item;
}

/** 获取第一个节点的元素 */
public E getFirst() {
  final Node<E> f = first;
  if (f == null)
    // 为空会抛出异常
    throw new NoSuchElementException();
  return f.item;
}

/** 获取最后一个节点的元素 */
public E getLast() {
  final Node<E> l = last;
  if (l == null)
    // 为空会抛出异常
    throw new NoSuchElementException();
  return l.item;
}

/** 查找相应位置的 Node 对象 */
Node<E> node(int index) {
 	// size >> 1 每次最好的情况下只需遍历链表中一半的元素
  if (index < (size >> 1)) {
    Node<E> x = first;
    // 从first开始遍历
    for (int i = 0; i < index; i++)
      x = x.next;
    return x;
  } else {
    Node<E> x = last;
    // 从last开始遍历
    for (int i = size - 1; i > index; i--)
      x = x.prev;
    return x;
  }
}

/** 从头遍历查找元素 */
public int indexOf(Object o) {
  int index = 0;
  if (o == null) {
    for (Node<E> x = first; x != null; x = x.next) {
      if (x.item == null)
        return index;
      index++;
    }
  } else {
    for (Node<E> x = first; x != null; x = x.next) {
      if (o.equals(x.item))
        return index;
      index++;
    }
  }
  return -1;
}

/** 从尾遍历查找元素 */
public int lastIndexOf(Object o) {
  int index = size;
  if (o == null) {
    for (Node<E> x = last; x != null; x = x.prev) {
      index--;
      if (x.item == null)
        return index;
    }
  } else {
    for (Node<E> x = last; x != null; x = x.prev) {
      index--;
      if (o.equals(x.item))
        return index;
    }
  }
  return -1;
}
```

### remove 删除

```java
/** 无参 */
public E remove() {
  return removeFirst();
}

/** 删除指定位置的元素 */
public E remove(int index) {
  // 检查下标是否在0~size 之间
  checkElementIndex(index);
  // 调用 node 方法获取该位置的 Node 对象后 unlink
  return unlink(node(index));
}

/** 移除 first */
public E removeFirst() {
  final Node<E> f = first;
  if (f == null)
    throw new NoSuchElementException();
  return unlinkFirst(f);
}

/**
* 删除第一个匹配的元素
* 只会删除一个匹配的对象，如果删除了匹配对象，返回true，否则false
*/
public boolean remove(Object o) {
  if (o == null) { // 如果删除对象为null
    //从头开始遍历
    for (Node<E> x = first; x != null; x = x.next) {
      //找到元素
      if (x.item == null) {
        // 移除
        unlink(x);
        return true;
      }
    }
  } else {
    //从头开始遍历
    for (Node<E> x = first; x != null; x = x.next) {
      //找到元素
      if (o.equals(x.item)) {
        // 移除
        unlink(x);
        return true;
      }
    }
  }
  return false;
}

private E unlinkFirst(Node<E> f) {
  final E element = f.item;
  final Node<E> next = f.next;
  f.item = null;
  f.next = null; // help GC
  first = next;
  if (next == null)
    last = null;
  else
    next.prev = null;
  size--;
  modCount++;
  return element;
}

E unlink(Node<E> x) {
  final E element = x.item; // 当前 Node 的数据
  final Node<E> next = x.next; // 当前 Node 的 next
  final Node<E> prev = x.prev; // 当前 Node 的 prev

  // 删除上一个节点指针
  if (prev == null) {
    first = next; // 如果上一个节点为 null(说明当前节点原本是头结点)，令头节点指向该节点的后继节点
  } else {
    prev.next = next; //将上一个节点节点的next节点指向next节点
    x.prev = null; // 将当前节点的 前置节点置空
  }
	// 删除下一个节点指针
  if (next == null) {
    last = prev; // 如果删除的节点是最后一个节点,令最后一个节点指向该节点的 prev节点
  } else {
    next.prev = prev;
    x.next = null;
  }

  x.item = null;
  size--;
  modCount++;
  return element;
}
```

### set 修改

```java
public E set(int index, E element) {
  // 检查下标是否在0~size 之间
  checkElementIndex(index);
  // 调用 node 方法获取该位置的 Node 对象
  Node<E> x = node(index);
  // 老的元素用于返回
  E oldVal = x.item;
  // 将新的元素赋值为当前 Node 对象的 item 属性
  x.item = element;
  return oldVal;
}
```

## 4. 其他数据形式的容器

### Queue 队列

```java
public E peek() {
  final Node<E> f = first;
  return (f == null) ? null : f.item;
}

public E element() {
  return getFirst();
}

public E poll() {
  final Node<E> f = first;
  return (f == null) ? null : unlinkFirst(f);
}

public E remove() {
  return removeFirst();
}

public boolean offer(E e) {
  return add(e);
}
```

### Deque 双端队列方法

```java
public boolean offerFirst(E e) {
  addFirst(e);
  return true;
}

public boolean offerLast(E e) {
  addLast(e);
  return true;
}

public E peekFirst() {
  final Node<E> f = first;
  return (f == null) ? null : f.item;
}

public E peekLast() {
  final Node<E> l = last;
  return (l == null) ? null : l.item;
}

public E pollFirst() {
  final Node<E> f = first;
  return (f == null) ? null : unlinkFirst(f);
}

public E pollLast() {
  final Node<E> l = last;
  return (l == null) ? null : unlinkLast(l);
}

public E pop() {
  return removeFirst();
}

public void push(E e) {
  addFirst(e);
}
```

### 栈(LIFO:后进先出)

```java
public E pop() {
  return removeFirst();
}

public void push(E e) {
  addFirst(e);
}
```



#### java.util.ArrayList

* 基于数组方式实现，无容量限制；
* 插入元素可能要扩容，删除元素并不会减少数组容量(如希望相应的缩小数组容量，可以调用ArrayList的trinToSize()),在查找元素时要遍历数组，对于非null得元素采取equals的方式寻找；
* ArrayList是非线程安全的；

######ArrayList:数组方式存放对象
创建ArrayList(int initialCapacity)
```
/**
* Constructs an empty list with the specified initial capacity.
*
* @param  initialCapacity  the initial capacity of the list
* @throws IllegalArgumentException if the specified initial capacity
*         is negative
*/
public ArrayList(int ç) {
   super();
   if (initialCapacity < 0)
       throw new IllegalArgumentException("Illegal Capacity: "+initialCapacity);
   this.elementData = new Object[initialCapacity];
}
```
其中，super方法调用的是AbstractList,在该类中此方法是个protected的空方法；所以，ArrayList的该方法主要是初始化了一个Object的数组，数组大小即为传入的initialCapacity。

######插入对象：add()
源码：
```
public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
}

private void ensureCapacityInternal(int minCapacity) {
       if (elementData == EMPTY_ELEMENTDATA) {
           minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
       }
       ensureExplicitCapacity(minCapacity);
}

private void ensureExplicitCapacity(int minCapacity) {
   modCount++;

   // overflow-conscious code
   if (minCapacity - elementData.length > 0)
       grow(minCapacity);
}

private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    elementData = Arrays.copyOf(elementData, newCapacity);
}

```

基于ArrayList中已有的元素数量+1到minCapacity，与elementData数组大小进行比较，如果大于当前elementData数组，则把当前elementData赋予给一个新的数组对象，其中通过计算当前数组值*1.5+1得出新的数组的容量，调用Arrays.copeOf方法来生成新的数组对象；


######删除对象：remove()
源码：
```
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                fastRemove(index);
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);
                return true;
            }
    }
    return false;
}

/*
 * Private remove method that skips bounds checking and does not
 * return the value removed.
 */
private void fastRemove(int index) {
    modCount++;
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; // clear to let GC do its work
}
```

首先判断传入对象是否为Null,如果为null,则遍历数组中已有值的元素，与传入对象进行比较是否为Null.如果为空则通过fastRemove删除相对应下标的对象，如果传入对象不为null,则通过equals方法进行比较，同样通过fastRemove方法删除；

fastRemove：如果需要移动，调用底层System.arraycopy方法将index后的对象往前复制以为，并将数组的最后一个元素值设置为null,即释放了对此对象的引用

######判断是否包含此对象：contains
源码：
```
public boolean contains(Object o) {
    return indexOf(o) >= 0;
}

public int indexOf(Object o) {
    if (o == null) {
        for (int i = 0; i < size; i++)
            if (elementData[i]==null)
                return i;
    } else {
        for (int i = 0; i < size; i++)
            if (o.equals(elementData[i]))
                return i;
    }
    return -1;
}
```

判断是否存在该对象，需要遍历整个ArrayList的元素，如果传入对象为空，则直接判断已有元素是否为null，如果返回null，则返回true;如果传入对象不为null,则通过equals方法找寻是否有相等元素，有则返回true

另外还有一个lastIndexOf的方法，与indexOf区别在
```
public int lastIndexOf(Object o) {
   if (o == null) {
       for (int i = size-1; i >= 0; i--)
           if (elementData[i]==null)
               return i;
   } else {
       for (int i = size-1; i >= 0; i--)
           if (o.equals(elementData[i]))
               return i;
   }
   return -1;
}
```

# StringBuilder与StringBuffer

> 非特殊说明时，源码均基于AdoptOpenJDK
>
> 作者: DeleiGuo
> 版权: 本文非特别声明外，均采用 © CC-BY-NC-SA 4.0 许可协议

## 1. 结构

```java
/** StringBuilder继承抽象类AbstractStringBuilder，final修饰 */
public final class StringBuilder
    extends AbstractStringBuilder
    implements java.io.Serializable, Comparable<StringBuilder>, CharSequence

/** StringBuffer继承抽象类AbstractStringBuilder，final修饰 */
public final class StringBuffer
    extends AbstractStringBuilder
    implements java.io.Serializable, Comparable<StringBuffer>, CharSequence

/** 抽象类AbstractStringBuilder */
abstract class AbstractStringBuilder implements Appendable, CharSequence
```

## 2. 属性

```java
/** ===== 主要属性均在父类AbstractStringBuilder中 ===== */

/**
* The value is used for character storage.
* 存储数据的字节数组
* 与String类相比，无final修饰
*/
byte[] value;

/**
* The id of the encoding used to encode the bytes in {@code value}.
* 编码
*/
byte coder;

/**
* The count is the number of characters used.
* 字符个数
*/
int count;

/** 空数组 */
private static final byte[] EMPTYVALUE = new byte[0];

/** 最大的容量 */
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
```


## 3. 方法

### 构造

```java
/** 无参构造 */
public StringBuilder() {
  super(16); // 调用父类实现，传入默认大小为16
}

/** 基于String的构造 */
public StringBuilder(String str) {
  super(str.length() + 16); // 调用父类实现，字符串的长度加16
  append(str); // 追加
}

/** 父类实现 */
AbstractStringBuilder(int capacity) {
  if (COMPACT_STRINGS) { // 是否字符串压缩
    value = new byte[capacity]; // 初始化字节数组大小
    coder = LATIN1; // 字符串压缩时的coder
  } else {
    value = StringUTF16.newBytesFor(capacity);
    coder = UTF16;
  }
}
```

### append追加

```java
/** StringBuilder append  */
public StringBuilder append(String str) {
  super.append(str); // 调用父类AbstractStringBuilder实现
  return this;
}

/** StringBuffer append，使用synchronized */
public synchronized StringBuffer append(String str) {
  toStringCache = null;
  super.append(str); // 调用父类AbstractStringBuilder实现
  return this;
}
```



### AbstractStringBuilder类中append实现

```java
public AbstractStringBuilder append(String str) {
  if (str == null) {
    return appendNull(); // 追加null字符
  }
  int len = str.length();
  // 扩容为当前大小加传入字符串的length
  ensureCapacityInternal(count + len); 
  putStringAt(count, str); // 将字符串放入字节数组value
  count += len;
  return this;
}

/** 放入null */
private AbstractStringBuilder appendNull() {
  ensureCapacityInternal(count + 4); // null为4个大小
  int count = this.count;
  byte[] val = this.value;
  if (isLatin1()) { // Latin
    val[count++] = 'n';
    val[count++] = 'u';
    val[count++] = 'l';
    val[count++] = 'l';
  } else {
    count = StringUTF16.putCharsAt(val, count, 'n', 'u', 'l', 'l');
  }
  this.count = count;
  return this;
}
```

### 扩容

```java
private void ensureCapacityInternal(int minimumCapacity) {
  // overflow-conscious code
  // 如果coder是LATIN1则为0，如果coder是UTF16则为1，即扩容2倍
  int oldCapacity = value.length >> coder;
  // 传入参数的容量大小必须大于当前value数组的大小
  if (minimumCapacity - oldCapacity > 0) {
    // 调用Arrays.copyOf进行复制成新的字节数组
    value = Arrays.copyOf(value,
                          newCapacity(minimumCapacity) << coder);
  }
}

/** 新容量计算 */
private int newCapacity(int minCapacity) {
  // overflow-conscious code
  int oldCapacity = value.length >> coder; // 当前字节数组容量计算
  int newCapacity = (oldCapacity << 1) + 2; // 扩容公式：当前字节数组容量/2 + 2;
  if (newCapacity - minCapacity < 0) {
    newCapacity = minCapacity;
  }
  int SAFE_BOUND = MAX_ARRAY_SIZE >> coder;
  return (newCapacity <= 0 || SAFE_BOUND - newCapacity < 0)
    ? hugeCapacity(minCapacity)
    : newCapacity;
}

/** 与最大容量(边界)判定 */
private int hugeCapacity(int minCapacity) {
  int SAFE_BOUND = MAX_ARRAY_SIZE >> coder;
  int UNSAFE_BOUND = Integer.MAX_VALUE >> coder;
  if (UNSAFE_BOUND - minCapacity < 0) { // overflow
    throw new OutOfMemoryError();
  }
  return (minCapacity > SAFE_BOUND)
    ? minCapacity : SAFE_BOUND;
}
```

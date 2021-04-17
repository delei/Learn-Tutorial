# String

> 非特殊说明时，源码均基于AdoptOpenJDK 11
>
> 作者: DeleiGuo
> 版权: 本文非特别声明外，均采用 © CC-BY-NC-SA 4.0 许可协议

## 1. 结构

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence
```

## 2. 属性

```java
/** 存储数据的字节数组 */
private final byte[] value;

/** 
* 编码格式：LATIN1、UTF16
*/
private final byte coder;

/** hash值 */
private int hash; // Default to 0

/** 是否开启字符串压缩,在static 静态初始化块中初始化为 true*/
static final boolean COMPACT_STRINGS;

/** 字符串压缩的相应静态值，用于判断 */
@Native static final byte LATIN1 = 0;
@Native static final byte UTF16  = 1;

```


## 3. 方法

### 构造

```java
/** 无参构造 */
public String() {
  // 默认即为空字符
  this.value = "".value;
  this.coder = "".coder;
}

/** 常用有参构造*/
public String(char value[]) {
  this(value, 0, value.length, null);
}

/** 常用有参构造*/
public String(byte[] bytes) {
  this(bytes, 0, bytes.length);
}

/** 有参构造 */
String(char[] value, int off, int len, Void sig) {
  if (len == 0) {
    // 如果构建长度为0的字符串即为空字符串
    this.value = "".value;
    this.coder = "".coder;
    return;
  }
  if (COMPACT_STRINGS) {
    // 开启字符串压缩
    byte[] val = StringUTF16.compress(value, off, len);
    if (val != null) {
      this.value = val;
      this.coder = LATIN1;
      return;
    }
  }
  this.coder = UTF16;
  this.value = StringUTF16.toBytes(value, off, len);
}
```

### Hash值
```java
/** 字符串压缩 */
private boolean isLatin1() {
  return COMPACT_STRINGS && coder == LATIN1;
}

/** 获取hashCode */
public int hashCode() {
  int h = hash;
  if (h == 0 && value.length > 0) {
    // 均使用31作为乘数进行计算
    hash = h = isLatin1() ? StringLatin1.hashCode(value)
      : StringUTF16.hashCode(value);
  }
  return h;
}

/** StringLatin1类中的hashCode方法 */
public static int hashCode(byte[] value) {
  int h = 0;
  for (byte v : value) {
    h = 31 * h + (v & 0xff);
  }
  return h;
}

/** StringUTF16类中的hashCode方法 */
public static int hashCode(byte[] value) {
  int h = 0;
  int length = value.length >> 1;
  for (int i = 0; i < length; i++) {
    h = 31 * h + getChar(value, i);
  }
  return h;
}

```

### 长度相关

```java
/** 长度为字节数组长度的一半 */
public static int length(byte[] value) {
  return value.length >> 1;
}

/**
* 判断value 字节数组长度是否为0
* 无法判断 null，会抛出 NPE
*/
public boolean isEmpty() {
  return value.length == 0;
}

/**
* @since 11
* JDK11中新增，能够过滤空格" ","\t"等
* 无法判断 null，会抛出 NPE
*/
public boolean isBlank() {
  return indexOfNonWhitespace() == length();
}

private int indexOfNonWhitespace() {
  if (isLatin1()) {
    return StringLatin1.indexOfNonWhitespace(value);
  } else {
    return StringUTF16.indexOfNonWhitespace(value);
  }
}

/** StringLatin1类中的indexOfNonWhitespace */
public static int indexOfNonWhitespace(byte[] value) {
  int length = value.length;
  int left = 0;
  while (left < length) {
    char ch = (char)(value[left] & 0xff);
    if (ch != ' ' && ch != '\t' && !Character.isWhitespace(ch)) {
      break;
    }
    left++;
  }
  return left;
}

/** StringUTF16类中的indexOfNonWhitespace */
public static int indexOfNonWhitespace(byte[] value) {
  int length = value.length >> 1;
  int left = 0;
  while (left < length) {
    int codepoint = codePointAt(value, left, length);
    if (codepoint != ' ' && codepoint != '\t' && !Character.isWhitespace(codepoint)) {
      break;
    }
    left += Character.charCount(codepoint);
  }
  return left;
}
```


### intern方法

```java
/**
* @return  a string that has the same contents as this string, but is
*          guaranteed to be from a pool of unique strings.
* @jls 3.10.5 String Literals
*
* native实现
* 如果常量池中存在当前字符串, 就会直接返回当前字符串. 如果常量池中没有此字符串, 会将此字符串放入常量池中后, 再返回
*/
public native String intern();
```

### equals重写

```java
public boolean equals(Object anObject) {
  if (this == anObject) { // 判断内存地址是否相同
    return true;
  }
  if (anObject instanceof String) { // 如果是String 实例
    String aString = (String)anObject; // 强制转换
    if (coder() == aString.coder()) { // 判断编码相同
      // 思路：for循环遍历字节数组，匹配相同
      return isLatin1() ? StringLatin1.equals(value, aString.value)
        : StringUTF16.equals(value, aString.value);
    }
  }
  return false;
}

/** StringLatin1类中的equals */
@HotSpotIntrinsicCandidate
public static boolean equals(byte[] value, byte[] other) {
  if (value.length == other.length) {
    for (int i = 0; i < value.length; i++) {
      if (value[i] != other[i]) {
        return false;
      }
    }
    return true;
  }
  return false;
}

/** StringUTF16类中的equals */
@HotSpotIntrinsicCandidate
public static boolean equals(byte[] value, byte[] other) {
  if (value.length == other.length) {
    int len = value.length >> 1;
    for (int i = 0; i < len; i++) {
      if (getChar(value, i) != getChar(other, i)) {
        return false;
      }
    }
    return true;
  }
  return false;
}
```


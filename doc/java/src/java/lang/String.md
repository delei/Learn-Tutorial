
#### java.lang.String相关

#### 一、JVM相关

1、栈： 存放基本数据类型及对象变量的引用，对象本身不存放于栈中而是存放于堆中

* 基础类型 byte (8位)、boolean (1位)、char (16位)、int (32位)、short (16位)、float (32位)、double (64位)、long (64位)

* java代码作用域中定义一个变量时，则java就在栈中为这个变量分配内存空间，当该变量退出该作用域时，java会自动释放该变量所占的空间

2、堆： new操作符的对象

* new创建的对象和数组

* 在堆中分配的内存，由Java虚拟机的自动垃圾回收器来管理

3、静态域： static定义的静态成员变量

4、常量池： 存放常量

#### 二、String
Java中String不是基本数据类型，而是一个final类。

实质为一个final char的数组，既然是final类型，那个该数组引用value就不允许再指向其他对象了，因此只从类的设计角度讲：如果jdk源码中并没有提供对value本身的修改，那么理论上来讲String是不可变的。

```
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final char value[];

    /** Cache the hash code for the string */
    private int hash; // Default to 0

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -6849794470754667710L;

    ......
}

```

#### 三、StringBuffer

>类本身为final<br/>
>存储值的char[]非final修饰<br/>
>自身的很多方法都有synchronized

咋一看,StringBuffer也是final修饰的类

```
public final class StringBuffer
    extends AbstractStringBuilder
    implements java.io.Serializable, CharSequence
{
    ......
}
```

实质得去看抽象类AbstractStringBuilder
String的char[]是final的。但是AbstractStringBuilder类的不是

```
abstract class AbstractStringBuilder implements Appendable, CharSequence {

    char[] value;
    int count;
    AbstractStringBuilder() {
    }
    AbstractStringBuilder(int capacity) {
        value = new char[capacity];
    }

    ......
}
```

StringBuffer本身的append方法中是加入了synchronized修饰
```
super.append(...);
```
这个方法中有一个在AbstractStringBuilder的底层调用：
```
/**
 * This implements the expansion semantics of ensureCapacity with no
 * size check or synchronization.
 */
void expandCapacity(int minimumCapacity) {
    int newCapacity = value.length * 2 + 2;
    if (newCapacity - minimumCapacity < 0)
        newCapacity = minimumCapacity;
    if (newCapacity < 0) {
        if (minimumCapacity < 0) // overflow
            throw new OutOfMemoryError();
        newCapacity = Integer.MAX_VALUE;
    }
    value = Arrays.copyOf(value, newCapacity);
}
```

#### 四、String,StringBuffer,StringBuilder

* 三者在执行速度方面的比较：
    >StringBuilder >  StringBuffer  >  String

* String <（StringBuffer，StringBuilder）的原因
    >String：字符串常量<br/>
    >StringBuffer：字符变量<br/>
    >StringBuilder：字符变量

* StringBuilder与 StringBuffer
    >StringBuilder：线程非安全的<br/>
    >StringBuffer：线程安全的，使用synchronized

当我们在字符串缓冲去被多个线程使用是，JVM不能保证StringBuilder的操作是安全的，虽然他的速度最快，但是可以保证StringBuffer是可以正确操作的。当然大多数情况下就是我们是在单线程下进行的操作，所以大多数情况下是建议用StringBuilder而不用StringBuffer的，就是速度的原因。

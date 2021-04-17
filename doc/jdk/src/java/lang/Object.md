# Object

> 非特殊说明时，源码均基于AdoptOpenJDK 11
> 
> 作者: DeleiGuo
> 版权: 本文非特别声明外，均采用 © CC-BY-NC-SA 4.0 许可协议

```java
public class Object {   

    /** 一个本地方法，具体是用C（C++）在DLL中实现的，然后通过JNI调用。*/    
    private static native void registerNatives();   
   
    /** 对象初始化时自动调用此方法*/  
    static {   
        registerNatives();   
    }   
    
    /** 返回此 Object 的运行时类。*/  
    public final native Class<?> getClass();   

    /**  
    hashCode 的常规协定是：  
    1.在 Java 应用程序执行期间，在对同一对象多次调用 hashCode 方法时，必须一致地返回相同的整数，前提是将对象进行 equals 比较时所用的信息没有被修改。从某一应用程序的一次执行到同一应用程序的另一次执行，该整数无需保持一致。   
    2.如果根据 equals(Object) 方法，两个对象是相等的，那么对这两个对象中的每个对象调用 hashCode 方法都必须生成相同的整数结果。   
    3.如果根据 equals(java.lang.Object) 方法，两个对象不相等，那么对这两个对象中的任一对象上调用 hashCode 方法不 要求一定生成不同的整数结果。但是，程序员应该意识到，为不相等的对象生成不同整数结果可以提高哈希表的性能。  
    */
    public native int hashCode();   

    /** 用于比较2个对象的内存地址是否相等 */
    public boolean equals(Object obj) {   
        return (this == obj);   
    }   

    /** 本地CLONE方法，用于对象的复制。*/  
    protected native Object clone() throws CloneNotSupportedException;   

    /** 返回该对象的字符串表示。非常重要的方法*/  
    public String toString() {   
        return getClass().getName() + "@" + Integer.toHexString(hashCode());  
    }   

    /** 唤醒在此对象监视器上等待的单个线程。*/  
    public final native void notify();   

    /** 唤醒在此对象监视器上等待的所有线程。*/  
    public final native void notifyAll();   


    /** 在其他线程调用此对象的 notify() 方法或 notifyAll() 方法前，导致当前线程等待。换句话说，此方法的行为就好像它仅执行 wait(0) 调用一样。   
    当前线程必须拥有此对象监视器。该线程发布对此监视器的所有权并等待，直到其他线程通过调用 notify 方法，或 notifyAll 方法通知在此对象的监视器上等待的线程醒来。然后该线程将等到重新获得对监视器的所有权后才能继续执行。*/  
    public final void wait() throws InterruptedException {   
        wait(0L);
    }   

   /** 在其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者超过指定的时间量前，导致当前线程等待。*/  
    public final native void wait(long timeout) throws InterruptedException;   

    /** 在其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者其他某个线程中断当前线程，或者已超过某个实际时间量前，导致当前线程等待。*/  
    public final void wait(long timeoutMillis, int nanos) throws InterruptedException {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("timeoutMillis value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }

        if (nanos > 0) {
            timeoutMillis++;
        }

        wait(timeoutMillis);
    }  

    /**
    * 当垃圾回收器确定不存在对该对象的更多引用时，由对象的垃圾回收器调用此方法。
    * JDK9之后标记为已过期
    */  
    @Deprecated(since="9")
    protected void finalize() throws Throwable { } 
}  
```

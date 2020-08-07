package cn.delei.java.basic;

import cn.delei.PrintUtil;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 引用相关
 */
public class ReferenceDemo {
    public final static int M = 1024 * 1024;

    public static void main(String[] args) {
        // 设置启动类 JVM 参数-Xmx20M -Xms20M
        //strongReferenceTest();
        //softReferenceTest();
        //weakReferenceTest();
    }

    /**
     * 强引用 StrongReference 测试方法
     */
    private static void strongReferenceTest() {
        // 软引用 StrongReference
        PrintUtil.printTitle("强引用 StrongReference");
        PrintUtil.printDivider("初始可用内存和总内存");
        PrintUtil.printlnMemory(ReferenceDemo.M);

        // 创建强引用
        PrintUtil.printDivider("实例化10M的数组,并创建强引用");
        byte[] reference = new byte[10 * ReferenceDemo.M];
        PrintUtil.printlnMemory(ReferenceDemo.M);
        System.out.println("StrongReference.get() : " + reference);

        // 显式强GC
        PrintUtil.printDivider("GC后");
        System.gc();
        PrintUtil.printlnMemory(ReferenceDemo.M);
        System.out.println("StrongReference.get() : " + reference);

        // 强引用断开了
        reference = null;
        PrintUtil.printDivider("强引用断开后");
        PrintUtil.printlnMemory(ReferenceDemo.M);
        System.out.println("StrongReference.get() : " + reference);

        // 强引用断开了 GC
        PrintUtil.printDivider("强引用断开GC后");
        System.gc();
        PrintUtil.printlnMemory(ReferenceDemo.M);
        System.out.println("StrongReference.get() : " + reference);
    }

    /**
     * 软引用 SoftReference 测试方法
     */
    private static void softReferenceTest() {
        // 软引用 SoftReference
        PrintUtil.printTitle("软引用 SoftReference");
        PrintUtil.printDivider("初始可用内存和总内存");
        PrintUtil.printlnMemory(M);

        // 创建软引用
        PrintUtil.printDivider("实例化10M的数组,并创建软引用");
        SoftReference<Object> reference = new SoftReference<Object>(new byte[10 * ReferenceDemo.M]);
        PrintUtil.printlnMemory(M);
        System.out.println("SoftRerference.get() : " + reference.get());

        // 强制GC
        PrintUtil.printDivider("内存可用充足，GC后");
        System.gc();
        PrintUtil.printlnMemory(M);
        System.out.println("SoftRerference.get() : " + reference.get());

        // 实例化一个新10M的数组,使内存不够用,并建立软引用
        SoftReference<Object> reference2 = new SoftReference(new byte[10 * ReferenceDemo.M]);
        PrintUtil.printDivider("实例化一个新的10M的数组后");
        PrintUtil.printlnMemory(M);
        System.out.println("SoftRerference.get() : " + reference.get());
        System.out.println("SoftRerference2.get() : " + reference2.get());
    }

    /**
     * 软引用 SoftReference 测试方法
     */
    private static void weakReferenceTest() {
        // 软引用 SoftReference
        PrintUtil.printTitle("弱引用 WeakReference");
        PrintUtil.printDivider("初始可用内存和总内存");
        PrintUtil.printlnMemory(M);

        // 创建弱引用
        PrintUtil.printDivider("实例化10M的数组,并创建弱引用");
        WeakReference<Object> reference = new WeakReference<Object>(new byte[10 * ReferenceDemo.M]);
        PrintUtil.printlnMemory(M);
        System.out.println("WeakReference.get() : " + reference.get());

        // 显式强GC
        PrintUtil.printDivider("GC后");
        System.gc();
        PrintUtil.printlnMemory(M);
        System.out.println("WeakReference.get() : " + reference.get());
    }
}

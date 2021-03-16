/*
 * All rights Reserved, Designed By DataDriver
 * Copyright:    DataDriver.Inc
 * Company:      Zhuo Wo NetWork Technology (ShangHai) CO.LTD
 */
package cn.delei.java.lang;

public class ClassLoaderDemo {
    public static void main(String[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        //并没有获取到ExtClassLoader的父Loader
        //原因是BootstrapLoader(引导类加载器)是用C语言实现的，找不到一个确定的返回父Loader的方式，于是就返回null
        System.out.println(loader.getParent().getParent());
    }
}

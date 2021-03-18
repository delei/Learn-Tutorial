package cn.delei.java.lang;

import cn.delei.PrintUtil;
import cn.delei.java.lang.inner.InnerUser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionDemo {
    public static void main(String[] args) {
        try {
            PrintUtil.printTitle("Class名");
            System.out.printf("%-10s", "类名：");
            System.out.println(InnerUser.class);
            System.out.printf("%-10s", "对象：");
            System.out.println(new InnerUser().getClass());
            System.out.printf("%-10s", "Class Name：");
            System.out.println(Class.forName("cn.delei.java.lang.inner.InnerUser"));

            Class<InnerUser> innerUserClass = InnerUser.class;
            // 类名打印
            PrintUtil.printTitle("类名输出");
            System.out.println(innerUserClass.getName());
            System.out.println(innerUserClass.getSimpleName());
            System.out.println(innerUserClass.getCanonicalName());
            System.out.println(innerUserClass.getSuperclass());
            // 类字段属性
            PrintUtil.printTitle("类属性输出");
            for (Field f : innerUserClass.getDeclaredFields()) {
                System.out.println(f.getName() + ":" + f.getType());
            }
            PrintUtil.printDivider();
            for (Field f : innerUserClass.getFields()) {
                System.out.println(f.getName());
            }
            // 类构造
            Constructor<InnerUser> constructor=innerUserClass.getConstructor();
            // 类方法
            PrintUtil.printTitle("类方法输出");
            String methodName;
            for (Method method : innerUserClass.getDeclaredMethods()) {
                methodName = method.getName();
                System.out.println(methodName);
            }
            PrintUtil.printDivider();
            for (Method method : innerUserClass.getMethods()) {
                methodName = method.getName();
                System.out.println(methodName);
            }
            // 类Invoke
            PrintUtil.printTitle("类Invoke输出");
            InnerUser innerUser = innerUserClass.getDeclaredConstructor().newInstance();
            Method method = innerUserClass.getMethod("setName", String.class);
//            修改私有方法的访问标识
//            method.setAccessible(true);
            method.invoke(innerUser, "王某某");
            System.out.println("InnerUser setName():" + innerUser.getName());
            method = innerUserClass.getMethod("getName");
            String name = (String) method.invoke(innerUser);
            System.out.println("InnerUser getName():" + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

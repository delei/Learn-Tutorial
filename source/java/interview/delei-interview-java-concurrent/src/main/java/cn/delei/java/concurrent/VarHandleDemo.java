package cn.delei.java.concurrent;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * VarHandle 类
 *
 * @author deleiguo
 */
public class VarHandleDemo {

    private int age;

    public static void main(String[] args) {
        try {
            VarHandleDemo varHandleDemo = new VarHandleDemo();
            MethodHandles.Lookup l = MethodHandles.lookup();
            final VarHandle varHandle = l.findVarHandle(VarHandleDemo.class, "age", int.class);
            varHandle.set(varHandleDemo, 10);
            System.out.println(varHandleDemo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "VarHandleDemo{" +
                "age=" + age + '}';
    }
}

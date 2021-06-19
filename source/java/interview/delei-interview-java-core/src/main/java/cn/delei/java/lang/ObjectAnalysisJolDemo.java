package cn.delei.java.lang;

import cn.delei.util.PrintUtil;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.util.HashMap;

/**
 * 使用 OpenJDK Jol 进行对象布局分析
 * <p>http://zhongmingmao.me/2016/07/01/jvm-jol-tutorial-1/</p>
 *
 * @author deleiguo
 */
public class ObjectAnalysisJolDemo {
    public static void main(String[] args) {
        // 查看对象大小需要确认是否开启了指针压缩(UseCompressedOops参数),64位JVM 默认开启
        System.out.println(VM.current().details());
        classLayoutDemo();
        graphLayoutDemo();
    }

    /**
     * 对象内部信息
     */
    static void classLayoutDemo() {
        PrintUtil.printDivider("Class Layout");
        System.out.println(ClassLayout.parseClass(String.class).toPrintable());
        System.out.println(ClassLayout.parseInstance("AAAAA").toPrintable());
        System.out.println(ClassLayout.parseInstance("AAAAA".getBytes()).toPrintable());
        System.out.println(ClassLayout.parseInstance(Integer.class).toPrintable());
        System.out.println(ClassLayout.parseInstance(1234).toPrintable());

        String str = "AAAAA";
        ClassLayout layout = ClassLayout.parseInstance(str);
        System.out.println(layout.toPrintable());
        System.out.println("layout hashCode=" + Integer.toHexString(str.hashCode()));
        System.out.println(layout.toPrintable());
    }

    /**
     * 对象外部信息
     */
    static void graphLayoutDemo() {
        PrintUtil.printDivider("Graph Layout");
        HashMap hashMap = new HashMap();
        hashMap.put("key01", "AAAAAA");
        System.out.println(GraphLayout.parseInstance(hashMap).toPrintable());
        System.out.println("size = " + GraphLayout.parseInstance(hashMap).totalSize());
    }
}

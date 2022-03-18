package cn.delei.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class JVMTest {

    public static void main(String[] args) throws Exception {
        //CMSTest();
        //G1Test();
        //stackOverFlow();
        //outOfMemory();
    }

    static void CMSTest() {
        // -Xms1M -Xmx10M -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC
        test();
    }

    static void G1Test() {
        // -Xms1M -Xmx10M -XX:+PrintGCDetails -XX:+UseG1GC -XX:MaxGCPauseMillis=200
        test();
    }

    static void outOfMemory() {
        // -Xms1M -Xmx10M
        List<JVMTest> data = new ArrayList<>();
        //无限创建对象，在堆中
        while (true) {
            data.add(new JVMTest());
        }
    }

    static void stackOverflow() {
        // -Xms1M -Xmx10M -Xss1M
        stackOverflow();
    }

    static void test() {
        int size = 1024 * 1024;
        IntStream.range(0, 11).forEach(e -> {
            byte[] data = new byte[5 * size];
        });
    }
}

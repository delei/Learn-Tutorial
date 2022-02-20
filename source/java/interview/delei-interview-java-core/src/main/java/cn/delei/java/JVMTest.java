package cn.delei.java;

import java.util.stream.IntStream;

public class JVMTest {

    public static void main(String[] args) {
        //CMSTest();
        G1Test();
    }

    static void CMSTest() {
        // -Xms1M -Xmx10M -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC
        test();
    }

    static void G1Test() {
        // -Xms1M -Xmx10M -XX:+PrintGCDetails -XX:+UseG1GC -XX:MaxGCPauseMillis=200
        test();
    }

    static void test() {
        int size = 1024 * 1024;
        IntStream.range(0, 11).forEach(e -> {
            byte[] data = new byte[5 * size];
        });
    }
}

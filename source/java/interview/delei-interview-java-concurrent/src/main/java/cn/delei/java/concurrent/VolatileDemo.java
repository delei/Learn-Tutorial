package cn.delei.java.concurrent;

public class VolatileDemo {

    private static volatile boolean flag = false;

    public static void main(String[] args) throws Exception {

        Thread t01 = new Thread(() -> {
            int i = 0;
            while (!flag) {
                i++;
            }
            System.out.printf("T02: flag = %s , i = %s\n", flag, i);
        }, "T01");

        t01.start();
        Thread.sleep(1000);
        flag = true;
        System.out.printf("Main: flag = %s\n", flag);

    }
}

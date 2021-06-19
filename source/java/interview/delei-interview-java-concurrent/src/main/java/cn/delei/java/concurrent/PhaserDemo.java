package cn.delei.java.concurrent;

import cn.delei.util.SimulationUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Phaser;

/**
 * Phaser Demo 多阶段栅栏
 *
 * @author deleiguo
 */
public class PhaserDemo {
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm:ss");

    public static void main(String[] args) {
        demo01();
    }

    static void demo01() {
        final int n = 5;
        Phaser phaser = new Phaser();
        phaser.register();
        for (int i = 0; i < n; i++) {
            phaser.register();// 注册每个线程参与者
            new Thread(() -> {
                System.out.printf("==> %s\t[线程%s]\t Start\n", format.format(LocalTime.now()), Thread.currentThread().getName());
                try {
                    SimulationUtil.runtime(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                phaser.arriveAndAwaitAdvance(); // 等待其他的参与者到达
                System.out.printf("==> %s\t[线程%s]\t End\n", format.format(LocalTime.now()), Thread.currentThread().getName());
            }).start();
        }
        System.out.println("regester=" + phaser.getRegisteredParties()); // 获取参与者的数量
        phaser.arriveAndDeregister();
        System.out.println("==> " + format.format(LocalTime.now()) + " Main End!");
    }
}

package cn.delei.distributed.lock.jedis;

import cn.delei.util.PrintUtil;
import cn.hutool.core.lang.Assert;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Application 入口
 *
 * @author deleiguo
 */
@SpringBootApplication
public class JedisApplication {
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Main方式启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // Spring Boot 启动入口
        ApplicationContext context = SpringApplication.run(JedisApplication.class, args);
        jedisTest(context);
    }

    private static void jedisTest(ApplicationContext context) {
        JedisPool jedisPool = context.getBean("jedisPool", JedisPool.class);
        Assert.notNull(jedisPool);

        int size = 10;
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch countDownLatch = new CountDownLatch(size);

        String lockKey = "lock_order";
        String lockValue = 100 + "";
        for (int i = 0; i < size; i++) {
            new Thread(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.printf("==> %s\t%s:\t ready\n", format.format(LocalTime.now()), threadName);
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Jedis jedis = jedisPool.getResource();
                Assert.notNull(jedis);
                try {
                    // 获取锁
                    boolean resultFlag = JedisUtil.lock(jedis, lockKey,
                            lockValue,
                            20000,
                            5,
                            1000);
                    System.out.printf("==> %s\t%s:\t %s\n", format.format(LocalTime.now()), threadName, resultFlag);
                    if (resultFlag) {
                        // 如果成功获取锁，进行业务操作
                        TimeUnit.SECONDS.sleep(2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    JedisUtil.releaseLock(jedis, lockKey, lockValue);
                    jedis.close();
                    countDownLatch.countDown();
                }
            }, "DD-Thread-" + i).start();
        }
        try {
            TimeUnit.SECONDS.sleep(3);
            PrintUtil.printDivider();
            PrintUtil.printDivider(format.format(LocalTime.now()));
            startLatch.countDown();
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
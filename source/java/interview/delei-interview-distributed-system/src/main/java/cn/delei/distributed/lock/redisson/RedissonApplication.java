package cn.delei.distributed.lock.redisson;

import cn.delei.util.PrintUtil;
import cn.hutool.core.lang.Assert;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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
public class RedissonApplication {
    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Main方式启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // Spring Boot 启动入口
        ApplicationContext context = SpringApplication.run(RedissonApplication.class, args);
        redissonTest(context);
    }

    private static void redissonTest(ApplicationContext context) {
        RedissonClient redissonClient = context.getBean("redisson", RedissonClient.class);
        Assert.notNull(redissonClient);

        int size = 10;
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch countDownLatch = new CountDownLatch(size);

        String lockKey = "lock_order";
        for (int i = 0; i < size; i++) {
            new Thread(() -> {
                // 获取锁实例
                RLock rlock = redissonClient.getLock(lockKey);
                String threadName = Thread.currentThread().getName();
                System.out.printf("==> %s\t%s:\t ready\n", format.format(LocalTime.now()), threadName);
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    // 尝试获取锁
                    // 第一个参数：等待获取锁失败的时间
                    // 第二个参数：锁自动释放失效时间
                    boolean resultFlag = rlock.tryLock(20L, 5L, TimeUnit.SECONDS);
                    if (resultFlag) {
                        // 如果成功获取锁，进行业务操作
                        System.out.printf("==> %s\t%s:\t %s\n", format.format(LocalTime.now()), threadName, resultFlag);
                        TimeUnit.SECONDS.sleep(1L);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    rlock.unlock();
                    // 判断要解锁的key是否已被锁定
                    // 判断要解锁的key是否被当前线程持有
                    //if (rlock.isLocked() && rlock.isHeldByCurrentThread()) {
                    //    rlock.unlock();
                    //}
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
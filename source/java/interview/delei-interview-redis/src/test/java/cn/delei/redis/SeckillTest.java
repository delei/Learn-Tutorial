package cn.delei.redis;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SeckillTest {

    @Resource
    private IDistributedLocker jedisLock;
    @Resource
    private IDistributedLocker redissonLock;

    @Test
    public void jedisSeckill() throws Exception {

        // 设置库存
        jedisLock.setStock(20, TimeUnit.SECONDS, 600);

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            new Thread(new SeckillTaskRunnable(start, countDownLatch,
                    jedisLock, "UT-" + i)).start();
        }
        start.countDown();
        countDownLatch.await();
    }

    @Test
    public void setStockTest() {
        // 设置库存
        Assertions.assertTrue(redissonLock.setStock(20, TimeUnit.SECONDS, 600));
    }

    @Test
    public void redissonSeckill() throws Exception {
        // 设置库存
        redissonLock.setStock(20, TimeUnit.SECONDS, 600);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            new Thread(new SeckillTaskRunnable(start, countDownLatch,
                    redissonLock, "UT-" + i)).start();
        }
        start.countDown();
        countDownLatch.await();
    }

}


class SeckillTaskRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RedissonTaskRunnable.class);

    private CountDownLatch start;
    private CountDownLatch countDownLatch;
    private IDistributedLocker lock;
    private String user;

    public SeckillTaskRunnable(CountDownLatch start, CountDownLatch countDownLatch,
                               IDistributedLocker lock, String user) {
        this.start = start;
        this.countDownLatch = countDownLatch;
        this.lock = lock;
        this.user = user;
    }

    @Override
    public void run() {
        // 每人秒杀数量
        int amount = RandomUtil.randomInt(1, 3);
        try {
            start.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        boolean flag = false;
        Thread current = Thread.currentThread();
        do {
            String result = lock.seckill(amount);
            if ("1".equals(result)) {
                flag = true;
                log.info(StrUtil.format("==> {}\t {}\t 秒杀 {}"),
                        current.getId(), user, amount);
            } else if ("-1".equals(result)) {
                flag = true;
            }
        } while (!flag);
        countDownLatch.countDown();
    }
}

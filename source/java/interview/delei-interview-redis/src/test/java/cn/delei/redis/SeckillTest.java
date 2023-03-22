package cn.delei.redis;

import cn.delei.util.PrintUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀模拟测试
 *
 * @author deleiguo
 */
@SpringBootTest
public class SeckillTest {
    private static final Logger log = LoggerFactory.getLogger(SeckillTest.class);
    @Resource
    private IDistributedLocker jedisLock;
    @Resource
    private IDistributedLocker redissonLock;

    @Test
    public void jedisTest() throws Exception {
        long leaseTime = 60L;
        seckill(jedisLock, TimeUnit.SECONDS, leaseTime, 20, 10, true);
        seckill(jedisLock, TimeUnit.SECONDS, leaseTime, 6, 8, false);
    }

    @Test
    public void redissonTest() throws Exception {
        long leaseTime = 60L;
        seckill(redissonLock, TimeUnit.SECONDS, leaseTime, 20, 10, true);
        seckill(redissonLock, TimeUnit.SECONDS, leaseTime, 6, 8, false);
    }

    /**
     * 通用模拟方法
     *
     * @param lock      lock具体实现
     * @param timeUnit  key持续时间单位
     * @param leaseTime key持续时间
     * @param stock     库存总量
     * @param users     模拟用户量
     * @param isRandom  是否随机每个用户下单的数量
     */
    void seckill(IDistributedLocker lock, TimeUnit timeUnit, long leaseTime,
                 int stock, int users, boolean isRandom) throws InterruptedException {
        Assert.isTrue(stock > 0);
        Assert.isTrue(users > 0);
        if (isRandom) {
            Assert.isTrue(stock > 2);
        }

        PrintUtil.printTitle(StrUtil.format("stock:{}  users:{}", stock, users));
        // 设置库存
        lock.setStock(stock, timeUnit, leaseTime);

        // 控制同时开始
        CountDownLatch startLatch = new CountDownLatch(1);
        // 控制所有线程运行结束
        CountDownLatch endLatch = new CountDownLatch(users);

        for (int i = 1; i <= users; i++) {
            new Thread(new SeckillTaskRunnable(startLatch, endLatch, lock, "UT-" + i,
                    isRandom ? RandomUtil.randomInt(1, stock / 2) : 1)
            ).start();
        }
        startLatch.countDown();
        // 等待所有线程运行结束
        endLatch.await();
        TimeUnit.SECONDS.sleep(1L);
    }

    /**
     * 任务 Runnable
     */
    static class SeckillTaskRunnable implements Runnable {
        private CountDownLatch startLatch;
        private CountDownLatch endLatch;
        /**
         * 锁
         */
        private IDistributedLocker lock;
        /**
         * 用户
         */
        private String user;
        /**
         * 下单数量
         */
        private int amount;

        private SeckillTaskRunnable() {
        }

        public SeckillTaskRunnable(CountDownLatch startLatch, CountDownLatch endLatch,
                                   IDistributedLocker lock, String user, int amount) {
            Assert.notNull(startLatch);
            Assert.notNull(endLatch);
            Assert.notNull(lock);
            Assert.notBlank(user);
            Assert.isTrue(amount > 0);
            this.startLatch = startLatch;
            this.endLatch = endLatch;
            this.lock = lock;
            this.user = user;
            this.amount = amount;
        }

        @Override
        public void run() {
            try {
                // 等待放行，模拟并发
                startLatch.await();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            // 执行
            log.info(StrUtil.format("==> [ {} ] {}\t 秒杀 {} , 结果: {}"),
                    Thread.currentThread().getId(), user, amount, lock.seckill(amount));
            endLatch.countDown();
        }
    }
}


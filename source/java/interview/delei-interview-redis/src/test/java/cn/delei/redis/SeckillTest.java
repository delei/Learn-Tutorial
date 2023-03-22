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
        long leaseTime = 600L;
        seckill(jedisLock, TimeUnit.SECONDS, leaseTime, 20, 10, true);
        seckill(jedisLock, TimeUnit.SECONDS, leaseTime, 6, 8, false);
    }

    @Test
    public void redissonTest() throws Exception {
        long leaseTime = 600L;
        seckill(redissonLock, TimeUnit.SECONDS, leaseTime, 20, 10, true);
        seckill(redissonLock, TimeUnit.SECONDS, leaseTime, 6, 8, false);
    }

    /**
     * 通用模拟方法
     *
     * @param lock      lock具体实现
     * @param unit      单位
     * @param leaseTime key持续时间
     * @param stock     库存总量
     * @param users     模拟用户量
     * @param isRandom  是否随机每用户下单的数量
     * @throws Exception InterruptedException
     */
    private void seckill(IDistributedLocker lock, TimeUnit unit, long leaseTime, int stock,
                         int users, boolean isRandom) throws Exception {
        PrintUtil.printTitle(StrUtil.format("stock:{}  users:{}", stock, users));
        // 设置库存
        lock.setStock(stock, unit, leaseTime);

        // 控制同时开始
        CountDownLatch start = new CountDownLatch(1);
        // 控制所有线程运行结束
        CountDownLatch end = new CountDownLatch(users);
        int amount;
        for (int i = 1; i <= users; i++) {
            amount = isRandom ? RandomUtil.randomInt(1, stock / 2) : 1;
            new Thread(new SeckillTaskRunnable(start, end,
                    lock, "UT-" + i, amount)).start();
        }
        start.countDown();
        end.await();
        TimeUnit.SECONDS.sleep(1L);
    }
    
    /**
     * 任务 Runnable
     */
    static class SeckillTaskRunnable implements Runnable {
        private CountDownLatch start;
        private CountDownLatch end;
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

        public SeckillTaskRunnable(CountDownLatch start, CountDownLatch end,
                                   IDistributedLocker lock, String user, int amount) {
            Assert.notNull(start);
            Assert.notNull(lock);
            Assert.notBlank(user);
            Assert.isTrue(amount > 0);
            this.start = start;
            this.end = end;
            this.lock = lock;
            this.user = user;
            this.amount = amount;
        }

        @Override
        public void run() {
            try {
                // 等待放行，模拟并发
                start.await();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            log.info(StrUtil.format("==> [ {} ] {}\t 秒杀 {} , 结果: {}"),
                    Thread.currentThread().getId(), user, amount, lock.seckill(amount));
            end.countDown();
        }
    }
}


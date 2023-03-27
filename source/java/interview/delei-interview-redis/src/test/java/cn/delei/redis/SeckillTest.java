package cn.delei.redis;

import cn.delei.util.PrintUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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
    public void jedisTest() {
        long leaseTime = 60L;
        seckill(jedisLock, TimeUnit.SECONDS, leaseTime, 20, 10, true);
        seckill(jedisLock, TimeUnit.SECONDS, leaseTime, 6, 8, false);
    }

    @Test
    public void redissonTest() {
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
     * @param isRandom  true:随机每个用户下单的数量,false:为 1
     */
    void seckill(IDistributedLocker lock, TimeUnit timeUnit, long leaseTime,
                 int stock, int users, boolean isRandom) {
        Assert.isTrue(stock > 0);
        Assert.isTrue(users > 0);
        if (isRandom) {
            Assert.isTrue(stock > 2);
        }

        PrintUtil.printTitle(StrUtil.format("stock:{}  users:{}", stock, users));
        // 设置库存
        lock.setStock(stock, timeUnit, leaseTime);

        // 模拟并行
        ConcurrencyTester tester = ThreadUtil.concurrencyTest(users, () -> {
            int amount = isRandom ? RandomUtil.randomInt(1, stock / 2) : 1;
            // 执行
            log.info(StrUtil.format("==> {}\t 秒杀 {} , 结果: {}"),
                    Thread.currentThread().getId(), amount, lock.seckill(amount));
        });
        // 执行时间(毫秒)
        log.info(String.valueOf(tester.getInterval()));
    }
}


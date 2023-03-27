package cn.delei.redis;

import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedissonTest {
    private static final Logger log = LoggerFactory.getLogger(RedissonTest.class);
    @Resource
    private IDistributedLocker redissonLock;

    /**
     * 分布式锁
     */
    @Test
    public void lockTest() {
        // 固定 key
        String lockKey = "lock_order";
        long lockTime = 5L;

        // 模拟并行
        ConcurrencyTester tester = ThreadUtil.concurrencyTest(15, () -> {
            long tId = Thread.currentThread().getId();
            try {
                boolean flag;
                do {
                    flag = redissonLock.tryLock(lockKey, null, TimeUnit.SECONDS, lockTime);
                    if (flag) {
                        log.info(StrUtil.format("==> {}\t 得到了 lock"), tId);
                        // 模拟业务逻辑处理时间，必须小于 lockTime
                        try {
                            TimeUnit.SECONDS.sleep(lockTime - 3L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } while (!flag);
            } finally {
                redissonLock.unlock(lockKey, null);
            }
        });
        // 执行时间(毫秒)
        log.info(String.valueOf(tester.getInterval()));
    }

}
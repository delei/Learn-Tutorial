package cn.delei.redis;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedissonTest {
    private static final Logger log = LoggerFactory.getLogger(RedissonTest.class);
    @Resource
    private IDistributedLocker redissonLock;

    @Test
    public void lockTest() throws Exception {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            new Thread(new RedissonTaskRunnable(start, countDownLatch,
                    redissonLock, "UT-" + i)).start();
        }
        start.countDown();
        countDownLatch.await();
    }

    static class RedissonTaskRunnable implements Runnable {
        private CountDownLatch start;
        private CountDownLatch end;
        private IDistributedLocker lock;
        private String user;

        public RedissonTaskRunnable(CountDownLatch start, CountDownLatch end,
                                    IDistributedLocker lock, String user) {
            this.start = start;
            this.end = end;
            this.lock = lock;
            this.user = user;
        }

        @Override
        public void run() {
            String lockKey = "lock_order";
            long lockTime = 10L;
            try {
                start.await();
                boolean flag;
                Thread current = Thread.currentThread();
                do {
                    flag = lock.tryLock(lockKey, null, TimeUnit.SECONDS, lockTime);
                    if (flag) {
                        log.info(StrUtil.format("==> {}\t {}\t 得到了 lock"),
                                current.getId(), user);
                        // 模拟业务逻辑处理时间
                        try {
                            TimeUnit.SECONDS.sleep(lockTime - 5L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } while (!flag);
                end.countDown();
            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                lock.unlock(lockKey, null);
            }
        }
    }
}
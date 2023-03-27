package cn.delei.redis;

import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Jedis 测试
 *
 * @author deleiguo
 */
@SpringBootTest
public class JedisTest {
    private static final Logger log = LoggerFactory.getLogger(JedisTest.class);

    @Resource
    private JedisPool jedisPool;
    @Resource
    private IDistributedLocker jedisLock;

    /**
     * 常用操作
     */
    @Test
    public void jedisOperaTest() {
        Jedis jedis = jedisPool.getResource();
        String testKey = "delei";
        String testValue = "deleiguo";
        try (jedis) {
            jedis.flushDB();
            jedis.exists(testKey);

            jedis.set(testKey, testValue);
            jedis.get(testKey);
            jedis.expire(testKey, 10);
            jedis.del(testKey);
            jedis.type(testKey);

            // String
            jedis.append(testKey, testValue);
            jedis.setnx(testKey, testValue);

            // List
            jedis.flushDB();
            jedis.lpush(testKey, "a", "b", "c");
            jedis.rpush(testKey, "11", "22", "33");

            // Map
            jedis.flushDB();
            Map<String, String> map = new HashMap<>();
            map.put("aaaa", "111");
            map.put("bbbb", "222");
            map.put("cccc", "333");
            jedis.hmset(testKey, map);
            System.out.println(jedis.hkeys(testKey));
            System.out.println(jedis.hlen(testKey));

            // keys
            jedis.keys("de*");


        }
    }

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
                    flag = jedisLock.tryLock(lockKey, String.valueOf(tId), TimeUnit.SECONDS, lockTime);
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
                jedisLock.unlock(lockKey, String.valueOf(tId));
            }
        });
        // 执行时间(毫秒)
        log.info(String.valueOf(tester.getInterval()));
    }
}

package cn.delei.redis;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 限流测试
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

    @Test
    public void lockTest() {
        String lockKey = "jedis_lock_order";
        String lockValue = 100 + "";
        long lockTime = 5L;
        int retry = 3;

        // 构造用户
        List<String> users = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            users.add("UT-" + i);
        }

        // 模拟并行
        users.parallelStream().forEach(u -> {
            try {
                boolean flag;
                int count = 0;
                do {
                    count++;
                    flag = jedisLock.tryLock(lockKey, lockValue, TimeUnit.SECONDS, lockTime);
                    if (flag) {
                        log.info(StrUtil.format("==>{}\t {}\t 得到了 lock"),
                                Thread.currentThread().getId(), u);
                        // 模拟业务逻辑处理时间
                        try {
                            TimeUnit.SECONDS.sleep(lockTime - 1L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 每隔一段时间再重试
                        try {
                            TimeUnit.SECONDS.sleep(1L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } while (!flag && count <= retry);
            } finally {
                jedisLock.unlock(lockKey, lockValue);
            }
        });
    }
}

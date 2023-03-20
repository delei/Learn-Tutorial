package cn.delei.redis.jedis;

import cn.delei.redis.IDistributedLocker;
import cn.hutool.core.lang.Assert;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Jedis Lock
 */
@Service
public class JedisLock implements IDistributedLocker {
    @Resource
    private JedisPool jedisPool;

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    private Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        Assert.notNull(jedis);
        return jedis;
    }

    @Override
    public boolean tryLock(String lockKey, String lockValue, TimeUnit unit, long leaseTime) {
        Jedis jedis = getJedis();
        long time = unit.toMillis(leaseTime);
        SetParams setParams = new SetParams();
        setParams.nx();
        setParams.px(time);
        try (jedis) {
            return LOCK_SUCCESS.equals(jedis.set(lockKey, lockValue, setParams));
        }
    }

    @Override
    public boolean unlock(String lockKey, String lockValue) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return redis.call('del', KEYS[1]); else return 0 end;";
        Jedis jedis = getJedis();
        try (jedis) {
            return RELEASE_SUCCESS.equals(jedis.eval(script, Collections.singletonList(lockKey),
                    Collections.singletonList(lockValue)));
        }
    }
}

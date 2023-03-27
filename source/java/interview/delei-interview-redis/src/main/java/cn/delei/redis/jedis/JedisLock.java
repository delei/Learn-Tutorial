package cn.delei.redis.jedis;

import cn.delei.redis.IDistributedLocker;
import cn.hutool.core.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Jedis Lock
 *
 * @author deleiguo
 */
@Service
public class JedisLock implements IDistributedLocker {

    private static final Logger log = LoggerFactory.getLogger(JedisLock.class);
    @Resource
    private JedisPool jedisPool;

    private static final String SET_SUCCESS = "OK";
    private static final String KEY_PREFIX = "jedis_";
    private static final Long RELEASE_SUCCESS = 1L;

    private Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        Assert.notNull(jedis);
        return jedis;
    }

    @Override
    public String prefix() {
        return KEY_PREFIX;
    }

    @Override
    public boolean tryLock(String lockKey, String lockValue, TimeUnit unit, long leaseTime) {
        Assert.notBlank(lockKey);

        Jedis jedis = getJedis();
        // Set key value nx px
        try (jedis) {
            return SET_SUCCESS.equals(jedis.set(prefix() + lockKey, lockValue,
                    SetParams.setParams().nx().px(unit.toMillis(leaseTime))));
        }
    }

    @Override
    public boolean unlock(String lockKey, String lockValue) {
        Assert.notBlank(lockKey);
        // lua script
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return redis.call('del', KEYS[1]); else return 0 end;";
        Jedis jedis = getJedis();
        try (jedis) {
            return RELEASE_SUCCESS.equals(jedis.eval(script,
                    Collections.singletonList(prefix() + lockKey),
                    Collections.singletonList(lockValue)));
        }
    }

    @Override
    public boolean setStock(int amount, TimeUnit unit, long leaseTime) {
        Assert.isTrue(amount > 0);
        Jedis jedis = getJedis();
        try (jedis) {
            jedis.del(prefix() + SECKILL_KEY);
            String result = jedis.set(prefix() + SECKILL_KEY, String.valueOf(amount),
                    SetParams.setParams().nx().px(unit.toMillis(leaseTime)));
            return SET_SUCCESS.equals(result);
        }
    }

    @Override
    public String seckill(int amount) {
        Assert.isTrue(amount > 0);
        List<String> keys = new ArrayList<>();
        keys.add(prefix() + SECKILL_KEY);
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(amount));
        // 执行 lua 文件
        Jedis jedis = getJedis();
        try (jedis) {
            return String.valueOf(jedis.eval(loadSeckillScript(), keys, args));
        }
    }
}

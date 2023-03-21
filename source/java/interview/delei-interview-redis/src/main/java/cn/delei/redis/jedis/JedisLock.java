package cn.delei.redis.jedis;

import cn.delei.redis.IDistributedLocker;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
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
        Assert.notNull(lockKey);
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
        Assert.notNull(lockKey);
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return redis.call('del', KEYS[1]); else return 0 end;";
        Jedis jedis = getJedis();
        try (jedis) {
            return RELEASE_SUCCESS.equals(jedis.eval(script, Collections.singletonList(lockKey),
                    Collections.singletonList(lockValue)));
        }
    }

    @Override
    public boolean setStock(int amount, TimeUnit unit, long leaseTime) {
        Assert.isTrue(amount > 0);
        Jedis jedis = getJedis();
        try (jedis) {
            long time = unit.toMillis(leaseTime);
            String result = jedis.set(SECKILL_KEY, String.valueOf(amount),
                    SetParams.setParams().nx().px(time));
            return "OK".equals(result);
        }
    }

    @Override
    public String seckill(int amount) {
        Assert.isTrue(amount > 0);
        // 读取 lua 文件
        String scripts = ResourceUtil.readUtf8Str(SECKILL_SCRIPT);
        List<String> keys = new ArrayList<>();
        keys.add(SECKILL_KEY);
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(amount));
        // 执行
        Jedis jedis = getJedis();
        try (jedis) {
            return String.valueOf(jedis.eval(scripts, keys, args));
        }
    }
}

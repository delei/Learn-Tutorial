package cn.delei.redis.redisson;

import cn.delei.redis.IDistributedLocker;
import cn.hutool.core.lang.Assert;
import org.redisson.api.RLock;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Redisson Lock
 *
 * @author deleiguo
 */
@Service
public class RedissonLock implements IDistributedLocker {
    private static final Logger log = LoggerFactory.getLogger(RedissonLock.class);

    private static final String KEY_PREFIX = "redisson_";
    @Resource
    private RedissonClient redissonClient;

    @Override
    public String prefix() {
        return KEY_PREFIX;
    }

    @Override
    public boolean tryLock(String lockKey, String lockValue, TimeUnit unit, long leaseTime) {
        Assert.notBlank(lockKey);
        RLock lock = redissonClient.getLock(prefix() + lockKey);
        try {
            return lock.tryLock(20L, leaseTime, unit);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean unlock(String lockKey, String lockValue) {
        Assert.notBlank(lockKey);
        redissonClient.getLock(prefix() + lockKey).unlock();
        return true;
    }

    @Override
    public boolean setStock(int amount, TimeUnit unit, long leaseTime) {
        Assert.isTrue(amount > 0);
        redissonClient.getBucket(prefix() + SECKILL_KEY).set(amount, leaseTime, unit);
        return true;
    }

    @Override
    public String seckill(int amount) {
        Assert.isTrue(amount > 0);
        // 执行 lua 文件
        RScript rScript = redissonClient.getScript();
        return rScript.evalSha(RScript.Mode.READ_WRITE,
                rScript.scriptLoad(loadSeckillScript()),
                RScript.ReturnType.VALUE,
                Collections.singletonList(prefix() + SECKILL_KEY),
                String.valueOf(amount));
    }
}

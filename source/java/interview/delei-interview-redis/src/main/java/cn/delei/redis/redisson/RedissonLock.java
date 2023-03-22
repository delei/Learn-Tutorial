package cn.delei.redis.redisson;

import cn.delei.redis.IDistributedLocker;
import cn.hutool.core.lang.Assert;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
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
            e.printStackTrace();
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
        RBucket<Integer> bucket = redissonClient.getBucket(prefix() + SECKILL_KEY);
        bucket.set(amount, leaseTime, unit);
        return true;
    }

    @Override
    public String seckill(int amount) {
        Assert.isTrue(amount > 0);
        // 执行 lua 文件
        RScript rScript = redissonClient.getScript();
        String sha1 = rScript.scriptLoad(loadSeckillScript());
        return rScript.evalSha(RScript.Mode.READ_WRITE, sha1, RScript.ReturnType.VALUE,
                Collections.singletonList(prefix() + SECKILL_KEY),
                String.valueOf(amount));
    }
}

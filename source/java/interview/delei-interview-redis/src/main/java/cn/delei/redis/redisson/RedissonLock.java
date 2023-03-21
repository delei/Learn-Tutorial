package cn.delei.redis.redisson;

import cn.delei.redis.IDistributedLocker;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
public class RedissonLock implements IDistributedLocker {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public boolean tryLock(String lockKey, String lockValue, TimeUnit unit, long leaseTime) {
        Assert.notNull(lockKey);
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(20L, leaseTime, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unlock(String lockKey, String lockValue) {
        Assert.notNull(lockKey);
        redissonClient.getLock(lockKey).unlock();
        return true;
    }

    @Override
    public boolean setStock(int amount, TimeUnit unit, long leaseTime) {
        Assert.isTrue(amount > 0);
        RBucket<Integer> bucket = redissonClient.getBucket(SECKILL_KEY);
        bucket.set(amount, leaseTime, unit);
        return bucket.get() == amount;
    }

    @Override
    public String seckill(int amount) {
        Assert.isTrue(amount > 0);
        // 读取 lua 文件
        String scripts = ResourceUtil.readUtf8Str(SECKILL_SCRIPT);
        RScript rScript = redissonClient.getScript();
        String sha1 = rScript.scriptLoad(scripts);
        Long result = rScript.evalSha(RScript.Mode.READ_WRITE, sha1, RScript.ReturnType.VALUE,
                Collections.singletonList(SECKILL_KEY),
                String.valueOf(amount));
        return "ok";
    }
}

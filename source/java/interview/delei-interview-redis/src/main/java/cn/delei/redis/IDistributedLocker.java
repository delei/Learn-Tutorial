package cn.delei.redis;

import java.util.concurrent.TimeUnit;

public interface IDistributedLocker {

    boolean tryLock(String lockKey, String lockValue, TimeUnit unit, long leaseTime);

    boolean unlock(String lockKey, String lockValue);
}

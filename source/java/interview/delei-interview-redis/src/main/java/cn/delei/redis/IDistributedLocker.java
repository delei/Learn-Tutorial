package cn.delei.redis;

import java.util.concurrent.TimeUnit;

public interface IDistributedLocker {

    /**
     * lua 脚本（src/resources/）
     */
    String SECKILL_SCRIPT = "seckill.lua";
    String SECKILL_KEY = "seckill";

    boolean tryLock(String lockKey, String lockValue, TimeUnit unit, long leaseTime);

    boolean unlock(String lockKey, String lockValue);

    boolean setStock(int amount, TimeUnit unit, long leaseTime);

    String seckill(int amount);
}

package cn.delei.redis;

import cn.hutool.core.io.resource.ResourceUtil;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 *
 * @author deleiguo
 */
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

    default String prefix() {
        return "";
    }

    default String loadSeckillScript() {
        return ResourceUtil.readUtf8Str(SECKILL_SCRIPT);
    }
}

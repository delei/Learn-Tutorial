package cn.delei.distributed.lock.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Jedis 操作工具类
 *
 * @author deleiguo
 */
public class JedisUtil {

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 加锁(指定最大尝试次数范围内)
     *
     * @param lockKey     锁
     * @param lockValue   锁标识
     * @param expireTime  PX超期时间
     * @param tryTimes    最大尝试次数
     * @param sleepMillis Sleep时间
     * @return 是否获取成功
     */
    public static boolean lock(Jedis jedis, String lockKey, String lockValue, int expireTime, int tryTimes,
                               long sleepMillis) {
        boolean result;
        int count = 0;
        do {
            count++;
            result = tryLock(jedis, lockKey, lockValue, expireTime);
            try {
                TimeUnit.MILLISECONDS.sleep(sleepMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!result && count <= tryTimes);
        return result;
    }

    /**
     * 单次尝试获取分布式锁(SET NX PX)
     *
     * @param jedis      Redis客户端
     * @param lockKey    锁
     * @param lockValue  锁值
     * @param expireTime PX超期时间
     * @return 是否获取成功
     */
    public static boolean tryLock(Jedis jedis, String lockKey, String lockValue, int expireTime) {
        SetParams setParams = new SetParams();
        setParams.nx();
        setParams.px(expireTime);
        String result = null;
        try (jedis) {
            result = jedis.set(lockKey, lockValue, setParams);
        }
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     * <p>使用 Lua 脚本，通过 Redis 的 eval/evalsha 命令来运行</p>
     *
     * @param jedis     Redis客户端
     * @param lockKey   锁
     * @param lockValue 锁值
     * @return 是否释放成功
     */
    public static boolean releaseLock(Jedis jedis, String lockKey, String lockValue) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockValue));
        return RELEASE_SUCCESS.equals(result);
    }

    public static String getValue(Jedis jedis, String lockKey) {
        return jedis.get(lockKey);
    }
}

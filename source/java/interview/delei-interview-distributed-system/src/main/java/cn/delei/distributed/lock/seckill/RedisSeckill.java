package cn.delei.distributed.lock.seckill;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis秒杀（主要是减库存）
 *
 * @author deleiguo
 */
public class RedisSeckill {
    /**
     * redis key
     */
    private final static String SECKILL_KEY = "seckill";
    /**
     * 过期时间
     */
    private final static int EXPIRE_SECONDS = 600;
    /**
     * lua 脚本（src/resources/）
     */
    private final static String SECKILL_SCRIPT = "seckill.lua";

    /**
     * 设置库存
     *
     * @param jedis jedis客户端
     * @param stock 库存数量
     * @return boolean true|false
     */
    public static boolean setStock(Jedis jedis, int stock) {
        Assert.notNull(jedis);
        Assert.isTrue(stock > 0);
        
        jedis.del(SECKILL_KEY);
        String result = jedis.set(SECKILL_KEY, String.valueOf(stock),
                SetParams.setParams().nx().ex(EXPIRE_SECONDS));
        return "OK".equals(result);
    }

    /**
     * 秒杀减库存
     *
     * @param jedis  jedis客户端
     * @param amount 每次减少的数量
     * @return String 执行结果
     */
    public static String seckill(Jedis jedis, int amount) {
        Assert.notNull(jedis);
        Assert.isTrue(amount > 0);
        // 读取 lua 文件
        String scripts = ResourceUtil.readUtf8Str(SECKILL_SCRIPT);
        List<String> keys = new ArrayList<>();
        keys.add(SECKILL_KEY);
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(amount));
        // 执行
        return String.valueOf(jedis.eval(scripts, keys, args));
    }
}

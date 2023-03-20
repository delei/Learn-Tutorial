package cn.delei.distributed.lock.seckill;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Application 入口
 *
 * @author deleiguo
 */
@SpringBootApplication
public class SeckillApplication {
    private static final Log logger = LogFactory.get();

    /**
     * Main方式启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // Spring Boot 启动入口
        ApplicationContext context = SpringApplication.run(SeckillApplication.class, args);
        seckillTest(context);
    }

    private static void seckillTest(ApplicationContext context) {
        JedisPool jedisPool = context.getBean("jedisPool", JedisPool.class);
        Assert.notNull(jedisPool);

        // 设置库存
        int stock = 50;
        try (Jedis jedis = jedisPool.getResource()) {
            if (jedis != null) {
                RedisSeckill.setStock(jedis, stock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 构造用户
        List<String> users = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            users.add("UT-" + i);
        }

        // 模拟并发
        users.parallelStream().forEach(b -> {
            Jedis jedisClient = null;
            try {
                jedisClient = jedisPool.getResource();
                if (jedisClient != null) {
                    // 每人秒杀数量
                    int amount = RandomUtil.randomInt(1, 3);
                    String result = RedisSeckill.seckill(jedisClient, amount);
                    if ("1".equals(result)) {
                        System.err.println(StrUtil.format("{} 秒杀 {} 成功", b, amount));
                    }
                }
            } catch (Exception e) {
                // e.printStackTrace();
            } finally {
                if (jedisClient != null) {
                    jedisClient.close();
                }
            }
        });
    }

}

package cn.delei.redis.jedis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * Jedis Configuration
 *
 * @author deleiguo
 */
@Configuration
public class JedisAutoConfiguration {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    @ConditionalOnMissingBean(JedisPool.class)
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        jedisPoolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());
        jedisPoolConfig.setTestOnCreate(false);
        jedisPoolConfig.setTestOnBorrow(false);
        return new JedisPool(jedisPoolConfig,
                redisProperties.getHost(),
                redisProperties.getPort(),
                redisProperties.getTimeout().toMillisPart(),
                redisProperties.getPassword(),
                redisProperties.getDatabase());
    }
}

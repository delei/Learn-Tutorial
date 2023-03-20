package cn.delei.redis.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisAutoConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    @ConditionalOnMissingBean(JedisPool.class)
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
        jedisPoolConfig.setTestOnCreate(false);
        jedisPoolConfig.setTestOnBorrow(true);
        return new JedisPool(jedisPoolConfig,
                redisProperties.getHost(),
                redisProperties.getPort(),
                redisProperties.getTimeout().toMillisPart(),
                redisProperties.getPassword(),
                redisProperties.getDatabase());
    }
}

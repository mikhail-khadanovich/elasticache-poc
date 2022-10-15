package com.vanguard.elasticcache.poc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.redis.hostname}")
    private String redisHostName;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.prefix}")
    private String redisPrefix;



    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        List<String> clusterNodes = Arrays.asList(redisHostName + ":" + redisPort);
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterNodes);
        redisClusterConfiguration.setPassword("FiXWVSsPAJmA1y4c6E63pc9p4YgM4m0lJzTVeRZD0hyJTiYPqZD5YkToDxBHX9N2APQqhnqVXYNipIkErKByFD9Gx38rbF5vE80z28u2v9NGD9vZz6GtgnElS1yqAfMk");

        JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder().useSsl().build();
        return new JedisConnectionFactory(redisClusterConfiguration, jedisConfig);
    }

    @Bean(value = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Primary
    @Bean(name = "cacheManager") // Default cache manager is infinite
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().prefixKeysWith(redisPrefix)).build();
    }

    @Bean(name = "cacheManager10Seconds")
    public CacheManager cacheManager1Hour(RedisConnectionFactory redisConnectionFactory) {
        Duration expiration = Duration.ofSeconds(10);
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().prefixKeysWith(redisPrefix).entryTtl(expiration)).build();
    }

}

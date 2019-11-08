package com.example.dbms.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id; // cache instance id
    private RedisTemplate redisTemplate;

    private static final long EXPIRE_TIME_IN_MINUTES = 30; // redis过期时间

    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }




    @Override
    @SuppressWarnings("unchecked")
    public void putObject(Object key, Object value) {
        try {
            RedisTemplate redisTemplate = getRedisTemplate();
            ValueOperations opsForValue = redisTemplate.opsForValue();
            opsForValue.set(key, value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
            logger.debug("Put query result to redis");
        }
        catch (Throwable t)
        {
            logger.error("Redis put failed", t);
        }
    }




    @Override
    public Object getObject(Object key) {
        try {
            RedisTemplate redisTemplate = getRedisTemplate();
            ValueOperations opsForValue = redisTemplate.opsForValue();
            logger.debug("Get cached query result from redis");
            return opsForValue.get(key);
        }
        catch (Throwable t) {
            logger.error("Redis get failed, fail over to db", t);
            return null;
        }
    }




    @Override
    @SuppressWarnings("unchecked")
    public Object removeObject(Object key) {
        try {
            RedisTemplate redisTemplate = getRedisTemplate();
            redisTemplate.delete(key);
            logger.debug("Remove cached query result from redis");
        }
        catch (Throwable t) {
            logger.error("Redis remove failed", t);
        }
        return null;
    }




    @Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
        logger.debug("Clear all the cached query result from redis");
    }




    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
           /* RedisSerializer stringSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(stringSerializer);
            redisTemplate.setValueSerializer(stringSerializer);
            redisTemplate.setHashKeySerializer(stringSerializer);
            redisTemplate.setHashValueSerializer(stringSerializer);*/
            //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）

            /*Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
            ObjectMapper om = new ObjectMapper();
            // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
            om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
            om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            jacksonSeial.setObjectMapper(om);
            // 值采用json序列化
            redisTemplate.setValueSerializer(jacksonSeial);
            //使用StringRedisSerializer来序列化和反序列化redis的key值
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            // 设置hash key 和value序列化模式
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashValueSerializer(jacksonSeial);
            redisTemplate.afterPropertiesSet();*/
        }
        return redisTemplate;
    }

}

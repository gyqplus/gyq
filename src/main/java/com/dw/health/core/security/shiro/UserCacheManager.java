package com.dw.health.core.security.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.ConcurrentHashMap;

public class UserCacheManager implements CacheManager {
    private final ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>();
    // 序列化相关的一些设置
    // 过期时间
    private int expire;
    // redis key的前缀
    private String keyPrefix;
    // redisTemplate
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache cache = caches.get(name);
        if (cache == null){
            cache = new UserRedisCache<K,V>(redisTemplate,keyPrefix + name + ":", expire);
            caches.put(name,cache);
        }
        return cache;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}

package com.dw.health.core.security.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class UserRedisCache<K,V> implements Cache<K,V> {
    private RedisTemplate<String, Object> redisTemplate;
    private String keyPrefix;
    private Integer expire;

    public UserRedisCache(RedisTemplate<String, Object> redisTemplate, String keyPrefix, int expire) {
        this.redisTemplate = redisTemplate;
        this.keyPrefix = keyPrefix;
        this.expire = expire;
    }

    public UserRedisCache() {
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据key获取缓存中的值
     * @param key
     * @return
     * @throws CacheException
     */
    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {
        System.out.println("-----------从缓存中获取-----------");
        if (key == null){
            return null;
        }
        V value  = (V) redisTemplate.opsForValue().get(keyPrefix + key);
        System.out.println(value);
        return value;
    }

    /**
     * 往缓存中放入 key-value，返回缓存中之前的值
     * @param key
     * @param value
     * @return
     * @throws CacheException
     */
    @Override
    @SuppressWarnings("unchecked")
    public V put(K key, V value) throws CacheException {
        System.out.println("------放入缓存中,并返回之前的值-----");
        V preValue = (V) redisTemplate.opsForValue().get(keyPrefix + key);
        redisTemplate.opsForValue().set(keyPrefix + key, value, expire, TimeUnit.SECONDS);
        return preValue;
    }

    /**
     * 移除缓存中key对应的值，并返回该值
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    @SuppressWarnings("unchecked")
    public V remove(K key) throws CacheException {
        System.out.println("-------移除缓存中key对应的值--------------");
        V value = (V) redisTemplate.opsForValue().get(keyPrefix + key);
        redisTemplate.opsForValue().getOperations().delete(keyPrefix + key);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keyPrefix+"*");
    }

    @Override
    @SuppressWarnings("unchecked")
    public int size() {
        Set<K> keys = (Set<K>)redisTemplate.keys(keyPrefix + "*");
        return keys.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<K> keys() {
        Set<K> keys = (Set<K>)redisTemplate.keys(keyPrefix + "*");
        return !keys.isEmpty() ? Collections.unmodifiableSet(keys) : Collections.emptySet();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<V> values() {
        Collection<V> values = new HashSet<>();
        Set<K> keys = (Set<K>)redisTemplate.keys(keyPrefix + "*");
        for (K key : keys) {
            V value = (V) redisTemplate.opsForValue().get(key);
            values.add(value);
        }
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableCollection(values);
        }
    }
}

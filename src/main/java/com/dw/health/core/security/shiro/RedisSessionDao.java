package com.dw.health.core.security.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qjf
 * @Date: 2019/3/30
 */
public class RedisSessionDao extends AbstractSessionDAO {
    private RedisTemplate<String, Object> redisTemplate;
    private String keyPrefix;
    private Integer expire;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    /**
     * 新增session，将session存到redis中
     *      key: sessionId
     *      value: session
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        if (session == null){
            throw new UnknownSessionException("session不能为空");
        }
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(keyPrefix + sessionId, session, expire, TimeUnit.SECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null){
            return null;
        }
        Session session = (Session)redisTemplate.opsForValue().get(keyPrefix + sessionId);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null){
            throw new NullPointerException("session或sessionId不能为空");
        }
        redisTemplate.opsForValue().set(keyPrefix + session.getId(), session, expire, TimeUnit.SECONDS);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            throw new NullPointerException("session或sessionId不能为空");
        }
        redisTemplate.opsForValue().getOperations().delete(keyPrefix+session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Collection<Session> values = new HashSet<>();
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        for (String key : keys) {
            Session value = (Session) redisTemplate.opsForValue().get(key);
            values.add(value);
        }
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableCollection(values);
        }
    }
}

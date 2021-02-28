package cn.error0.common.shiro;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;

public class RedisSessionDAO extends AbstractSessionDAO {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(String.valueOf(sessionId), session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null)
            throw new NullPointerException("id argument cannot be null.");
        Session session = (Session) redisTemplate.opsForValue().get(sessionId);
        return session;
    }


    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null)
            return;
        Serializable sessionId = session.getId();
        if (sessionId == null)
            return;
        redisTemplate.opsForValue().set(sessionId, session);
    }

    @Override
    public void delete(Session session) {
        if (session == null)
            return;
        Serializable sessionId = session.getId();
        if (sessionId == null)
            return;
        redisTemplate.delete(sessionId);
    }

    @Override
    public Collection<Session> getActiveSessions() {

        Collection<Session> values = (Collection<Session>) redisTemplate.opsForValue().get("*");
        return values;
    }
}

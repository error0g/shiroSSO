package cn.error0.common.config;



import cn.error0.common.tools.MyRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class redisConfig {
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        // 设置反序列化器
        template.setKeySerializer( getKeySerializer() );
        template.setValueSerializer( getValueSerializer() );
        return template;
    }

    // key 采用String序列化器
    private RedisSerializer<String> getKeySerializer() {
        return new StringRedisSerializer();
    }

    // value 采用字节码序列化器
    private RedisSerializer getValueSerializer() {
        return new MyRedisSerializer();
    }

}

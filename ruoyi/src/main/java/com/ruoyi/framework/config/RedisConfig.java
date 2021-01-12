package com.ruoyi.framework.config;

import com.ruoyi.common.enums.RedisPublishClassify;
import com.ruoyi.framework.redis.Receiver;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * redis配置
 * 
 * @author ruoyi
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport
{


    @Bean(name = "redisTemplate")
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory)
    {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 系统消息适配器
     * @param receiver
     * @return
     */
    @Bean(name = "systemAdapter")
    public MessageListenerAdapter systemAdapter(Receiver receiver){
        //指定类中回调接收消息的方法
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "systemMessage");
        //adapter.afterPropertiesSet();
        return adapter;
    }

    /**
     * 短信消息适配器
     * @param receiver
     * @return
     */
    @Bean(name = "smsAdapter")
    public MessageListenerAdapter smsAdapter(Receiver receiver){
        //指定类中回调接收消息的方法
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "smsMessage");
        //adapter.afterPropertiesSet();
        return adapter;
    }


    /**
     * 日志消息适配器
     * @param receiver
     * @return
     */
    @Bean(name = "logAdapter")
    public MessageListenerAdapter logAdapter(Receiver receiver){
        //指定类中回调接收消息的方法
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "logMessage");
        //adapter.afterPropertiesSet();
        return adapter;
    }

    /**
     * 构建redis消息监听器容器
     * @param connectionFactory
     * @param systemAdapter
     * @param smsAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter systemAdapter, MessageListenerAdapter smsAdapter,MessageListenerAdapter logAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //指定不同的方法监听不同的频道
        container.addMessageListener(systemAdapter, new PatternTopic("system"));
        container.addMessageListener(smsAdapter, new PatternTopic("sms"));
        container.addMessageListener(logAdapter, new PatternTopic("log"));
        return container;
    }
}

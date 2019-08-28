package com.example.demo.config;

import com.example.demo.subscirbe.MessageReceiver;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // private RedisTemplate redisTemplate;

    /**
     * redis消息监听器容器
     * @param factory
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory, MessageListenerAdapter adapter1,MessageListenerAdapter adapter2){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        // 订阅了一个叫 laowang 的通道，container可以添加多个messageListener
        container.addMessageListener(adapter1,new PatternTopic("laowang"));
        // 订阅多个通道
        // container.addMessageListener(adapter2,new PatternTopic("laowang1"));
        return container;
    }

    /**
     * 绑定频道和处理消息的pojo
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(MessageReceiver receiver){
        // 绑定实体类与通道的关联关系
        return new MessageListenerAdapter(receiver,"receiverMessage");
    }

    /* 定义之后，MessageListenerAdapter 报错，提示单个通道存在多个监听，redis发布订阅较为少用，不研究
    @Bean
    public MessageListenerAdapter listenerAdapter1(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiverMessage1");
    }*/


    /**
     * 转换redis Key，value的格式
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则 (解决存储后的键出现 \x  十六进制编码)
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}

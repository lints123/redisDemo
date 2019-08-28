package com.example.demo.common;

import ch.qos.logback.core.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

public abstract class BaseCacheService {

    private static Logger logger = LoggerFactory.getLogger(BaseCacheService.class);

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public ValueOperations<String, Object> getOperations(){
        return redisTemplate.opsForValue();
    }

    /**
    * 保存数据到redis中。
    * @author lints
    * @date 2019-08-23
    * @return true
    */
    public boolean saveKV(String key, Object value, Integer time, TimeUnit timeUtil) {
        boolean rtn = false;
        if (StringUtils.isEmpty(key) || value == null) {
            logger.info("lints >>> 保存数据到redis出错，key或value为空，key = [{}],value = [{}]", key, value);
            return false;
        }
        try {
            logger.info("lints >>> 保存数据到redis，key=[{}]", key);
            key = new String(key.getBytes(),"utf-8");
            if (time == null && timeUtil == null) {
                getOperations().set(key, value);

            } else if (time != null && timeUtil == null) {
                // 默认分钟
                getOperations().set(key, value, time, TimeUnit.MINUTES);

            } else {
                getOperations().set(key, value, time, timeUtil);

            }
            rtn = true;
        } catch (Exception e) {
            logger.info("lints >>> 保存数据到redis出错，key = [{}],message = [{}]", key, e.getMessage());
            return false;
        }
        return rtn;
    }

    /**
    * 根据key获取redis存储的数据
    * @author lints
    * @date 2019-08-23
    */
    public Object get(String key) {

        try {
            return getOperations().get(key);

        } catch (Exception e) {
            logger.info("lints >>> 保存数据到redis出错，key = [{}],message = [{}]", key, e.getMessage());
        }
        return null;
    }

    public boolean deleteKV(String key) {
        if (key == null){
            return false;
        }
        logger.info("lints >>> 删除redis中的数据，key = [{}]", key);
        try{
            return redisTemplate.delete(key);
        }catch (Exception e){
            logger.info("lints >>> 删除redis中的数据出错，key = [{}],message = [{}]", key, e.getMessage());
            return false;
        }
    }


}

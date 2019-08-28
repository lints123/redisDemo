package com.example.demo.service.impl;

import com.example.demo.common.BaseCacheService;
import com.example.demo.common.RedisCacheService;
import com.example.demo.constants.RedisConstants;
import com.example.demo.service.HelloService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

@Service("helloService")
public class HelloServiceImpl implements HelloService {


    @Resource
    private RedisCacheService redisCacheService;

    @Override
    public String sayHello() {

        redisCacheService.saveKV(RedisConstants.helloKey, "123", 2, null);
        System.out.println("成功");
        System.out.println("lints >>> 缓存中读取的数据为：" + redisCacheService.get(RedisConstants.helloKey));
        System.out.println("lints >>> 删除redis中的数据,结果：" + redisCacheService.deleteKV(RedisConstants.helloKey));
        return "成功";
    }
}

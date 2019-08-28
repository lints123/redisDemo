package com.example.demo;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

    @LocalServerPort
    private String port;

    private URL url;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Before
    public void setUp() throws MalformedURLException {

        String url = String.format("http://localhost:%d/redisDemo",Integer.valueOf(port));
        System.out.println(String.format("prot is : [%d]",Integer.valueOf(port)));
        this.url = new URL(url);
    }

    @Test
    public void contextLoads() {

        // 简单的set，get，del 操作
        // this.restTemplate.getForEntity(this.url.toString() + "/hello/sayHello", String.class, "");

        // redis 订阅消息与处理消息
        this.stringRedisTemplate.convertAndSend("laowang", String.valueOf(Math.random()));

    }

}

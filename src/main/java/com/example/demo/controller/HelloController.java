package com.example.demo.controller;

import com.example.demo.config.SysConfig;
import com.example.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "hello")
public class HelloController {

    @Resource
    private SysConfig sysConfig;

    @Resource
    private HelloService helloService;

    @RequestMapping(value = "sayHello",method = RequestMethod.GET)
    public void sayHello(){

        System.out.println(sysConfig.getName());
        helloService.sayHello();

    }


}

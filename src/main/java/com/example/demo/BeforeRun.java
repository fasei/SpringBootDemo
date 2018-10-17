package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.util.OutputUtil;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Author: wangchao
 * Time: 2018-10-17
 * Description: This is
 */
public class BeforeRun implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        OutputUtil.log("我第一个启动的！！！");
    }
}

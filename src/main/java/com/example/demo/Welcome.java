package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.util.OutputUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Author: wangchao
 * Time: 2018-10-17
 * Description: This is 服务启动时，同时执行的操作
 */
@Component
public class Welcome implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                OutputUtil.log("服务启动完成！");
                OutputUtil.d("swagger2 url : http://localhost:20888/swagger-ui.html?baseUrl");
                OutputUtil.d("日志管理系统 url : http://127.0.0.1:5601/app/home#/");
            }
        }).start();

    }
}

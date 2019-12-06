package com.example.demo;

import com.example.demo.util.OutputUtil;
import com.example.demo.websocket.message.MessageCenter;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Properties;

//@EnableTransactionManagement  //开启事务管理
//此处加了MapperScan，单独的mapper类就不用添加了
@MapperScan("com.example.demo.mapper")
@EnableSwagger2 //允许使用swagger2
@SpringBootApplication
@EnableAsync
public class DemoApplication {

    public static void main(String[] args) {
        OutputUtil.d("启动服务");
        MessageCenter.init();
        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.addInitializers(new BeforeRun());
        ConfigurableApplicationContext context = app.run(args);
//        context.close();

        //默认启动方式
//        SpringApplication.run(DemoApplication.class, args);
    }

    //配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("dialect", "Mysql");    //配置postgresql数据库的方言支持Oracle,Mysql,MariaDB,SQLite,Hsqldb,PostgreSQL六种数据库
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}

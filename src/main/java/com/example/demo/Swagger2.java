package com.example.demo;

import com.example.demo.util.OutputUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Parameter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        //Swagger2 JWT 整合增加token输入！
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("登录后的令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))//暴露指定包下的接口说明，可以指定多个包
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        OutputUtil.d("swagger2 url : http://localhost:20888/swagger-ui.html?baseUrl");

        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("简单优雅的restful风格，")
                .termsOfServiceUrl("http://localhost:20888/swagger-ui.html?baseUrl=#/")
                .contact(new Contact("Wanc","","919536816@qq.com"))
                .version("1.0")
                .build();
    }

}

package com.wang.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {

    private final String name = "Kell Wang";
    private final String url = "https://github.com/MagicWang0907";
    private final String email = "2509921643@qq.com";
    private final String description = "秒杀Swagger文档";
    private final String title = "秒杀系统说明文档";

    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfoBuilder()
                .contact(new Contact(name,url,email))
                .description(description).title(title)
                .version("1.0.0")
                .build();
        docket = docket.apiInfo(apiInfo);
        return docket;
    }
}

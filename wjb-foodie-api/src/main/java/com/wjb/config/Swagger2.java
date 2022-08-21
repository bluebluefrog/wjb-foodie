package com.wjb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2//开始Swagger2
public class Swagger2 {

    //配置Swagger2核心配置
    @Bean
    public Docket createRestApi(){
        //指定使用文档的类型
        return new Docket(DocumentationType.SWAGGER_2)//指定API类型为Swagger2
                .apiInfo(apiInfo())//用于定义api文档总汇信息
                .select().apis(RequestHandlerSelectors.basePackage("com.wjb.controller"))//扫描包(controller)所在的地址
                .paths(PathSelectors.any())//在包下方选择所有的controller
                .build();//构建

    }

    //创建ApiInfo对象
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("foodie api")//文档页标题
                .contact(new Contact("wjb", "wjb.com", "wjb.email"))//创建人信息
                .description("foodie api 文档")
                .version("1.0")//文档版本号
                .termsOfServiceUrl("wjb.com")//网站地址
                .build();//构建

    }
}

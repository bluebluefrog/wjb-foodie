package com.wjb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    public CorsConfig(){}

    @Bean
    public CorsFilter corsFilter(){
        //1添加cores配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //开启前端可访问的端口
        corsConfiguration.addAllowedOrigin("*");

        //是否开启凭证，让请求数据携带内容，发送cookie信息
        corsConfiguration.setAllowCredentials(true);

        //放行哪些method
        corsConfiguration.addAllowedMethod("*");

        //开放header参数，设置允许的header
        corsConfiguration.addAllowedHeader("*");

        //2为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        //注册配置参数1适用于什么路由参数2传入配置
        corsSource.registerCorsConfiguration("/**",corsConfiguration);

        //3返回重新定义好的Source
        return new CorsFilter(corsSource);
    }
}

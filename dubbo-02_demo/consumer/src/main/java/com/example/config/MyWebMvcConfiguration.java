package com.example.config;

import com.example.controller.ConsumerInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName:MyWebMvcConfiguration
 * Package:com.example.config
 * Description:
 *
 * @Date:2022/4/25 19:50
 * @Author:qs@1.com
 */
@Configuration
@EnableWebMvc
public class MyWebMvcConfiguration implements WebMvcConfigurer {

    // 这么写的目的是为了在SessionInterceptor中能注入spring中的service
        @Bean
        ConsumerInterceptor consumerInterceptor() {
            return new ConsumerInterceptor();
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            // 多个拦截器组成一个拦截器链
            // addPathPatterns 用于添加拦截规则
            // excludePathPatterns 用户排除拦截
            registry.addInterceptor(consumerInterceptor()).addPathPatterns("/**");
        }
}

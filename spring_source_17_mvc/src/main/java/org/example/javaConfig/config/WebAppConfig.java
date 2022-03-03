package org.example.javaConfig.config;

import org.example.javaConfig.interceptor.MyHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * ClassName:WebConfig
 * Package:org.example.config
 * Description:
 *
 * @Date:2022/3/3 8:54
 * @Author:qs@1.com
 */
@Configuration
@ComponentScan(basePackages = "org.example.javaConfig",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class, ControllerAdvice.class}),},
        useDefaultFilters = false)
@EnableWebMvc // 相当于<mvc:annotation-driven />，会去定义一些HandlerMapping、HandlerAdapter、解析器等等
public class WebAppConfig implements WebMvcConfigurer {

    /**
     * 视图解析器
     * @return
     */
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * 文件上传下载的组件
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setMaxUploadSize(1024*1024*10);
        return multipartResolver;
    }

    /**
     * 拦截器
     * @return
     */
    @Bean
    public HandlerInterceptor myHandlerInterceptor() {
        return new MyHandlerInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myHandlerInterceptor()).addPathPatterns("/**");
    }


}

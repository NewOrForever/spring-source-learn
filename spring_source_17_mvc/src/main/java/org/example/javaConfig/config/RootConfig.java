package org.example.javaConfig.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * ClassName:RootConfig
 * Package:org.example.config
 * Description: 父容器配置类
 *
 * @Date:2022/3/3 8:54
 * @Author:qs@1.com
 */
@Configuration
@ComponentScan(basePackages = "org.example.javaConfig", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebAppConfig.class)})
public class RootConfig {

}

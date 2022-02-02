package org.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * ClassName:AppConfig
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/14 15:01
 * @Author:qs@1.com
 */
@ComponentScan("org.example")
@EnableAspectJAutoProxy
@EnableAsync
public class AppConfig {
}

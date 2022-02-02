package org.example.destroy;

import org.springframework.stereotype.Component;

/**
 * ClassName:TestBeanDefinitionDestroyMethodName
 * Package:org.example.destroy
 * Description:
 *
 * @Date:2022/1/7 16:51
 * @Author:qs@1.com
 */
@Component
public class TestBeanDefinitionDestroyMethodName {

    /**
     *  设置beandefinition的destroyMethodName这种方式如果bean的close方法和shutdown方法
     *  同时存在则只会执行close方法，源码中是先判断是否有close方法没有才会去找shutdown方法
     */

    public void close() {
        System.out.println("BeanDefinitionDestroyMethodName - close 。。。");
    }

    public void shutdown() {
        System.out.println("BeanDefinitionDestroyMethodName - shutdown 。。。");
    }
}

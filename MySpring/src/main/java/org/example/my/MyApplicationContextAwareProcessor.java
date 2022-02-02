package org.example.my;

import org.example.spring.BeanPostProcessor;
import org.example.spring.Component;

/**
 * ClassName:ApplicationContextAwareProcessor
 * Package:org.example.spring
 * Description:
 *
 * @Date:2021/12/22 17:01
 * @Author:qs@1.com
 */
@Component
public class MyApplicationContextAwareProcessor implements BeanPostProcessor {

    private final String applicationContext = "myApplicationContextProcessor";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof MyApplicationContextAware) {
            ((MyApplicationContextAware)bean).setApplicationContext(applicationContext);
        }

        return bean;
    }
}

package org.example;

import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyFactory
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/29 21:35
 * @Author:qs@1.com
 */
@Component
public class MyFactoryBean implements SmartFactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new UserService();
    }

    @Override
    public Class<?> getObjectType() {
        return UserService.class;
    }

    /**
     * 当实现SmartFactoryBean而且该方法返回true时，在容器初始化的时候就会调用getObject创建bean
     * 同时会缓存到factoryBeanObjectCache中
     * &xxx ---> 取单例池中的bean
     * xxx ---> 取factoryBeanObjectCache中缓存的getObect得到的对象
     * @return
     */
    @Override
    public boolean isEagerInit() {
        return false;
    }
}

package org.example;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.stereotype.Component;

/**
 * ClassName:OrderService
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/25 21:24
 * @Author:qs@1.com
 */
@Component
public class MyFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        UserService userService = new UserService();
        return userService;
    }

    @Override
    public Class<?> getObjectType() {
        return UserService.class;
    }

}

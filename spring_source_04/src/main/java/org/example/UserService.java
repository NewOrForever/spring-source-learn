package org.example;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/27 16:39
 * @Author:qs@1.com
 */
@Component
@Conditional({MyCondition.class})
//@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
public class UserService implements SmartInitializingSingleton, InitializingBean {

    public UserService() {
        System.out.println("userservice init construct");
    }

    public void test() {
        System.out.println("userService test");
    }


    /**
     *  在所有的非懒加载单例bean加载完后执行
     */
    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("userservice process afterSingletonsInstantiated。。。");
    }

    /**
     * 初始化前，如果是非懒加载单例bean的时候这个方法是比afterSingletonsInstantiated先调用的
     * 因为非懒加载单例bean初始化容器的时候就去去创建，而afterPropertiesSet方法是在创建bean的
     * 过程中（初始化前）执行，当所有的非懒加载单例bean创建完后会去执行afterSingletonsInstantiated方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("userservice process afterPropertiesSet method。。。");
    }
}

package org.example.spring;

/**
 * ClassName:BeanNameAware
 * Package:org.example.spring
 * Description: spring内核自带去回调UserService的setBeanName方法
 *
 * @Date:2021/12/22 16:49
 * @Author:qs@1.com
 */
public interface BeanNameAware {

    void setBeanName(String name);

}

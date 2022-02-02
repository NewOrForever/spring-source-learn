package org.example.spring;

/**
 * ClassName:InitializingBean
 * Package:org.example.spring
 * Description:
 *
 * @Date:2021/12/22 11:22
 * @Author:qs@1.com
 */
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}

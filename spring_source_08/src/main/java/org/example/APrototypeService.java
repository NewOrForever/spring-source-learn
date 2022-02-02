package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * ClassName:AService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/14 14:58
 * @Author:qs@1.com
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class APrototypeService {

    @Autowired
//    @Lazy
    private BPrototypeService bPrototypeService;

    public void test() {
        System.out.println("APrototypeService test 。。。");
    }

}

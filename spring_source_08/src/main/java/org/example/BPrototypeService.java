package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ClassName:BService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/14 14:58
 * @Author:qs@1.com
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Lazy
public class BPrototypeService {

    @Autowired
    private APrototypeService aPrototypeService;

//    @Async
    public void testB() {
        System.out.println("BPrototypeService test 。。。");
    }

}

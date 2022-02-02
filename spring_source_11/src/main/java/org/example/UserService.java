package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/26 13:53
 * @Author:qs@1.com
 */
@Component
@Import(AccountService.class)
public class UserService{

//    @Bean
//    public OrderService orderService() {
//        return new OrderService();
//    }
}

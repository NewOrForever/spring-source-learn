package org.example;

import org.springframework.context.annotation.Bean;

/**
 * ClassName:OrderInterface
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/28 16:58
 * @Author:qs@1.com
 */
public interface AccountInterface {

    @Bean
    default AccountService accountService(){
        return new AccountService();
    }

}

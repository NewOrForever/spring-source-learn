package org.example;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * ClassName:OrderService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/26 13:53
 * @Author:qs@1.com
 */
@Component
@Import(AccountService.class)
public class OrderService implements AccountInterface {
}

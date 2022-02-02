package org.example;

import javafx.scene.effect.SepiaTone;
import org.example.qualifier.LoadBalance;
import org.example.qualifier.Random;
import org.example.qualifier.RoundRobin;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/10 18:20
 * @Author:qs@1.com
 */
@Component
public class UserService implements BeanNameAware {
//    @Autowired
//    @Lazy
//    private OrderService orderService;
//
//    private User user;
//    private Order order;
//    @Autowired
//    @Lazy
//    public void setUser( @Lazy User user, Order order) {
//        this.user = user;
//        this.order = order;
//    }

    @Value("${myorder}")
    private String order;

    @Value("#{order}")
    private Order orderBean;

//    @Autowired
//    private OrderService[] orderServiceArr;
//    @Autowired
//    private List<OrderService> orderServiceList;
//    @Autowired
//    private Map<String, OrderService> orderServiceMap;
//    @Autowired
//    private Set<OrderService> orderServiceSet;
//    @Autowired
//    private List<?> xxx;
//    @Autowired
//    private List<Object> ttt;

    // 自己注入自己，当没有别的bean时才使用自己
    @Autowired
    private UserService userService;

    @Autowired
    private OrderServiceInterface orderService;

    @Autowired
    private User user;

    @Autowired
    @RoundRobin
    private LoadBalance loadBalance;

    //@Resource(name = "orderService", type = OrderService.class)
    private OrderService orderService123;

    @Resource(name = "orderService")
    public void setOrderService123(OrderService orderService123) {
        this.orderService123 = orderService123;
    }

    public void test() {
        System.out.println(userService);
        System.out.println(orderService);
        System.out.println(user);
        System.out.println(order);
        System.out.println(orderBean);
        System.out.println(loadBalance);
        System.out.println(orderService123);
//        System.out.println(orderServiceArr);
//        System.out.println(orderServiceList);
//        System.out.println(orderServiceMap);
//        System.out.println(orderServiceSet);
//        System.out.println(ttt);
    }

    private String beanName;
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "beanName='" + beanName + '\'' +
                '}';
    }
}

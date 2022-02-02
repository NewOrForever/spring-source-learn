package org.example.my;

import org.example.spring.*;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/21 11:10
 * @Author:qs@1.com
 */
@Component("userService")
@Scope("prototype")
public class UserService implements InitializingBean, UserServiceInterface, BeanNameAware, MyApplicationContextAware {

    @Autowired
    private OrderService orderService;

    @MyValue("====xxx")
    private String myValue;

    public UserService() {
        System.out.println("调用userService无参构造方法");
    }

    @Override
    public void test(){
        System.out.println("orderservice = " + orderService);
        System.out.println(myValue);
        System.out.println("test");
    }

    /**
     * 依赖注入后，初始化前
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet。。。");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("进入postconstruct注解的方法。。。");
    }


    @Override
    public void setBeanName(String name) {
        System.out.println("执行beannameaware的setbeanname方法: " + name);
    }

    @Override
    public void setApplicationContext(String applicationContext) {
        System.out.println("执行ApplicationContextAware的setApplicationContext方法: " + applicationContext);
    }
}

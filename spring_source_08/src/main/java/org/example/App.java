package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        AService aService = (AService) context.getBean("AService");
//        aService.test();

        /**
         *  spring在什么情况下会产生循环依赖
         */


        // aop

        // A依赖B，B依赖A，加个缓存存储原始对象，A实例化后得到一个原始对象加入缓存，A填充属性需要注入B，B没有则去创建
        // B又需要A，A还在创建中直接取缓存中的，B就创建了，然后A也能创建了
        // 这样不就行了？为啥还要三级缓存？
        // 因为AOP，实际单例池中存的是A的代理对象，A的属性B应该注入的是代理对象

        // B填充A属性 ---》 单例池找A ---》 creatingSet<A> ---》说明是循环依赖了 ---》提前aop ----》代理对象


        // Async注解A类方法：生成了其他代理对象，循环依赖时抛错
        // 如果不抛错，B注入的就是A的AOP后的代理对象，而A添加到单例池的是Async后的代理对象，很明显这是有冲突的
        // 循环依赖的注入点使用@Lazy
        // Async注解B类方法不跑错：A先创建 再B
        /**
         * 属性注入时会先去判断是否有@Lazy注解，有的话直接返回一个代理对象给属性，当用到的时候再去getTarget去找bean
         * 所以循环依赖时注入B的时候B直接注入一个代理对象，然后A就可以往下走创建bean了，当B使用的时候去注入A（此时A
         * 已经创建完成，直接从单例池拿就可以了）
         */

        // 原型bean
        /**
         * A、B只要有一个是单例bean就可以
         * 如果都是原型bean则会抛错，原型bean没有三级缓存只能去新创建一个所以打破不了循坏
         * 可以使用@Lazy注解来解决
         */
//        APrototypeService aService = (APrototypeService) context.getBean("APrototypeService");
//        aService.test();

        // 构造方法Lazy
        /**
         * A构造方法注入的时候循环依赖抛错
         * 因为构造方法注入的时候A还没有实例化，缓存就用不了啊，B的A属性注入的时候缓存中没有就又得去创建，重复如此.
         * 解决：A的构造方法使用@Lazy注解
         *
         * B构造方法注入不会抛错
         * 因为A比B先创建，但是如果还有个C和B是循环依赖，则还是会抛错地
         */


        // @Transactional不跑错
        // 事务相关的没有用BeanPostProcessor

        // 自己注入自己其实是一种特殊的循环依赖


        // 测试getSingle()这个解决循环依赖核心方法的bug
        // getSingle方法需要加入sleep来模拟阻塞
        // AService和BService类上使用@Lazy注解，为了不在spring容器初始化的时候创建bean而是在使用的时候创建bean
     /*   new Thread(){
            @Override
            public void run() {
                AService aService = (AService) context.getBean("AService");
                System.out.println(aService);
            }
        }.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run() {
                AService aService = (AService) context.getBean("AService");
                System.out.println(aService);
            }
        };*/

    }
}


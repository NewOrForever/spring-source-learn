package com.example.consumer;

import com.example.CallbackListener;
import com.example.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAutoConfiguration
public class CallBackDubboConsumerDemo {

    /**
     * 参数回调
     */

    @Reference(version = "callback")
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CallBackDubboConsumerDemo.class, args);
        DemoService demoService = context.getBean(DemoService.class);

        demoService.sayHello("哈哈", "d1", new CallbackListenerImpl());
        demoService.sayHello("哈哈", "d2", new CallbackListenerImpl());
        demoService.sayHello("哈哈", "d3", new CallbackListenerImpl());
        //demoService.sayHello("哈哈", "d4", new CallbackListenerImpl());

        /**
         * callbacks这个参数理解的测试
         *  - 上面是4个实例报错
         *  - 这是一个实例调4次不报错
         *  - 所以callbacks是同一个连接最大回调实例个数
         */
//        CallbackListenerImpl callbackListener = new CallbackListenerImpl();
//        demoService.sayHello("哈哈", "d1", callbackListener);
//        demoService.sayHello("哈哈", "d2", callbackListener);
//        demoService.sayHello("哈哈", "d3", callbackListener);
//        demoService.sayHello("哈哈", "d4", callbackListener);



    }
}

class CallbackListenerImpl implements CallbackListener {

    @Override
    public void changed(String msg) {
        System.out.println("被回调了：" + msg);
    }
}

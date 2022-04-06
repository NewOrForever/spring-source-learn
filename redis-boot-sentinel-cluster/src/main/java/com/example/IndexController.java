package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:IndexController
 * Package:com.example
 * Description:
 *
 * @Date:2022/3/28 14:54
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/index")
public class IndexController implements ApplicationContextAware {

    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private HttpServletRequest request;

    /**
     * 测试节点挂了哨兵重新选举新的master节点，客户端是否能动态感知到
     * 新的master选举出来后，哨兵会把消息发布出去，客户端实际上是实现了一个消息监听机制，
     * 当哨兵把新master的消息发布出去，客户端会立马感知到新master的信息，从而动态切换访问的masterip
     */
    @RequestMapping("test_sentinel")
    public void testSentinel() {
        int i = 1;
        while (true) {
            try {
                stringRedisTemplate.opsForValue().set("zhuge" + i, i + "");
                System.out.println("设置key：" + "zhuge" + i);
                i++;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("错误：" + e);
            }
        }
    }

    @RequestMapping("/testCluster")
    public void testCluster() {
        stringRedisTemplate.opsForValue().set("zhuge", "666");
        System.out.println(stringRedisTemplate.opsForValue().get("zhuge"));
    }


    @GetMapping("/test")
    public String test() {
        System.out.println(request.getRequestURL());
        return "001";
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Object aaa = applicationContext.getBean("redisConverter");

    }
}

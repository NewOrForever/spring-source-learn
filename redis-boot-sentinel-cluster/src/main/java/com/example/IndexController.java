package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class IndexController {

    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试节点挂了哨兵重新选举新的master节点，客户端是否能动态感知到
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


    @GetMapping("/test")
    public String test() {
        return "001";
    }


}

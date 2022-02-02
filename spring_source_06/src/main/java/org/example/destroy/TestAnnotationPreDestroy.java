package org.example.destroy;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * ClassName:TestAnnotationPreDestroy
 * Package:org.example.destroy
 * Description:
 *
 * @Date:2022/1/7 16:42
 * @Author:qs@1.com
 */
@Component
public class TestAnnotationPreDestroy {
    @PreDestroy
    public void testPreDestroy() {
        System.out.println("@PreDestroy - test 。。。");
    }
    @PreDestroy
    public void testPreDestroy2() {
        System.out.println("@PreDestroy2 - test 。。。");
    }
}

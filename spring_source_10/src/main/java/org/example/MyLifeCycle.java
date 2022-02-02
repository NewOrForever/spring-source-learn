package org.example;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyLifeCycle
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/24 15:55
 * @Author:qs@1.com
 */
@Component
public class MyLifeCycle implements SmartLifecycle {
    private boolean isRunning;

    @Override
    public void start() {
        System.out.println("finishfresh 。。。生命周期开始");
        isRunning = true;
    }

    @Override
    public void stop() {
        System.out.println("context close 。。。生命周期结束");
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPhase() {
        return 1;
    }
}

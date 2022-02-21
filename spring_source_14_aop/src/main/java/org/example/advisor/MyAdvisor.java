package org.example.advisor;

import org.aopalliance.aop.Advice;
import org.example.UserService;
import org.example.advice.MyBeforeAdvice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * ClassName:advisor
 * Package:org.example
 * Description:
 *
 * @Date:2022/2/15 8:17
 * @Author:qs@1.com
 */
@Component
public class MyAdvisor implements PointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return new Pointcut() {
            @Override
            public ClassFilter getClassFilter() {
                // 类型过滤
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> clazz) {
                        return UserService.class.getName() == clazz.getName();
                    }
                };
            }

            @Override
            public MethodMatcher getMethodMatcher() {
                // 方法匹配
                return new MethodMatcher() {
                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        return method.getReturnType() == Void.TYPE;
                    }

                    @Override
                    public boolean isRuntime() {
                        return false;
                    }

                    // 当isRuntime返回true时，最终还是需要有参数的这个matches来再次匹配
                    @Override
                    public boolean matches(Method method, Class<?> targetClass, Object... args) {
                        return false;
                    }
                };
            }
        };
    }

    @Override
    public Advice getAdvice() {
        return new MyBeforeAdvice();
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}

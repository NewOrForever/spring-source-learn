package org.example.cglibProxy;

import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * ClassName:cglibProxy
 * Package:org.example
 * Description:
 *
 * @Date:2022/2/11 14:11
 * @Author:qs@1.com
 */
public class CglibProxyFactory<T> {
    private T target;

    public void setTarget(T target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
//        enhancer.setCallback(new MethodInterceptor() {
//            @Override
//            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//                System.out.println("cglib动态代理start...");
////            Object result = method.invoke(target, objects);
////            Object result = methodProxy.invoke(target, objects);
//                Object result = methodProxy.invokeSuper(o, objects);
//                System.out.println("cglib动态代理end...");
//                return result;
//            }
//        });
        // NoOp是啥都不做的拦截器
        enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE, new MyCgInterceptor() });
        // 返回的数字就是上面Callback数组的下标（根据方法名来返回不同的下标）
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                return "execute".equals(method.getName()) ? 1 : 0;
            }
        });
        return enhancer.create();
    }

    private class MyCgInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

            System.out.println("cglib动态代理start...");
            Object result = method.invoke(target, objects);
//            Object result = methodProxy.invoke(target, objects);
//            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println("cglib动态代理end...");
            return result;
        }
    }
}

package org.example.javaConfig.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:MyInterceptor
 * Package:org.example.javaConfig
 * Description:
 *
 * @Date:2022/3/3 10:11
 * @Author:qs@1.com
 */
public class MyHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("org.example.javaConfig.interceptor.MyHandlerInterceptor#preHandle");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

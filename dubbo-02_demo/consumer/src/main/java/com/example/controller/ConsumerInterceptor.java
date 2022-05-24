package com.example.controller;

import org.apache.dubbo.rpc.Constants;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:ConsumerInterceptor
 * Package:com.example.controller
 * Description:
 *
 * @Date:2022/4/25 19:15
 * @Author:qs@1.com
 */

public class ConsumerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // RpcContext设置tag
        String name = request.getParameter("name");
        if (name.endsWith("test")) {
            RpcContext.getContext().setAttachment("dubbo.tag", "tag1");
        } else {
            RpcContext.getContext().setAttachment("dubbo.tag", "tag2");
        }
        return  true;
    }
}

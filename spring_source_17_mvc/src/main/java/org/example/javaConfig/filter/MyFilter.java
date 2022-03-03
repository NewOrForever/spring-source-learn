package org.example.javaConfig.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * ClassName:MyFilter
 * Package:org.example.javaConfig.filter
 * Description:
 *
 * @Date:2022/3/3 15:13
 * @Author:qs@1.com
 */
@WebFilter("/user/mapping")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("org.example.javaConfig.filter.MyFilter#doFilter");
        chain.doFilter(request, response);
    }

}

package org.example.web.spi;

import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * ClassName:MySpringServletContainerInitializer
 * Package:org.example.spi
 * Description:
 *
 * @Date:2022/3/2 14:37
 * @Author:qs@1.com
 */
@HandlesTypes(WebApplicationInitializer.class)
public class MySpringServletContainerInitializer extends SpringServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) throws ServletException {
        // 通过servletContext动态添加Servlet
        servletContext.addServlet("spiServlet", new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                resp.getWriter().write("spi-servlet -> doGet");
            }
        }).addMapping("/");
    }
}

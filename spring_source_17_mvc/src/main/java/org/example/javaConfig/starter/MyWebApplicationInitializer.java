package org.example.javaConfig.starter;

import org.example.javaConfig.config.RootConfig;
import org.example.javaConfig.config.WebAppConfig;
import org.example.javaConfig.filter.MyFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.File;

/**
 * ClassName:MyWebApplicationInitializer
 * Package:org.example
 * Description: 在 {@link org.springframework.web.SpringServletContainerInitializer}中执行
 *
 * @Date:2022/3/3 8:32
 * @Author:qs@1.com
 */
public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

       /** @see {@link org.springframework.web.WebApplicationInitializer}中的注释*/
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        // Create the 'root' Spring application context
////        AnnotationConfigWebApplicationContext rootContext =
////                new AnnotationConfigWebApplicationContext();
////        rootContext.register(AppConfig.class);
////
////        // Manage the lifecycle of the root application context
////        servletContext.addListener(new ContextLoaderListener(rootContext));
////
////        // Create the dispatcher servlet's Spring application context
////        AnnotationConfigWebApplicationContext dispatcherContext =
////                new AnnotationConfigWebApplicationContext();
////        dispatcherContext.register(DispatcherConfig.class);
////
////        // Register and map the dispatcher servlet
////        ServletRegistration.Dynamic dispatcher =
////                servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
////        dispatcher.setLoadOnStartup(1);
////        dispatcher.addMapping("/");
//    }

    /**
     * @return 前端控制器DispatcherServlet的拦截路径
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * @return 父容器的配置文件
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * @return 子容器的配置文件
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebAppConfig.class};
    }

//    @Override
//    protected Filter[] getServletFilters() {
//        return new Filter[]{new MyFilter()};
//    }
}

package org.example.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * ClassName:MyPlugin
 * Package:org.example.plugins
 * Description: 自定义一个Interceptor
 *  - 接口默认方法plugin中Plugin.wrap中能看出这个plugin应该怎么配
 *  - 实现Interceptor，有@Intercepts注解，@Signature注解中定义了拦截哪个类的哪个方法
 *      - 在读源码过程中能看到Mybatis中这个plugin是用来增强四大核心对象的
 *      - Executor、StatementHandler、ParameterHandler、ResultSetHandler
 *      - 既然是增强那肯定是要生成一个代理对象罗
 *
 * @Date:2022/3/14 13:04
 * @Author:qs@1.com
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MyPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MyPlugin：代理");
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        return invocation.proceed();
    }
}

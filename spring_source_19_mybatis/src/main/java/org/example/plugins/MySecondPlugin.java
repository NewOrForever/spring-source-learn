package org.example.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * ClassName:MyPlugin
 * Package:org.example.plugins
 * Description: 用来测试interceptorChain.pluginAll的时候第二个是不是target是动态代理对象
 *
 * SecondPluginProxy --------------------- 执行进入SecondPlugin的intercet方法
 *  target: PluginProxy ------------------ 执行进入MyPlugin的intercept方法
 *              target: Executor --------- 执行Eeecutor的query方法
 *
 *
 *
 * @Date:2022/3/14 13:04
 * @Author:qs@1.com
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MySecondPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MyPlugin2：代理");
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];

        return invocation.proceed();
    }
}

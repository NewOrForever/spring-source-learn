package org.example.spring;

/**
 * ClassName:BeanDefinition
 * Package:org.example.spring
 * Description:
 *
 * @Date:2021/12/21 15:53
 * @Author:qs@1.com
 */
public class BeanDefinition {

    private Class type;
    private String scope;
    private boolean isLazy = false;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean getLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }
}

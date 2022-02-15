# spring aop学习

## 阅读源码
### 源码解析
ProxyFactory选择cglib或者jdk动态代理
ProxyFactory在生成代理对象之前需要决定到底是使用JDK动态代理还是CGLIB技术：
```
// config就是ProxyFactory对象

// optimize为true,或proxyTargetClass为true,或用户没有给ProxyFactory对象添加interface
if (!NativeDetector.inNativeImage() &&
    config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
	Class<?> targetClass = config.getTargetClass();
 if (targetClass == null) {
  throw new AopConfigException("TargetSource cannot determine target class: " +
    "Either an interface or a target is required for proxy creation.");
	}
    // targetClass是接口
    // target是Jdk动态代理生成的类
 if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
  return new JdkDynamicAopProxy(config);
	}
    // 使用Cglib
 return new ObjenesisCglibAopProxy(config);
}
else {
    // 使用Jdk动态代理
 return new JdkDynamicAopProxy(config);
}
```
- if(targetClass.isInterface() || targetClass.isProxyClass())这个判断其实不怎么用
```
proxyFactory.setTargetClass(UserServiceInterface.class);
将接口设置到targetClass中，getProxy方法强转成接口类型，但是执行方法时，method.invoke(target, args)中
target为空，报错。
setTarget()方法和setTargetClass()方法底层都是要构建一个TargetSource对象给targetsource属性，所以这里即便是一开始setTarget了也
没用，setTargetClass后targetSource属性又被覆盖掉了，所以这样设置没啥意义。
```


### 问题

### 一些知识点：
- 实例化子类的有参构造方法时，会不会先去调父类构造方法?
```
还是会先去调用父类的无参构造方法的，父类没有无参构造方法的话会报错的(编译器就过不了)
子类构造方法第一行默认有个super()只不过被省略了而已
```












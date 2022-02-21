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
- exposeProxy属性
```
factoryproxy.setExposeProxy(true)会将代理对象添加到ThreadLocal中
test方法中AopContext.currentProxy() 拿到当前类的代理对象就不需要UserService去自己依赖自己了
```
- Pointcut的MethodMatcher
当isRuntime()返回true时，会将MethodInterceptor封装成InterceptorAndDynamicMethodMatcher对象，最后在
MethodInvocation.proceed()执行链路的过程中会最后再去调用带参数的matches方法去匹配 
```
@Override
public MethodMatcher getMethodMatcher() {
    // 方法匹配
    return new MethodMatcher() {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return method.getReturnType() == Void.TYPE;
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        // 当isRuntime返回true时，最终还是需要有参数的这个matches来再次匹配
        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    };
}
```


### 问题
开启aop后，AppConfig中如果使用@Bean来定义一个Advisor，没使用static
```
 启动中会报错AppConfig还在创建中，但是进入aop的初始化后中，找所有advisor -> 找到getBean(advisor) -> @Bean走factoryMethod
 -> getBean(factoryBeanName也就是AppConfig) -> 但是还在创建中，所以会在getSingleton方法中的beforeSingleton中报错
 所以需要@Component注解来定义，或者@Bean static方法来定义
```

### 一些知识点：
- 实例化子类的有参构造方法时，会不会先去调父类构造方法?
```
还是会先去调用父类的无参构造方法的，父类没有无参构造方法的话会报错的(编译器就过不了)
子类构造方法第一行默认有个super()只不过被省略了而已
```
- 建一个BeanFactoryPostProcessor在启动的时候给引入的aop的那个beandefinition添加一个属性（我自己弄的）
```
@Component
public class MyAspectSetIntercetorBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory.containsBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME)) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME);
            if (AnnotationAwareAspectJAutoProxyCreator.class.getName() == beanDefinition.getBeanClassName()) {
                // 开启了aop
                // 设置interceptorNames（advice的beanName）
                beanDefinition.getPropertyValues().addPropertyValue("interceptorNames", "myBeforeAdvice");
            }
        }
    }
}
```












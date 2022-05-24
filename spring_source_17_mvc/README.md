rootconfig implements ServletContextAware
@EnableWebMvc
加载拦截器啊啥的WebMvcConfigurer



思考：有必要加载两个容器吗？



## spring mvc javaconfig方式启动原理
### java的SPI机制（Service Provider interface - 服务提供者接口）
maven引入spring_source_16_mvc的包，spring_source_17_mvc的resources目录下META-INF/services目录下建一个接口全名的文件，并在文件中
写上接口实现类
```
public class UserSpi {
    public static void main(String[] args) {
        ServiceLoader<IUserDao> userDaos = ServiceLoader.load(IUserDao.class);
        for (IUserDao userDao : userDaos) {
            userDao.save();
        }
    }
}
```

### 注解方式启动
* 启动大致流程
```
tomcat通过SPI机制去加载spring-web下WEB-INF/services中的SpringServletContainerInitializer类 -> 执行WebApplicationInitializer的onStartUp
方法，ServletContext参数是tomcat提供进来的,@HandlesTypes导入的接口，spring会去找实现接口的所有类，然后传入另一个参数中
-> 非接口&&非抽象类去实例化添加到集合 -> 遍历集合，执行每个WebApplicationInitializer的onStartUp方法 -> 所以我们要创建一个
实现了WebApplicationInitializer的类（继承AbstractAnnotationConfigDispatcherServletInitializer这个抽象类就行了）
```
* web.xml不需要了，但是得要在pom中添加插件
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.3.1</version>
</plugin>
```
* 创建启动器继承了WebApplication的子类AbstractAnnotationConfigDispatcherServletInitializer
```
public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

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
}
```
* 父子容器配置类，WebAppConfig可以实现WebMvcConfigurer来扩展配置










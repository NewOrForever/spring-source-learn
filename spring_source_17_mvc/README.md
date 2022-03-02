## spring mvc启动原理
web.xml中ContextLoaderListener（父）
DispatcherServlet（子）
父子容器，子容器可以访问父容器，父容器不能访问子容器


不用xml方式，用javaconfig方式
SPI（服务提供者接口）
META-INF.services下添加一个接口限定名的文件，文件里写上实现类的全名 -> 执行实现类的onStartUp方法
spring-web下web-inf -> 执行initaializer的onStartUp方法，ServletContext参数是tomcat提供进来的,@HandlesTypes导入的接口，spring会去找
实现接口的所有类，然后传入另一个参数中 -> 非接口&&非抽象类去实例化添加到集合 -> 遍历集合，执行每个WebApplicationInitializer的onStartUp方法
-> CreateRootApplicationContext -> AnnotationCOnfigWebApplicationCOntext无参，先不refresh


ContextLoaderListener
rootconfig implements ServletContextAware
init DispatcherServlet的时候进入FrameworkServlet的initWebApplicitoncontext会拿到parent context设置进来
finishrefresh会发布一个事件
@EnableWebMvc
加载拦截器啊啥的WebMvcConfigurer



思考：有必要加载两个容器吗？








spring mvc父子容器



## spring mvc 注解方式启动原理
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
* web.xml不需要了，但是得要在pom中添加插件
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.3.1</version>
</plugin>
```
* 模仿spring的spi，spring-web下的spi文件



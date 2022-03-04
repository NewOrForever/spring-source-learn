

rootconfig implements ServletContextAware
@EnableWebMvc
加载拦截器啊啥的WebMvcConfigurer



思考：有必要加载两个容器吗？



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






## mybatis
### jdbc
DriverManager使用spi机制 -> 看mysql-connector-java的META-INF
DriverManager的静态代码块loadInitialDrivers()方法执行ServiceLoader类的方法来加载驱动
弊端：
1.数据库配置、sql语句在代码中硬编码，维护性差
xml
2.频繁创建和关闭数据库连接，资源消耗大
连接池
3.没有缓存
一二
4.参数设置不方便，开发效率低
xml动态配置<if>
5.处理程序结果集、类型转换，开发效率低
resultType、resultMap

mybatis半自动的ORM  

基础支撑层

接口层SqlSession

数据处理层

源码版本修改一下

SqlSessionFactory（构建sqlsessionfactory的过程就是解析xml的过程） 
解析xml节点：
全局：XmlConfigBuilder解析，settings、数据环境、类型处理器、别名解析器、插件（分页插件添加到InterceptorChain）... -> 封装到Configuration对象
DefaultObjectFactory



mapper.xml：XmlMappeBuilder解析，CRUD（MappedStatement）、resultMap...
cache节点（二级缓存）

-》 SqlSession








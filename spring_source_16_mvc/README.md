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

思考：有必要加载两个容器吗？








spring mvc父子容器
# spring学习 
## spring底层核心原理解析 (表层解析非深入)
### bean的创建生命周期
```
UserService.class ---> 无参构造方法（推断构造方法） ---> 普通对象 ---> 依赖注入（属性赋值） ---> 初始化前（@PostConstruct） ---> 初始化（InitializingBean）
---> 初始化后（AOP）---> 代理对象 ---> bean
```
### 推断构造方法
```
默认是会去找无参构造方法
多个构造方法的情况下:
 1. 无参构造方法没有,若是构造方法超过两个,spring会不知道选哪个构造方法而报错,若是只有一个那么spring就会选择这个
 2. 无参构造方法有,spring直接会去找无参的
如果某个构造方法使用了@Autowired注解，则spring会去找这个构造方法 
```

###  aop相关(代理对象)
- 开启切面@EnableAspectJAutoProxy后，获取到的UserService代理对象的OrderService属性为空
```
开启切面后,spring判断此时UserService要去进行Aop处理,因此getBean()获取到的是代理对象
```
- spring去找aop的方法
```
找出所有切面bean
遍历所有切面bean
遍历每个切面bean的方法
将匹配PointCut的方法缓存map(可以理解为<UserService.class, List<Method>>)
```
- 简单理解事务管理器工作原理
```
判断当前执行的方法是否有@Transactional注解
创建数据库连接(事务管理器datasource)
conn.autoCommit = false;   //这一步是很关键的,因为默认是true的,会自动去commit,这样的话事务就没用了

target.test();  // 执行目标对象方法

conn.commit();
conn.rollback();
```
注意：
```
判断事务是否会失效的标准：某个加了@Transanctional的方法被调用时，需要判断该方法是否是直接被代理对象调用的，是的话事务
就能生效否则事务不会生效，比如下面的代码a()方法的事务就不会生效（a()方法没有直接被代理对象调用）：
    @Transactional
    public void test() {
        String sql = "INSERT INTO users VALUES(1, '1','1',1,1,NOW(),NOW())";
        jdbcTemplate.execute(sql);
        //throw  new NullPointerException();
        a(); // 这样写不会报事务已经存在的错误,因为此时调用者不是代理对象,而是目标对象,对目标对象而言事务的注解是没用的
    }
    // Propagation.NEVER: 已经存在事务,再调用时会报错
    @Transactional(propagation = Propagation.NEVER)
    public void a() {
    
    }
    
```

## 手写模拟一个简单的spring
context =new annotationapplicationcontext(appconfig.class)
context.getbean("beanName")

annotationbeannamegenerator


HierarchicalBeanFactory父子bean工厂











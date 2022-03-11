## Mybatis源码学习
### jdbc的使用
* 详细使用看TestJdbc
* DriverManager使用的spi机制，可以省略加载驱动这一步骤
```
DriverManager使用spi机制 -> 看mysql-connector-java包下的的META-INF/services
DriverManager的静态代码块loadInitialDrivers()方法执行ServiceLoader类的方法来加载驱动
```
* jdbc的弊端与mybatis的解决方案
```
弊端：
1.数据库配置、sql语句在代码中硬编码，维护性差
mybatsi.xml全局配置
2.频繁创建和关闭数据库连接，资源消耗大
连接池
3.没有缓存
一二
4.参数设置不方便，开发效率低
userMapper.xml动态配置<if>、<where>
5.处理程序结果集、类型转换，开发效率低
resultType、resultMap
```



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
cache节点（二级缓存）：一层包一层

解析sql -> sqlsource -> 执行sql语句 -> boundsql
动态sql：select * from user where id=${id} -> select * from user where id = 1
解析成一个个sqlnode，组合设计模式
静态sql：select * from user where id=#{id} -> select * from user where id = ?

注解方式

-》 SqlSession



1.#{}方式能够很大程度防止sql注入，而${}方式无法防止Sql注入。
2.${}方式一般用于传入数据库对象，例如传入表名，order by后的列名。
3.一般能用#的就别用$。
4.MyBatis排序时使用order by 动态参数时需要注意，用${}而不是#{}。




1.mybatis以前的基础课，用法
1.1下源码
2.mybatis整合spring复习
3.mybatis本节课学习 - 源码学习
4.xml解析回顾


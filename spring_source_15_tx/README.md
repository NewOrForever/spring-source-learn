### spring事务传播机制测试
测试都是在有多个逻辑事务情况下。一些数据库的隔离级别没测，读数据的情况下，一些脏读啊啥的不是很了解，待学习完mysql优化再回来测试测试。

* test新建一个事务，a不同传播机制测试（NEVER/NOT_SUPPORTED/NESTED/NEW/其他（SUPPORTED/QREQUIRED））
1. NOT_SUPPORTED
```
a这个逻辑事务会suspend但是不会新开一个事务，执行sql的时候让jdbc或者mybatis来创建数据库
连接（autoCommit=true），每个sql单独提交，也就是a不支持事务了。
```
伪代码：
```
   spring事务管理器创建数据库连接conn
    conn.autocommit = false; // 手动提交
    conn.隔离级别
    conn放入ThreadLocal<Map>  datasource,conn连接
    target.test()执行sql1，sql2

        a()
            挂起 -> resources解绑
            
            sql1 -> resources没有数据库连接 -> jdbc或mybatis创建conn1执行autoCommit
            sql2 -> resources有conn1 -> conn1执行autoCommit
         
            恢复 -> bindResources
            
    conn提交或回滚
```
实例：
```
 @Transactional
    public void test() {
        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(1,'1','1','1','0')");
        orderService.a();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void a() {
        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(2,'1','1','1','0')");
    }
```
2. NEW
```
a这个逻辑事务会suspend但是会新开一个事务，a事务和test事务各自处理自己的提交回滚
```
伪代码：
```
    spring事务管理器创建数据库连接conn
    conn.autocommit = false; // 手动提交
    conn.隔离级别
    conn放入ThreadLocal<Map>  datasource,conn连接
    target.test()执行sql1，sql2
    
         a()
             挂起 -> 挂起对象.conn连接 ->
             spring事务管理器，创建数据库连接conn1
             conn1.autocommit=false;
             conn1.隔离级别
             conn1放入ThreadLocal<Map> datasource，conn1连接
             sql
             // 是Propagation.REQUIRED这个传播机制：那么a()和test()方法就在同一个数据库连接，set部分失败全局回滚不管是true还是false，全局都会回滚，如果
             // try...catch了的话，true则全局回滚，false则不全局回滚也就是都会commit（如果sql报错了，那么错误的sql不会提交）
             // 是Propagation.REQUIRES_NEW这个传播机制：那么a()方法就会新建一个数据库连接，两个方法各自提交回滚
             throw new Exception()
             conn1提交、回滚
             恢复 -> 挂起对象.conn连接 -> ThreadLocal<Map>
             rollback() -> true -> ThreadLocal中添加一个全局回滚为true的标记
             
    sql3
    提交 -> commit() -> ThreadLocal true -> 回滚
```
实例：
```
 @Transactional
    public void test() {
        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(1,'1','1','1','0')");
        orderService.a();
    }

    @Transactional(propagation = Propagation.NEW)
    public void a() {
        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(2,'1','1','1','0')");
    }
```

* test不新建事务，a不同传播机制测试
1.test是SUPPORTS，a是REQUIRED
```
test not a actual transaction
执行sql时会让jdbc或mybatis去创建数据库连接，每个sql单独自动提交
a会去用spring事务管理器创建一个新的数据库连接开启一个新的事务
```
伪代码：
```
    sql1 -> resources没有数据库连接 -> jdbc或mybatis创建conn执行autoCommit
    sql2 -> resources有conn -> conn执行autoCommit
    
    spring事务管理器创建数据库连接conn1
    conn.autocommit = false; // 手动提交
    conn.隔离级别
    conn放入ThreadLocal<Map>  datasource,conn连接
    target.a()执行sql1，sql2

    conn1提交或回滚
```
实例：
```
 @Transactional
    public void test() {
        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(1,'1','1','1','0')");
        orderService.a();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void a() {
        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(2,'1','1','1','0')");
    }
```  

* 以上两大类抛异常情况

### 思考
开启事务也是aop ---> 那我要往切面加东西不就只要自己写个advisor就行了么，还有就是要注意advisor的顺序
package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.entity.User;
import org.mybatis.spring.SqlSessionFactoryBean;

import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mybatis源码学习
 */
public class App {
    public static void main(String[] args) throws IOException {
        // SqlNode
        // trim、where、set、foreach、if、choose、when、otherwise、bind


        // 1.加载mybatis全局配置文件，将XML配置文件构建为Configuration配置类
        String resources = "mybatis.xml";
        Reader reader = Resources.getResourceAsReader(resources);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        // 2.拿到要给sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            // 3.执行sql
             User user = sqlSession.selectOne("org.example.mapper.UserMapper.selectById", 1);
            System.out.println(user);

//            User paramUser = new User();
//            paramUser.setId(1);
//            paramUser.setDeleted(0);
//            paramUser.setUsername("1");
//            paramUser.setPassword("1");
//            paramUser.setSex(1);
//            List<Object> userList = sqlSession.selectList("org.example.mapper.UserMapper.selectSqlNode", paramUser);
//            userList.forEach(System.out::println);

//            User updateUser = new User();
//            updateUser.setUsername("2");
//            updateUser.setPassword("2");
//            Map<String, Object> map = new HashMap<>();
//            map.put("user", updateUser);
//            map.put("ids", new int[]{1, 2});
//            int r = sqlSession.update("org.example.mapper.UserMapper.updateUserForeach", map);
//            System.out.println(r);

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

    }
}

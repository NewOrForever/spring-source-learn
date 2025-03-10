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
 *  执行sql
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
//             User user = sqlSession.selectOne("org.example.mapper.UserMapper.selectById", 1);
//            System.out.println(user);

            // 测试泛型失效
            List<Map<String, String>> list = sqlSession.selectList("org.example.mapper.UserMapper.selectAll");


            List<MyUser> list2 = sqlSession.selectList("org.example.mapper.UserMapper.selectUsers");

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

    }

    public static class MyUser {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

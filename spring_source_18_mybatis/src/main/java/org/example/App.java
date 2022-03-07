package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.entity.User;

import java.io.IOException;
import java.io.Reader;

/**
 * mybatis源码学习
 */
public class App {
    public static void main(String[] args) throws IOException {
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

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

    }
}

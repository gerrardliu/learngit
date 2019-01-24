package com.gerrardliu.test.school.mapper;

import com.gerrardliu.test.school.model.School;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class SchoolMapperTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            SchoolMapper schoolMapper = sqlSession.getMapper(SchoolMapper.class);
            List<School> list = schoolMapper.selectAll();
            Assert.assertNotNull(list);
            Assert.assertTrue(list.size() > 0);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllByPage() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            SchoolMapper schoolMapper = sqlSession.getMapper(SchoolMapper.class);
            List<School> list = schoolMapper.selectAllByPage(0, 3);
            Assert.assertNotNull(list);
            Assert.assertTrue(list.size() == 3);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            SchoolMapper schoolMapper = sqlSession.getMapper(SchoolMapper.class);
            School school = schoolMapper.selectById(1);
            Assert.assertNotNull(school);
            System.out.println(school.getName());
        } finally {
            sqlSession.close();
        }
    }
}

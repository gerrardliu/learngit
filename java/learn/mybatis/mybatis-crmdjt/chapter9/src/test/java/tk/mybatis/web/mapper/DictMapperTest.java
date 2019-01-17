package tk.mybatis.web.mapper;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.web.model.SysDict;

import java.util.List;

public class DictMapperTest extends BaseMapperTest {

    @Test
    public void testSelectByPrimaryKey() {
        SqlSession sqlSession = getSqlSession();
        try {
            DictMapper dictMapper = sqlSession.getMapper(DictMapper.class);
            SysDict dict = dictMapper.selectByPrimaryKey(1L);
            Assert.assertNotNull(dict);
            System.out.println(dict.getName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectBySysDict() {
        SqlSession sqlSession = getSqlSession();
        try {
            DictMapper dictMapper = sqlSession.getMapper(DictMapper.class);
            SysDict query = new SysDict();
            query.setCode("性别");
            RowBounds rowBounds = new RowBounds();
            List<SysDict> dictList = dictMapper.selectBySysDict(query, rowBounds);
            Assert.assertNotNull(dictList);
            for (SysDict dict : dictList) {
                System.out.println(dict.getName());
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert() {
        SqlSession sqlSession = getSqlSession();
        try {
            DictMapper dictMapper = sqlSession.getMapper(DictMapper.class);
            SysDict sysDict = new SysDict();
            sysDict.setCode("性别");
            sysDict.setName("中");
            sysDict.setValue("中性");
            int result = dictMapper.insert(sysDict);
            Assert.assertEquals(1, result);
            SysDict query = new SysDict();
            query.setCode("性别");
            RowBounds rowBounds = new RowBounds();
            List<SysDict> dictList = dictMapper.selectBySysDict(query, rowBounds);
            Assert.assertNotNull(dictList);
            for (SysDict dict : dictList) {
                System.out.println(dict.getName());
            }
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = getSqlSession();
        try {
            DictMapper dictMapper = sqlSession.getMapper(DictMapper.class);
            SysDict sysDict = dictMapper.selectByPrimaryKey(1L);
            sysDict.setValue("nan");
            int result = dictMapper.updateById(sysDict);
            Assert.assertEquals(1, result);
            SysDict query = new SysDict();
            query.setCode("性别");
            RowBounds rowBounds = new RowBounds();
            List<SysDict> dictList = dictMapper.selectBySysDict(query, rowBounds);
            Assert.assertNotNull(dictList);
            for (SysDict dict : dictList) {
                System.out.println(dict.getName() + "\t" + dict.getValue());
            }
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteById() {
        SqlSession sqlSession = getSqlSession();
        try {
            DictMapper dictMapper = sqlSession.getMapper(DictMapper.class);
            int result = dictMapper.deleteById(1L);
            Assert.assertEquals(1, result);
            SysDict query = new SysDict();
            query.setCode("性别");
            RowBounds rowBounds = new RowBounds();
            List<SysDict> dictList = dictMapper.selectBySysDict(query, rowBounds);
            Assert.assertNotNull(dictList);
            for (SysDict dict : dictList) {
                System.out.println(dict.getName() + "\t" + dict.getValue());
            }
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }
}

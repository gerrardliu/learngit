package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;

import java.util.Date;
import java.util.List;

public class RoleMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            Assert.assertNotNull(role);
            System.out.println(role.getRoleName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectById2() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById2(1L);
            Assert.assertNotNull(role);
            System.out.println(role.getRoleName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectAll();
            Assert.assertNotNull(roleList);
            for (SysRole role : roleList) {
                System.out.println(role.getRoleName());
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = new SysRole();
            role.setRoleName("testRole");
            role.setCreateTime(new Date());
            int result = roleMapper.insert(role);
            Assert.assertEquals(1, result);
            System.out.println(role.getId());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert2() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = new SysRole();
            role.setRoleName("testRole");
            role.setCreateTime(new Date());
            int result = roleMapper.insert2(role);
            Assert.assertEquals(1, result);
            System.out.println(role.getId());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert3() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = new SysRole();
            role.setRoleName("testRole");
            role.setCreateTime(new Date());
            int result = roleMapper.insert3(role);
            Assert.assertEquals(1, result);
            System.out.println(role.getId());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            role.setRoleName("管理员");
            role.setCreateTime(new Date());
            int result = roleMapper.updateById(role);
            Assert.assertEquals(1, result);
            role = roleMapper.selectById(1L);
            System.out.println(role.getRoleName());
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteById() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            int result = roleMapper.deleteById(4L);
            Assert.assertEquals(1, result);
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }
}

package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.plugin.PageRowBounds;

import javax.management.relation.Role;
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
    public void testSelectAllByRowBounds() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            RowBounds rowBounds = new RowBounds(0, 1);
            List<SysRole> list = roleMapper.selectAll(rowBounds);
            for (SysRole role : list) {
                System.out.println("roleName:" + role.getRoleName());
            }
            PageRowBounds pageRowBounds = new PageRowBounds(0, 1);
            list = roleMapper.selectAll(pageRowBounds);
            System.out.println("total:" + pageRowBounds.getTotal());
            for (SysRole role : list) {
                System.out.println("roleName:" + role.getRoleName());
            }
            pageRowBounds = new PageRowBounds(1, 1);
            list = roleMapper.selectAll(pageRowBounds);
            System.out.println("total:" + pageRowBounds.getTotal());
            for (SysRole role : list) {
                System.out.println("roleName:" + role.getRoleName());
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
            //Assert.assertEquals(1, result);
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllRolesAndPrivileges() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectAllRolesAndPrivileges();
            Assert.assertNotNull(roleList);
            System.out.println("role number:" + roleList.size());
            for (SysRole role : roleList) {
                System.out.println("rolename:" + role.getRoleName());
                for (SysPrivilege privilege : role.getPrivilegeList()) {
                    System.out.println("privilegename:" + privilege.getPrivilegeName());
                }
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserId() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectRoleByUserId(1L);
            Assert.assertNotNull(roleList);
            for (SysRole role : roleList) {
                System.out.println(role.getRoleName());
                for (SysPrivilege privilege : role.getPrivilegeList()) {
                    System.out.println(privilege.getPrivilegeName());
                }
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserIdChoose() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(2L);
            role.setEnabled(0L);
            roleMapper.updateById(role);

            List<SysRole> roleList = roleMapper.selectRoleByUserIdChoose(1L);
            for (SysRole r : roleList) {
                System.out.println("rolename:" + r.getRoleName());
                if (r.getId().equals(1L)) {
                    Assert.assertNotNull(r.getPrivilegeList());
                } else if (r.getId().equals(2L)) {
                    Assert.assertNull(r.getPrivilegeList());
                    continue;
                }
                for (SysPrivilege privilege : r.getPrivilegeList()) {
                    System.out.println("privilegeName:" + privilege.getPrivilegeName());
                }
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testL2Cache() {
        SqlSession sqlSession = getSqlSession();
        SysRole role1 = null;
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            role1 = roleMapper.selectById(1L);
            role1.setRoleName("New Name");
            SysRole role2 = roleMapper.selectById(1L);
            Assert.assertEquals("New Name", role2.getRoleName());
            Assert.assertEquals(role1, role2); //这里使用的是一级缓存，所以是相同的实例
        } finally {
            sqlSession.close();
        }
        sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role2 = roleMapper.selectById(1L);
            Assert.assertEquals("New Name", role2.getRoleName());
            Assert.assertNotEquals(role1, role2);
            SysRole role3 = roleMapper.selectById(1L);
            Assert.assertNotEquals(role2, role3);
        } finally {
            sqlSession.close();
        }
    }
}

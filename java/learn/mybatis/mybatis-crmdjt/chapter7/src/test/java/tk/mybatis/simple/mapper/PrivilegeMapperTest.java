package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;

import java.util.List;

public class PrivilegeMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        SqlSession sqlSession = getSqlSession();
        try {
            PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
            SysPrivilege privilege = privilegeMapper.selectById(1L);
            Assert.assertNotNull(privilege);
            System.out.println(privilege.getPrivilegeName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectPrivilegesByRoleId() {
        SqlSession sqlSession = getSqlSession();
        try {
            PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
            List<SysPrivilege> privilegeList = privilegeMapper.selectPrivilegesByRoleId(1L);
            Assert.assertNotNull(privilegeList);
            for (SysPrivilege privilege : privilegeList) {
                System.out.println(privilege.getPrivilegeName());
            }
        } finally {
            sqlSession.close();
        }
    }
}

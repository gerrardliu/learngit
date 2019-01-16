package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    SysUser selectById(Long id);

    SysUser selectById2(Long id);

    SysUser selectByIdOrUserName(SysUser sysUser);

    SysUser selectUserAndRoleById(Long id);

    SysUser selectUserAndRoleById2(Long id);

    SysUser selectUserAndRoleByIdSelect(Long id);

    List<SysUser> selectAllUsersAndRoles();

    List<SysUser> selectAllUsersAndRolesAndPrivileges();

    SysUser selectAllUserAndRolesSelect(Long id);

    List<SysUser> selectByUser(SysUser sysUser);

    List<SysUser> selectAll();

    List<SysUser> selectByIdList(List<Long> idList);

    List<SysRole> selectRolesByUserId(Long userId);

    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId, @Param("enabled") Integer enabled);

    List<SysRole> selectRolesByUserAndRole(@Param("user") SysUser user, @Param("role") SysRole role);

    int insert(SysUser sysUser);

    int insert2(SysUser sysUser);

    int insert3(SysUser sysUser);

    int insertList(List<SysUser> userList);

    int updateById(SysUser sysUser);

    int updateByIdSelective(SysUser sysUser);

    int updateByMap(Map<String, Object> map);

    int deleteById(Long id);

}

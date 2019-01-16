package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.simple.model.SysRole;

import java.util.List;

@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper {

    @Select({"select id,role_name roleName,enabled,create_by createBy,create_time createTime from sys_role where id=#{id}"})
    SysRole selectById(Long id);

    /*@Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "create_time", column = "createTime")
    })*/
    @Results(id = "roleResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "create_time", column = "createTime")
    })
    @Select("select * from sys_role where id=#{id}")
    SysRole selectById2(Long id);

    @ResultMap(value = "roleResultMap")
    @Select("select * from sys_role")
    List<SysRole> selectAll();

    List<SysRole> selectAll(RowBounds rowBounds);

    @Insert("insert into sys_role values(#{id},#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=TIMESTAMP})")
    int insert(SysRole role);

    @Insert("insert into sys_role(role_name,enabled,create_by,create_time) values(#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=TIMESTAMP})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert2(SysRole role);

    @Insert("insert into sys_role(role_name,enabled,create_by,create_time) values(#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=TIMESTAMP})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", resultType = Long.class, before = false)
    int insert3(SysRole role);

    @Update("update sys_role set role_name=#{roleName},enabled=#{enabled},create_by=#{createBy},create_time=#{createTime,jdbcType=TIMESTAMP} where id=#{id}")
    int updateById(SysRole sysRole);

    @Delete("delete from sys_role where id=#{id}")
    int deleteById(Long id);

    List<SysRole> selectAllRolesAndPrivileges();

    List<SysRole> selectRoleByUserId(Long userId);

    List<SysRole> selectRoleByUserIdChoose(Long userId);
}

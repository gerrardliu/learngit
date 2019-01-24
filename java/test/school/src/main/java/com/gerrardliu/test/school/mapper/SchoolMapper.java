package com.gerrardliu.test.school.mapper;

import com.gerrardliu.test.school.model.School;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SchoolMapper {

    public List<School> selectAll();

    @Select("select * from school limit #{start}, #{count}")
    public List<School> selectAllByPage(Integer start, Integer count);

    @Select("select * from school where id = #{id}")
    public School selectById(Integer id);
}

package com.gerrardliu.test.sqlite.mapper;

import com.gerrardliu.test.sqlite.model.DemoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DemoMapper {

    @Select("select * from hello")
    List<DemoModel> selectAll();
}

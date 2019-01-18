package tk.mybatis.springboot.mapper;

import tk.mybatis.springboot.model.Country;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CountryMapper {
    List<Country> selectAll();
}

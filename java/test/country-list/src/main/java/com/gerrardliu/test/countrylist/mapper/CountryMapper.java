package com.gerrardliu.test.countrylist.mapper;

import com.gerrardliu.test.countrylist.model.Country;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CountryMapper {
    List<Country> selectAll();

    Country selectById(Long id);

    Country selectByCode(String code);
}

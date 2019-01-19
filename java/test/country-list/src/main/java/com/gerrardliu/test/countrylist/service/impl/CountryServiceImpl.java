package com.gerrardliu.test.countrylist.service.impl;

import com.gerrardliu.test.countrylist.mapper.CountryMapper;
import com.gerrardliu.test.countrylist.model.Country;
import com.gerrardliu.test.countrylist.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public List<Country> findAll() {
        List<Country> list = countryMapper.selectAll();
        if (list == null || list.size() < 1) {
            return new ArrayList<Country>();
        }
        return list;
    }

    @Override
    public Country findById(Long id) {
        Country country = countryMapper.selectById(id);
        return country == null ? Country.unknowCountry() : country;
    }

    @Override
    public Country findByCode(String code) {
        Country country = countryMapper.selectByCode(code);
        return country == null ? Country.unknowCountry() : country;
    }
}

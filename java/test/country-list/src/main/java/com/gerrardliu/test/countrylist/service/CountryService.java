package com.gerrardliu.test.countrylist.service;

import com.gerrardliu.test.countrylist.model.Country;

import java.util.List;

public interface CountryService {
    List<Country> findAll();

    Country findById(Long id);

    Country findByCode(String code);
}

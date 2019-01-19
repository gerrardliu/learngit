package com.gerrardliu.test.countrylist.model;

import lombok.Data;

@Data
public class Country {
    private Long id;
    private String countryname;
    private String countrycode;

    public static Country unknowCountry() {
        Country country = new Country();
        country.setId(0L);
        country.setCountrycode("N/A");
        country.setCountryname("未知国家");
        return country;
    }
}

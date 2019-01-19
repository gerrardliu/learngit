package com.gerrardliu.test.countrylist.mapper;

import com.gerrardliu.test.countrylist.model.Country;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CountryMapperTest {

    @Autowired
    private CountryMapper countryMapper;

    @Test
    public void testSelectAll() {
        List<Country> countryList = countryMapper.selectAll();
        Assert.assertNotNull(countryList);
        for (Country country : countryList) {
            System.out.println(country.getCountryname() + " " + country.getCountrycode());
        }
    }

    @Test
    public void testSelectById() {
        Country country = countryMapper.selectById(1L);
        Assert.assertNotNull(country);
        System.out.println(country.toString());
    }

    @Test
    public void testSelectByCode() {
        Country country = countryMapper.selectByCode("CN");
        Assert.assertNotNull(country);
        System.out.println(country.toString());
    }
}

package com.gerrardliu.test.countrylist.service;

import com.gerrardliu.test.countrylist.model.Country;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Test
    public void testFindAll() {
        List<Country> countryList = countryService.findAll();
        Assert.assertNotNull(countryList);
        for (Country country : countryList) {
            System.out.println("---" + country.getCountryname());
        }
    }

    @Test
    public void testFindById() {
        Country country = countryService.findById(1L);
        Assert.assertNotNull(country);
        System.out.println(country.toString());
    }

    @Test
    public void testFindByCode() {
        Country country = countryService.findByCode("CN");
        Assert.assertNotNull(country);
        System.out.println(country.toString());
    }
}

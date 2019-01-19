package com.gerrardliu.test.countrylist.controller;

import com.gerrardliu.test.countrylist.model.Country;
import com.gerrardliu.test.countrylist.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
public class CountryListController {

    @Resource
    private CountryService countryService;

    @RequestMapping("/countrylist")
    List<Country> countryList() {
        return countryService.findAll();
    }

    @RequestMapping("/country/{id}")
    Country country(@PathVariable("id") Long id) {
        return countryService.findById(id);
    }

    @RequestMapping("/country")
    CountryResp countryid(@RequestParam Long id) {
        return CountryResp.successResp(countryService.findById(id));
    }

    @RequestMapping("/countrycode/{cd}")
    Country countrycode(@PathVariable("cd") String code) {
        return countryService.findByCode(code);
    }
}

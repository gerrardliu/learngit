package com.gerrardliu.test.countrylist.controller;

import com.gerrardliu.test.countrylist.model.Country;
import com.gerrardliu.test.countrylist.service.CountryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CountryListController {

    @Resource
    private CountryService countryService;

    @RequestMapping("/countrylist")
    CountryResp countryList() {
        return CountryResp.successResp(countryService.findAll());
    }

    @RequestMapping("/country/{id}")
    CountryResp country(@PathVariable("id") Long id) {
        return CountryResp.successResp(countryService.findById(id));
    }

    @RequestMapping("/country")
    CountryResp countryid(@RequestParam Long id) {
        return CountryResp.successResp(countryService.findById(id));
    }

    @RequestMapping("/countrycode/{cd}")
    CountryResp countrycode(@PathVariable("cd") String code) {
        return CountryResp.successResp(countryService.findByCode(code));
    }
}

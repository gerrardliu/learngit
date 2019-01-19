package com.gerrardliu.test.countrylist.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping("/")
    String desc() {
        return "Country List Demo";
    }
}

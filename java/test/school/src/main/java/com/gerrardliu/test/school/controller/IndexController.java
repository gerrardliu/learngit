package com.gerrardliu.test.school.controller;

import com.gerrardliu.test.school.common.RestResult;
import com.gerrardliu.test.school.model.School;
import com.gerrardliu.test.school.service.SchoolService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class IndexController {

    @Resource
    private SchoolService schoolService;

    @RequestMapping("")
    public RestResult index() {
        return RestResult.success(schoolService.findAll());
    }

    @RequestMapping("/get")
    public RestResult school() {
        return RestResult.success(new School());
    }
}

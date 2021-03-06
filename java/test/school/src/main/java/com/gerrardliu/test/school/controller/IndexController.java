package com.gerrardliu.test.school.controller;

import com.gerrardliu.test.school.common.RestResult;
import com.gerrardliu.test.school.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;

import javax.annotation.Resource;

@Slf4j
@RestController
public class IndexController {

    @Resource
    private SchoolService schoolService;

    @RequestMapping
    public RestResult index() {
        log.info("这里是index内部");
        return RestResult.success("Hello");
    }

    @RequestMapping("/list")
    public RestResult list(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer count) {
        log.info("enter list()");
        if (start != null) {
            if (count == null) {
                count = new Integer(3);
            }
            return RestResult.success(schoolService.findAllByPage(start, count));
        }
        return RestResult.success(schoolService.findAll());
    }

    @RequestMapping("/get")
    public RestResult get(@RequestParam Integer id) {
        log.info("enter get: id={}", id);
        return RestResult.success(schoolService.findById(id));
    }
}

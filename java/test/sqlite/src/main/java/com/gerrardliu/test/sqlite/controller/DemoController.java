package com.gerrardliu.test.sqlite.controller;

import com.gerrardliu.test.sqlite.model.DemoModel;
import com.gerrardliu.test.sqlite.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DemoController {
    private DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/listall")
    @ResponseBody
    public List<DemoModel> listAll() {
        return demoService.selectAll();
    }
}

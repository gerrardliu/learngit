package com.bee.sample.ch1.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloworldController {

    private Log log = LogFactory.getLog(HelloworldController.class);

    @RequestMapping("/sayhello.html")
    @ResponseBody
    public String say() {
        log.info("some one accessed");
        return "Hello Spring Boot";
    }

}

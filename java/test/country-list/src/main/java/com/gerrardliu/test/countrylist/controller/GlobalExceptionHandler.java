package com.gerrardliu.test.countrylist.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler()
    @ResponseBody
    CountryResp handleException(Exception e) {
        e.printStackTrace();
        CountryResp resp = new CountryResp();
        resp.setCode(-1);
        resp.setMessage(e.getMessage());
        return resp;
        //return "=======handleException:" + e.getMessage();
    }
}

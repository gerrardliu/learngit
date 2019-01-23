package com.gerrardliu.test.school.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ResponseBody
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public RestResult handleException(Exception e) {
        log.error("捕获异常:{}", e.toString());
        //return RestResult.fail(-1, e.getMessage());
        return RestResult.fail(-1, "内部错误");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RestResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("参数{}缺失:{}", e.getParameterName(), e.toString());
        return RestResult.fail(-2, String.format("参数%s缺失", e.getParameterName()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RestResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数{}类型错误：{}", e.getName(), e.toString());
        return RestResult.fail(-3, String.format("参数%s类型错误", e.getName()));
    }
}

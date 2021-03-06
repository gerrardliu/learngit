package com.gerrardliu.test.school.common;

import lombok.Data;

@Data
public class RestResult {

    private Integer code;
    private String message;
    private Object data;

    public static RestResult success(Object data) {
        RestResult r = new RestResult();
        r.setCode(0);
        r.setMessage("success");
        r.setData(data);
        return r;
    }

    public static RestResult fail(Integer code, String message) {
        RestResult r = new RestResult();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }
}

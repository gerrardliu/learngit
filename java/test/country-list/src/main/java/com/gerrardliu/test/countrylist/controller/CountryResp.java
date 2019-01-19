package com.gerrardliu.test.countrylist.controller;

import lombok.Data;

@Data
public class CountryResp {
    private Integer code;
    private String message;
    private Object data;

    public static CountryResp successResp(Object data) {
        CountryResp r = new CountryResp();
        r.setCode(0);
        r.setMessage("success");
        r.setData(data);
        return r;
    }
}

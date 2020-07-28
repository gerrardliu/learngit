package com.gerrardliu.test.sqlite.service;

import com.gerrardliu.test.sqlite.mapper.DemoMapper;
import com.gerrardliu.test.sqlite.model.DemoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    private DemoMapper demoMapper;

    @Autowired
    public DemoService(DemoMapper demoMapper) {
        this.demoMapper = demoMapper;
    }

    public List<DemoModel> selectAll() {
        return demoMapper.selectAll();
    }

}

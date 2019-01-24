package com.gerrardliu.test.school.service;

import com.gerrardliu.test.school.mapper.SchoolMapper;
import com.gerrardliu.test.school.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    public List<School> findAll() {
        return schoolMapper.selectAll();
    }

    public List<School> findAllByPage(Integer start, Integer count) {
        return schoolMapper.selectAllByPage(start, count);
    }

    public School findById(Integer id) {
        return schoolMapper.selectById(id);
    }
}

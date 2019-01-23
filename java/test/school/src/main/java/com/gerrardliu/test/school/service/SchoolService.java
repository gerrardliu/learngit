package com.gerrardliu.test.school.service;

import com.gerrardliu.test.school.model.School;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolService {

    public List<School> findAll() {
        List<School> list = new ArrayList<School>();
        {
            School school = new School();
            school.setName("酒仙桥中心小学");
            school.setScale(2000);
            list.add(school);
        }
        {
            School school = new School();
            school.setName("中关村二小");
            school.setScale(200);
            list.add(school);
        }
        return list;
    }

    public School findById(Integer id) {
        School school = new School();
        school.setName("北京大学");
        school.setScale(9999);
        return school;
    }
}

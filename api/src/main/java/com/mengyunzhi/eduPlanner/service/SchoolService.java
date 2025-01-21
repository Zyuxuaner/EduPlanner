package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.entity.School;

import java.util.List;

public interface SchoolService {
    /**
     * 新增学校
     * @param school
     */
    School save(School school);

    /**
     * 获取所有学校
     * @return
     */
    List<School> getAll();
}

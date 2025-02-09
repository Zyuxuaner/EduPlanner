package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.School;

import java.util.List;
import java.util.Optional;

public interface SchoolService {
    /**
     * 新增学校
     * @param school
     */
    Response<Void> save(School school);

    /**
     * 获取所有学校
     * @return
     */
    List<School> getAll();

    Optional<School> getSchoolById(Long id);

    School updateSchool(Long id, String name);
}

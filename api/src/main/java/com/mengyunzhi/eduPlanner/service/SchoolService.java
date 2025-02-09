package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.School;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SchoolService {
    Response<Void> deleteSchool(Long id);

    /**
     * 获取所有学校
     * @return
     */
    List<School> getAll();

    ResponseEntity<List<School>> getSchoolById(Long id);

    /**
     * 新增学校
     * @param school
     */
    Response<Void> save(School school);

    List<School> searchSchoolsByName(String name);

    Response<School> updateSchool(Long id, String name);
}

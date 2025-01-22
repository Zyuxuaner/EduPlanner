package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseRequest;
import com.mengyunzhi.eduPlanner.entity.Course;

public interface CourseService {
    /**
     * 新增课程
     * @param courseRequest
     * @param userId
     * @param schoolId
     * @return
     */
    Course save(CourseRequest courseRequest, Long userId, Long schoolId);

}

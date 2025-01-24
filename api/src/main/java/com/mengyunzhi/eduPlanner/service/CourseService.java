package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseResponse;
import com.mengyunzhi.eduPlanner.dto.CourseRequest;
import com.mengyunzhi.eduPlanner.entity.Course;

import java.util.List;

public interface CourseService {
    /**
     * 新增课程
     * @param courseRequest
     * @param userId
     * @param schoolId
     * @return
     */
    Course save(CourseRequest courseRequest, Long userId, Long schoolId);

    /**
     * 获取课程列表
     * @param clazzId 当前登录用户的班级id
     * @param studentId 当前登录用户的学生id
     * @return
     */
    List<CourseResponse> getAllCoursesForCurrentUser(Long clazzId, Long studentId);

}

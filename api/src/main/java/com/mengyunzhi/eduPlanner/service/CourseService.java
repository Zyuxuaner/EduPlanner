package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseDto;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {
    /**
     * 新增课程
     * @param saveRequest 新增的课程
     * @param userId 登录用户的id
     * @param schoolId 登录用户的 学校id
     * @return course
     */
    Course save(CourseDto.SaveRequest saveRequest, Long userId, Long schoolId);

    /**
     * 获取课程列表
     * @param clazzId 当前登录用户的班级id
     * @param studentId 当前登录用户的学生id
     * @return
     */
    List<CourseDto.GetAllCoursesForCurrentUserResponse> getAllCoursesForCurrentUser(Long clazzId, Long studentId);

    /**
     * 根据学校id 和 确定的第几周week 来查询该学校下所有学生的有课情况
     * @param schoolId 学校id
     * @param week 第几周
     * @return 嵌套数据结构
     */
    Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> getAllStudentsCoursesOfSchool(Long schoolId, Long week);

}

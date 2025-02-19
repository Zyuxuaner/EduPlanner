package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseDto;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Course;
import com.mengyunzhi.eduPlanner.entity.CourseInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangyuxuan
 */
public interface CourseService {
    /**
     * 删除课程安排
     * @param courseInfoId 课程安排id
     * @return 响应
     */
    Response<String> delete(Long courseInfoId);
    /**
     * 新增课程
     * @param saveRequest 新增的课程
     * @param userId 登录用户的id
     * @param schoolId 登录用户的 学校id
     * @return course
     */
    Response<String> save(CourseDto.SaveRequest saveRequest, Long userId, Long schoolId);

    /**
     * 获取课程列表
     * @return 课程安排列表
     */
    List<CourseDto.GetAllCoursesResponse> getAllCourses();

    /**
     * 课程复用
     * @param courseInfoId 被复用的课程安排id
     * @param studentId 需要复用的学生id
     * @return 响应
     */
    Response<String> reuseCourseInfo(Long courseInfoId, Long studentId);

    /**
     * 取消课程复用
     * @param courseInfoId 需要取消的课程安排id
     * @param studentId 操作的学生id
     * @return 响应
     */
    Response<String> cancelReuseCourseInfo(Long courseInfoId, Long studentId);

    /**
     * 根据school和week获取所有学生的课程安排
     * 如果传了student id，那么获取该周单个学生的课程安排
     * @param schoolId 学校id
     * @param week 周数
     * @param studentId 学生id
     * @return 数组
     */
    Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> getMessage(Long schoolId, Long week, Long studentId);
}
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
     * 检查该课程安排是否还有学生复用
     * @param courseInfoId 该课程id
     * @return response.status = true 未被复用
     */
    Response<String> checkReuseStudent(Long courseInfoId);

    /**
     * 根据school和week获取所有学生的课程安排
     * 如果传了student id，那么获取该周单个学生的课程安排
     * @param schoolId 学校id
     * @param week 周数
     * @param studentId 学生id
     * @return 数组
     */
    Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> getMessage(Long schoolId, Long week, Long studentId);

    /**
     * 获取所有学校所有学生的课程安排信息
     * @param schoolId 学校id列表
     * @param weeks 学校对应的当前周
     * @return 课程安排信息
     */
    Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> getAllMessage(List<Long> schoolId, List<Long> weeks);

    List<CourseDto.GetAllCoursesResponse> search(String searchCourse, Long creatorStudent);

    /**
     * 根据 courseInfoId 获取需要编辑的课程安排
     * @param courseInfoId 待编辑的课程安排
     * @return 响应
     */
    Response<CourseDto.GetCourseInfoByIdResponse> getCourseInfoById(Long courseInfoId);

    /**
     * 更新课程安排
     * @param saveRequest 需要更新的数据
     * @param courseInfoId 更新的课程安排id
     * @param studentId 学生id
     * @return 响应
     */
    Response<String> update(CourseDto.SaveRequest saveRequest, Long courseInfoId, Long studentId);

}
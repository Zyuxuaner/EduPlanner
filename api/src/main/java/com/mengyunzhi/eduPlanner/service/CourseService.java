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
     * @return StudentsCoursesOfSchoolResponse嵌套数据结构
     */
    Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> getAllStudentsCoursesOfSchool(Long schoolId, Long week);
    /**
     * 获取当前登录用户对应的学期id
     * @param currentUser
     * @return
     */
    Long getTermIdByLoginUser(Response<CurrentUser> currentUser);

    /**
     * 根据选择的周数，来获取当前登录用户在所选周的课程安排
     * @param clazzId 当前登录学生用户的班级id
     * @param studentId 当前登录学生用户的学生id
     * @param week 被选中的周数
     * @return StudentsCoursesOfSchoolResponse嵌套数据结构
     */
    Map<Long, Map<Long, List<CourseDto.StudentCourseInfoResponse>>> getCourseInfoByCurrentUserOfWeek(Long clazzId, Long studentId, Long week);
    /**
     * 获取当前登录用户的班级id
     * @param currentUser
     * @return
     */
    Long getClassIdByLoginUser(Response<CurrentUser> currentUser);

    boolean isTimeLegal(CourseDto.SaveRequest saveRequest);

    boolean isTimeConflict(CourseInfo newCourseInfo, CourseInfo existingCourseInfo);

    /**
     * 返回开始周到结束周，包括开始周和结束周。比如{1, 2, 3, 4, 5}
     * @param startWeek
     * @param endWeek
     * @return
     */
    Set<Long> getWeeksInRange(Long startWeek, Long endWeek, Long type);
}
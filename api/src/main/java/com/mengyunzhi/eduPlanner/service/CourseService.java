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
//    /**
//     * 新增课程
//     * @param saveRequest 新增的课程
//     * @param userId 登录用户的id
//     * @param schoolId 登录用户的 学校id
//     * @return course
//     */
//    Course save(CourseDto.SaveRequest saveRequest, Long userId, Long schoolId);
//
//    /**
//     * 获取课程列表
//     * @param clazzId 当前登录用户的班级id
//     * @param studentId 当前登录用户的学生id
//     * @return
//     */
//    List<CourseDto.GetAllCoursesForCurrentUserResponse> getAllCoursesForCurrentUser(Long clazzId, Long studentId);
//
//    /**
//     * 获取当前登录用户对应的学期id
//     * @param currentUser
//     * @return
//     */
//    Long getTermIdByLoginUser(Response<CurrentUser> currentUser);
//
//    /**
//     * 根据选择的周数，来获取当前登录用户在所选周的课程安排
//     * @param clazzId 当前登录学生用户的班级id
//     * @param studentId 当前登录学生用户的学生id
//     * @param week 被选中的周数
//     * @param termId 当前激活的学期id
//     * @return StudentsCoursesOfSchoolResponse嵌套数据结构
//     */
//    Map<Long, Map<Long, List<CourseDto.StudentCourseInfoResponse>>> getCourseInfoByCurrentUserOfWeek(Long clazzId, Long studentId, Long week, Long termId);
//    /**
//     * 获取当前登录用户的班级id
//     * @param currentUser
//     * @return
//     */
//    Long getClassIdByLoginUser(Response<CurrentUser> currentUser);
//
//    boolean isTimeLegal(CourseDto.SaveRequest saveRequest);
//
//    boolean isTimeConflict(CourseInfo newCourseInfo, CourseInfo existingCourseInfo);
//
//    /**
//     * 返回开始周到结束周，包括开始周和结束周。比如{1, 2, 3, 4, 5}
//     * @param startWeek
//     * @param endWeek
//     * @return
//     */
//    Set<Long> getWeeksInRange(Long startWeek, Long endWeek, Long type);

    /**
     * 根据school和week获取所有学生的课程安排
     * 如果传了student id，那么获取该周单个学生的课程安排
     * @param schoolId
     * @param week
     * @param studentId
     * @return
     */
    Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> getMessage(Long schoolId, Long week, Long studentId);
}
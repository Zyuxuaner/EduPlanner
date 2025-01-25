package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseResponse;
import com.mengyunzhi.eduPlanner.dto.CourseRequest;
import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Course;
import com.mengyunzhi.eduPlanner.entity.CourseInfo;

import java.util.List;
import java.util.Set;

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

    /**
     * 获取当前登录用户对应的学期id
     * @param currentUser
     * @return
     */
    Long getTermIdByLoginUser(Response<CurrentUser> currentUser);

    /**
     * 获取当前登录用户的班级id
     * @param currentUser
     * @return
     */
    Long getClassIdByLoginUser(Response<CurrentUser> currentUser);

    boolean isTimeLegal(CourseRequest courseRequest);

    boolean isTimeConflict(CourseInfo newCourseInfo, CourseInfo existingCourseInfo);

    /**
     * 返回开始周到结束周，包括开始周和结束周。比如{1, 2, 3, 4, 5}
     * @param startWeek
     * @param endWeek
     * @return
     */
    Set<Long> getWeeksInRange(Long startWeek, Long endWeek, Long type);
}

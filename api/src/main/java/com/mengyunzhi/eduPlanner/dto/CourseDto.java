package com.mengyunzhi.eduPlanner.dto;

import com.mengyunzhi.eduPlanner.entity.Term;
import lombok.Data;

import java.util.List;

/**
 * @author zhangyuxuan
 */
public class CourseDto {
    @Data
    public static class SaveRequest {
        private String name;
        /**
         * all:全周，odd:单周，even:双周，other:自定义（其他）
         */
        private String weekType;
        private List<Integer> weeks;
        private Long day;
        private Long begin;
        private Long length;
    }

    @Data
    public static class GetAllCoursesForCurrentUserResponse {
        private String name;
        /**
         * 1：选修，2：必修
         */
        private Long type;
        private Long weekType;
        private Long startWeek;
        private Long endWeek;
        private Long week;
        private Long begin;
        private Long length;
        private Term term;
    }

    /**
     * 确定学校，确定周数的所有学生课程安排
     * week 周几
     * begin 开始的小节
     * length 持续几节
     * students 学生名单
     */
    @Data
    public static class StudentsCoursesOfSchoolResponse {
        private Long week;
        private Long begin;
        private Long length;
        private List<String> students;
    }

    /**
     * 当前登录学生的，确定周数的课程安排
     * week 周几
     * begin 开始的小节
     * length 持续几节
     * name 课程名称
     */
    @Data
    public static class StudentCourseInfoResponse {
        private Long week;
        private Long begin;
        private Long length;
        private String name;
    }

    @Data
    public static class SchoolWeekRequest {
        private Long schoolId;
        private Long week;
    }
}

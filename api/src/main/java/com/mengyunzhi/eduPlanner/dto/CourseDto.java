package com.mengyunzhi.eduPlanner.dto;

import com.mengyunzhi.eduPlanner.entity.Term;
import lombok.Data;

import java.util.List;

public class CourseDto {
    @Data
    public static class SaveRequest {
        private String name;

        /**
         * 1:选修，2:必修
         */
        private Long type;

        /**
         * 1:单周，2:双周，3:全周
         */
        private Long status;
        private Long startWeek;
        private Long endWeek;
        private Long week;
        private Long begin;
        private Long end;
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

    @Data
    public static class GetAllCoursesRequest {
        private Long schoolId;
        private Long week;
    }
}

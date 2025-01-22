package com.mengyunzhi.eduPlanner.dto;

import lombok.Data;

@Data
public class CourseRequest {
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

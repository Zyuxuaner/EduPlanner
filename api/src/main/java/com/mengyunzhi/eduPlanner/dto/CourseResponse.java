package com.mengyunzhi.eduPlanner.dto;

import com.mengyunzhi.eduPlanner.entity.Term;
import lombok.Data;

import java.sql.Struct;

@Data
public class CourseResponse {
    private String name;
    /**
     * 1：选秀，2：必修
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

package com.mengyunzhi.eduPlanner.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CourseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_week")
    private Long startWeek;

    @Column(name = "end_week")
    private Long endWeek;

    private Long type;

    private Long day;

    private Long begin;

    private Long length;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
}

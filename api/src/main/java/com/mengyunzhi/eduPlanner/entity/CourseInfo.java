package com.mengyunzhi.eduPlanner.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class CourseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long weekType;

    @ElementCollection
    @CollectionTable(name = "course_weeks", joinColumns = @JoinColumn(name = "course_info_id"))
    @Column(name = "week")
    private List<Integer> weeks;

    private Long day;

    private Long begin;

    private Long length;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}

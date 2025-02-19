package com.mengyunzhi.eduPlanner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author zhangyuxuan
 */
@Entity
@Data
public class CourseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week_type")
    private String weekType;

    @ElementCollection
    @CollectionTable(name = "course_info_week", joinColumns = @JoinColumn(name = "course_info_id"))
    @Column(name = "week")
    private List<Integer> weeks;

    private Long day;

    private Long begin;

    private Long length;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "creator_student_id", nullable = false)
    private Student creator;

    @ManyToMany
    @JoinTable(
            name = "student_course_info",
            joinColumns = @JoinColumn(name = "course_info_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;
}

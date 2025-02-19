package com.mengyunzhi.eduPlanner.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String sno;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToMany(mappedBy = "students")
    private Set<CourseInfo> reusedCourses;

    @OneToOne
    private User user;

    /**
     *  1：激活 2：冻结
     */
    private Long status;
}

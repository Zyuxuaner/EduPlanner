package com.mengyunzhi.eduPlanner.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long type;

    @ManyToOne
    @JoinColumn(name = "term_id", referencedColumnName = "id")
    private Term term;

    @ManyToOne
    @JoinColumn(name = "clazz_id", referencedColumnName = "id")
    private Clazz clazz;

    private Long studentId;
}

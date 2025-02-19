package com.mengyunzhi.eduPlanner.entity;

import lombok.Data;

import javax.persistence.*;

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

    @OneToOne
    private User user;

    /**
     *  1：激活 2：冻结
     */
    private Long status;
}

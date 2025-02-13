package com.mengyunzhi.eduPlanner.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String sno;

    @Getter
    @Setter
    @ManyToOne
    private Clazz clazz;

    @OneToOne
    private User user;

    /**
     *  1：激活 2：冻结
     */
    private Long status;
}

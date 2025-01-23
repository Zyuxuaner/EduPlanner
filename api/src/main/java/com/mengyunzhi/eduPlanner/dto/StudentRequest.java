package com.mengyunzhi.eduPlanner.dto;

import com.mengyunzhi.eduPlanner.entity.Clazz;
import com.mengyunzhi.eduPlanner.entity.School;
import lombok.Data;

@Data
public class StudentRequest {
    private Clazz clazz;
    private String name;
    private String username;
    private String sno;
    private Long status;
}

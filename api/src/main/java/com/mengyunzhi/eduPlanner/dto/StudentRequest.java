package com.mengyunzhi.eduPlanner.dto;

import com.mengyunzhi.eduPlanner.entity.School;
import lombok.Data;

@Data
public class StudentRequest {
    private School school;
    private String name;
    private String username;
    private String sno;
    private Long status;
}

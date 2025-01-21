package com.mengyunzhi.eduPlanner.dto;

import lombok.Data;

@Data
public class AdminRequest {
    private String name;
    private String username;
    private Long ano;
    private Long role;
}

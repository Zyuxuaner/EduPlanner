package com.mengyunzhi.eduPlanner.dto;


import lombok.Data;

import java.util.Map;

@Data
public class DingTalkResponse {
    /**
     * 学生姓名
     */
    private String name;

    /**
     * 每节课的状态 显示有课或空
     */
    private Map<String, String> sections;

    public DingTalkResponse(String name, Map<String, String> sections) {
        this.name = name;
        this.sections = sections;
    }
}

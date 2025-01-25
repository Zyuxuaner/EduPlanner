package com.mengyunzhi.eduPlanner.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回当前登录用户的详细信息
 */
public class CurrentUser {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Long role;

    @Getter
    @Setter
    private Long schoolId;

    @Getter
    @Setter
    private String no;

    public CurrentUser(Long id, String name, String username, String password, Long role, Long schoolId, String no) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.schoolId = schoolId;
        this.no = no;
    }
}
